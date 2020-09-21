package com.track.tracker.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.track.tracker.exception.UnsupportedFileException;
import com.track.tracker.model.TrackProfile;
import com.track.tracker.repository.TrackProfileRepository;
import com.track.tracker.response.TrackerProfileResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class TrackerProfileService {
	@Autowired
	TrackProfileRepository trackProfileRepository;

	private TrackProfile saveOrUpdate(TrackProfile trackProfile) {
		trackProfileRepository.save(trackProfile);
		return trackProfile;
	}

	public TrackerProfileResponse handleGPXFIle(MultipartFile file) throws IOException {
		String[] fileNameParts = file.getOriginalFilename().toUpperCase().split("\\.");
		if (fileNameParts.length == 0 || !fileNameParts[fileNameParts.length - 1].equals("GPX")) {
			throw new UnsupportedFileException();
		}
		return saveGPXFile(file.getInputStream());
	}

	public TrackerProfileResponse saveGPXFile(InputStream inputStream) throws IOException {
		String xml = inputStreamToString(inputStream);
		TrackerProfileResponse trackerProfileResponse = new TrackerProfileResponse();
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		XmlMapper xmlMapper = new XmlMapper(module);
		TrackProfile value = xmlMapper.readValue(xml, TrackProfile.class);
		List<TrackProfile> returnTrackProfile = new ArrayList<TrackProfile>();
		returnTrackProfile.add(saveOrUpdate(value));
		trackerProfileResponse.setResult(returnTrackProfile);
		return trackerProfileResponse;
	}

	private String inputStreamToString(InputStream inputStream) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}

	private Page<TrackProfile> getTrackerProflieList(Pageable pageable) {
		return trackProfileRepository.findAll(pageable);
	}

	public TrackerProfileResponse getLastedTrackerProfiles(int page, int size) {
		TrackerProfileResponse trackerProfileResponse = new TrackerProfileResponse();
		Pageable sortedByPriceDesc = PageRequest.of(page, size, Sort.by("creationTime").descending());
		List<TrackProfile> returnTrackProfile = getTrackerProflieList(sortedByPriceDesc).toList();
		trackerProfileResponse.setResult(returnTrackProfile);
		return trackerProfileResponse;
	}

	private Optional<TrackProfile> findTrackProfileById(UUID id) {
		return trackProfileRepository.findById(id);
	}

	public TrackerProfileResponse getTrackProfileById(String id) {
		TrackerProfileResponse trackerProfileResponse = new TrackerProfileResponse();
		List<TrackProfile> returnTrackProfile = new ArrayList<TrackProfile>();
		Optional<TrackProfile> trackProfile = findTrackProfileById(UUID.fromString(id));
		if (!trackProfile.isEmpty()) {
			returnTrackProfile.add(trackProfile.get());
		}
		trackerProfileResponse.setResult(returnTrackProfile);
		return trackerProfileResponse;
	}

}
