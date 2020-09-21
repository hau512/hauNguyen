package com.track.tracker.controller;

import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.track.tracker.constants.ResponseConstants;
import com.track.tracker.constants.ValidatorConstants;
import com.track.tracker.model.Link;
import com.track.tracker.model.Metadata;
import com.track.tracker.model.TrackProfile;
import com.track.tracker.model.Tracking;
import com.track.tracker.model.TrackingPoint;
import com.track.tracker.model.TrackingSegment;
import com.track.tracker.model.Waypoint;
import com.track.tracker.repository.TrackProfileRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TrackProfileControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TrackProfileRepository trackProfileRepository;

	private UUID preTrackProfileId = null;

	@Before
	public void setUp() {
		Set<TrackingPoint> trackingPointSet = new HashSet<TrackingPoint>();
		trackingPointSet.add(new TrackingPoint(42.2208895, -1.4580696, 315.86, new Date()));
		trackingPointSet.add(new TrackingPoint(42.2208228, -1.458099, 316.03888000000006, new Date()));
		Set<TrackingSegment> trackingSegmentSet = new HashSet<TrackingSegment>();
		trackingSegmentSet.add(new TrackingSegment(trackingPointSet));
		Set<Tracking> trackingSet = new HashSet<Tracking>();
		trackingSet.add(new Tracking(trackingSegmentSet));
		Set<Waypoint> waypointSet = new HashSet<Waypoint>();
		waypointSet.add(new Waypoint(42.2205377, -1.4564538, "testing weapoint", "/static/wpt/Waypoint"));
		waypointSet.add(new Waypoint(42.2208346, -1.4544232, "testing weapoint2", "/static/wpt/Waypoint"));
		Metadata metadata = new Metadata("metadata", "testing metadata", "unittest",
				new Link("http://www.sample.com", "sample"), new Date());
		TrackProfile trackProfile = new TrackProfile("1.1", "unittest", metadata, waypointSet, trackingSet);
		trackProfileRepository.save(trackProfile);
		preTrackProfileId = trackProfile.getId();
	}

	@Test
	public void test_uploadFile_return200() throws Exception {
		InputStream is = getClass().getClassLoader().getResourceAsStream("sample.gpx");
		MockMultipartFile firstFile = new MockMultipartFile("file", "sample.gpx", "application/xml", is);
		System.out.println(firstFile.getName());
		mockMvc.perform(MockMvcRequestBuilders.multipart("/uploadFile").file(firstFile)).andExpect(status().isOk())
				.andExpect(jsonPath("result[0].creator", is("OruxMaps v.7.1.6 Donate")));
	}

	@Test
	public void test_uploadFile_segmentOnePoint_return400() throws Exception {
		InputStream is = getClass().getClassLoader().getResourceAsStream("sample-segment-1-point.gpx");
		MockMultipartFile firstFile = new MockMultipartFile("file", "sample.gpx", "application/xml", is);
		System.out.println(firstFile.getName());
		mockMvc.perform(MockMvcRequestBuilders.multipart("/uploadFile").file(firstFile)).andExpect(status().is(400))
				.andExpect(jsonPath("detail[0]", containsString(ValidatorConstants.SEGMENT_POINTS_VALIDATOR)));
	}

	@Test
	public void test_uploadFile_Invalid_return400() throws Exception {
		InputStream is = getClass().getClassLoader().getResourceAsStream("schema.sql");
		MockMultipartFile firstFile = new MockMultipartFile("file", is);
		mockMvc.perform(MockMvcRequestBuilders.multipart("/uploadFile").file(firstFile)).andExpect(status().is(400))
				.andExpect(jsonPath("code", is(ResponseConstants.UNSUPPORTED_FILE_CODE)));
	}

	@Test
	public void test_getById_return200() throws Exception {
		this.mockMvc.perform(get("/trackProfile/" + preTrackProfileId.toString())).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("result[0].creator", is("unittest")));
	}

	@Test
	public void test_getById_NotFound_return200() throws Exception {
		this.mockMvc.perform(get("/trackProfile/" + UUID.randomUUID().toString())).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("result.length()", is(0)));
	}

	@Test
	public void test_getById_Invalid_return400() throws Exception {
		String invalidId = "1";
		this.mockMvc.perform(get("/trackProfile/" + invalidId)).andExpect(status().is(400))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("detail[0]", containsString(invalidId)));
	}

	@Test
	public void test_getLatestTrack_return200() throws Exception {
		this.mockMvc.perform(get("/latestTrack/")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("result[0].creator", is("unittest")));
	}
}
