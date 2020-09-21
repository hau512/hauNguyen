package com.track.tracker.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.track.tracker.constants.ValidatorConstants;

@Entity
@Table(name = "tracking")
public class Tracking extends AbstractLocalEntity {
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "track_profile_id", nullable = false)
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private TrackProfile trackProfile;
	@JacksonXmlProperty(isAttribute = false, localName = "trkseg")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tracking")
	@NotEmpty(message = ValidatorConstants.NOT_EMPTY_VALIDATOR)
	private Set<TrackingSegment> trackingSegments;

	public Tracking() {

	}

	@JsonCreator
	public Tracking(@JsonProperty("trkseg") Set<TrackingSegment> trackingSegments) {
		this.setTrackingSegments(trackingSegments);
	}

	public Set<TrackingSegment> getTrackingSegments() {
		return trackingSegments;
	}

	public void setTrackingSegments(Set<TrackingSegment> trackingSegments) {
		if (trackingSegments != null) {
			for (TrackingSegment trackingSegment : trackingSegments) {
				trackingSegment.setTracking(this);
			}
		}
		this.trackingSegments = trackingSegments;
	}

	public TrackProfile getTrackProfile() {
		return trackProfile;
	}

	public void setTrackProfile(TrackProfile trackProfile) {
		this.trackProfile = trackProfile;
	}

}
