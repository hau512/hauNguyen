package com.track.tracker.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import com.track.tracker.response.TrackerProfileResponse;
import com.track.tracker.service.TrackerProfileService;

@RestController
@CrossOrigin("http://localhost:4200")
public class TrackProfileController {
	private final HttpStatus okStatus = HttpStatus.OK;
	@Autowired
	TrackerProfileService trackerProfileService;

	@PostMapping("/uploadFile")
	public ResponseEntity<TrackerProfileResponse> uploadFile(@RequestParam("file") MultipartFile file)
			throws IOException {
		TrackerProfileResponse response = trackerProfileService.handleGPXFIle(file);
		response.setCode(okStatus.value());
		response.setMessage(okStatus.toString());
		return new ResponseEntity<TrackerProfileResponse>(response, okStatus);
	}

	@GetMapping("/latestTrack")
	public ResponseEntity<TrackerProfileResponse> getLatestTrack() {
		TrackerProfileResponse response = trackerProfileService.getLastedTrackerProfiles(0, 10);
		response.setCode(okStatus.value());
		response.setMessage(okStatus.toString());
		return new ResponseEntity<TrackerProfileResponse>(response, okStatus);
	}
	
	@GetMapping("/trackProfile/{id}")
	public ResponseEntity<TrackerProfileResponse> getTrackProfileById(@PathVariable String id) {
		TrackerProfileResponse response = trackerProfileService.getTrackProfileById(id);
		response.setCode(okStatus.value());
		response.setMessage(okStatus.toString());
		return new ResponseEntity<TrackerProfileResponse>(response, okStatus);
	}
}