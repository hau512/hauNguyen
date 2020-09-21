package com.track.tracker.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.track.tracker.model.TrackProfile;

public interface TrackProfileRepository extends JpaRepository<TrackProfile, String> {
	public Optional<TrackProfile> findById(UUID id);
}
