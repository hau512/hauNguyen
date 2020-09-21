package com.track.tracker.model;

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
@Table(name = "waypoint")
public class Waypoint extends AbstractLocalEntity {
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "track_profile_id", nullable = false)
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private TrackProfile trackProfile;
	@JacksonXmlProperty(isAttribute = true, localName = "lat")
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private Double latitude;
	@JacksonXmlProperty(isAttribute = true, localName = "lon")
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private Double longitude;
	private String name;
	@JacksonXmlProperty(isAttribute = true, localName = "sym")
	private String symbol;

	public Waypoint() {

	}

	@JsonCreator
	public Waypoint(@JsonProperty("lat") Double latitude, @JsonProperty("lon") Double longitude,
			@JsonProperty("name") String name, @JsonProperty("sym") String symbol) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.symbol = symbol;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public TrackProfile getTrackProfile() {
		return trackProfile;
	}

	public void setTrackProfile(TrackProfile trackProfile) {
		this.trackProfile = trackProfile;
	}

}
