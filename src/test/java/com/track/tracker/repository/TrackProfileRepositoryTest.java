package com.track.tracker.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.track.tracker.model.Link;
import com.track.tracker.model.Metadata;
import com.track.tracker.model.TrackProfile;
import com.track.tracker.model.Tracking;
import com.track.tracker.model.TrackingPoint;
import com.track.tracker.model.TrackingSegment;
import com.track.tracker.model.Waypoint;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrackProfileRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TrackProfileRepository trackProfileRepository;

	@Test
	public void test_findById_returnTrackProfile() {
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
		entityManager.persist(trackProfile);
		entityManager.flush();

		TrackProfile found = trackProfileRepository.findById(trackProfile.getId()).get();

		assertEquals(found.getId().compareTo(trackProfile.getId()), 0);
	}
	
//	@Test
//	public void test_findById_notFound() {
//		Optional<TrackProfile> found = trackProfileRepository.findById(UUID.randomUUID());
//		assertTrue(found.isEmpty());
//	}
}
