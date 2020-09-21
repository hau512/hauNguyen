package com.track.tracker.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.track.tracker.model.Link;
import com.track.tracker.model.Metadata;
import com.track.tracker.model.TrackProfile;
import com.track.tracker.model.Tracking;
import com.track.tracker.model.TrackingPoint;
import com.track.tracker.model.TrackingSegment;
import com.track.tracker.model.Waypoint;
import com.track.tracker.repository.TrackProfileRepository;
import com.track.tracker.response.TrackerProfileResponse;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrackProfileServiceTest {

	@Autowired
	private TrackerProfileService trackerProfileService;

	@Autowired
	TrackProfileRepository trackProfileRepository;

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
	public void test_getLatestRecords() {
		TrackerProfileResponse trackerProfileResponse = trackerProfileService.getLastedTrackerProfiles(0, 20);
		boolean result = false;
		for (TrackProfile trackProfile : trackerProfileResponse.getResult()) {
			if (trackProfile.getId().compareTo(preTrackProfileId) == 0) {
				result = true;
				break;
			}
		}
		assertTrue(result);
	}

	@Test
	public void test_getRecordById() {
		TrackerProfileResponse trackerProfileResponse = trackerProfileService
				.getTrackProfileById(preTrackProfileId.toString());
		boolean result = false;
		for (TrackProfile trackProfile : trackerProfileResponse.getResult()) {
			if (trackProfile.getId().compareTo(preTrackProfileId) == 0) {
				result = true;
				break;
			}
		}
		assertTrue(result);
	}

	@Test
	public void test_getRecordById_NotFound() {
		TrackerProfileResponse trackerProfileResponse = trackerProfileService
				.getTrackProfileById(UUID.randomUUID().toString());
		assertEquals(0, trackerProfileResponse.getResult().size());
	}

	@Test
	public void test_saveGPXFile() throws IOException {
		String expectedResult = "Bardenas Reales: Piskerra y el Paso de los Ciervos";
		String actualResponse = "";
		String actualSave = "";
		InputStream is = getClass().getClassLoader().getResourceAsStream("sample.gpx");
		TrackerProfileResponse trackerProfileResponse = trackerProfileService.saveGPXFile(is);
		String savedId = trackerProfileResponse.getResult().get(0).getId().toString();
		actualResponse = trackerProfileResponse.getResult().get(0).getMetadata().getName();
		List<TrackProfile> getByIdResponse = trackerProfileService.getTrackProfileById(savedId).getResult();
		if (getByIdResponse.size() > 0) {
			actualSave = getByIdResponse.get(0).getMetadata().getName();
		}
		assertEquals(expectedResult, actualResponse);
		assertEquals(expectedResult, actualSave);
	}
}
