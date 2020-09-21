package com.track.tracker.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.track.tracker.constants.ValidatorConstants;

@Entity
@Table(name = "tracking_point")
public class TrackingPoint extends AbstractLocalEntity {
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tracking_segment_id", nullable = false)
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private TrackingSegment trackingSegment;
	@JacksonXmlProperty(isAttribute = true, localName = "lat")
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private Double latitude;
	@JacksonXmlProperty(isAttribute = true, localName = "lon")
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private Double longitude;
	@JacksonXmlProperty(localName = "ele")
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private Double elevation;
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private Date time;

	public TrackingPoint() {

	}

	@JsonCreator
	public TrackingPoint(@JsonProperty("lat") Double latitude, @JsonProperty("lon") Double longitude,
			@JsonProperty("ele") Double elevation, @JsonProperty("time") Date time) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
		this.time = time;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getElevation() {
		return elevation;
	}

	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public TrackingSegment getTrackingSegment() {
		return trackingSegment;
	}

	public void setTrackingSegment(TrackingSegment trackingSegment) {
		this.trackingSegment = trackingSegment;
	}

}
