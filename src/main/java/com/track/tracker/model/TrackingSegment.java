package com.track.tracker.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.track.tracker.constants.ValidatorConstants;
import com.track.tracker.validator.annotation.ValidSegmentPoints;

@Entity
@Table(name = "tracking_segment")
public class TrackingSegment extends AbstractLocalEntity {
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tracking_id", nullable = false)
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private Tracking tracking;
	@JacksonXmlProperty(isAttribute = false, localName = "trkpt")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trackingSegment")
	@ValidSegmentPoints(message = ValidatorConstants.SEGMENT_POINTS_VALIDATOR)
	private Set<TrackingPoint> trackingPoints;

	public TrackingSegment() {

	}

	@JsonCreator
	public TrackingSegment(@JsonProperty("trkpt") Set<TrackingPoint> trackingPoints) {
		this.setTrackingPoints(trackingPoints);
	}

	public Tracking getTracking() {
		return tracking;
	}

	public void setTracking(Tracking tracking) {
		this.tracking = tracking;
	}

	public Set<TrackingPoint> getTrackingPoints() {
		return trackingPoints;
	}

	public void setTrackingPoints(Set<TrackingPoint> trackingPoints) {
		if (trackingPoints != null) {
			for (TrackingPoint trackingPoint : trackingPoints) {
				trackingPoint.setTrackingSegment(this);
			}
		}
		this.trackingPoints = trackingPoints;
	}

}
