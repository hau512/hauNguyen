package com.track.tracker.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.track.tracker.constants.ValidatorConstants;

@Entity
@Table(name = "track_profile")
@JacksonXmlRootElement(localName = "gpx")
public class TrackProfile extends AbstractGlobalEntity {
	@Transient
	private static final long serialVersionUID = 1L;
	@JacksonXmlProperty(isAttribute = true, localName = "version")
	@Column(name = "file_version")
	private String fileVersion;
	@JacksonXmlProperty(isAttribute = true)
	private String creator;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "metadata_id", referencedColumnName = "id")
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private Metadata metadata;
	@JacksonXmlProperty(isAttribute = false, localName = "wpt")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trackProfile")
	@NotEmpty(message = ValidatorConstants.NOT_EMPTY_VALIDATOR)
	private Set<Waypoint> waypoints;
	@JacksonXmlProperty(isAttribute = false, localName = "trk")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trackProfile")
	@NotEmpty(message = ValidatorConstants.NOT_EMPTY_VALIDATOR)
	private Set<Tracking> trackings;
	@Column(name = "creation_time", nullable = false, updatable = false)
	@CreationTimestamp
	private Date creationTime;
	@Column(name = "modification_time")
	@UpdateTimestamp
	private Date modificationTime;

	public TrackProfile() {

	}

	@JsonCreator
	public TrackProfile(@JsonProperty("version") String fileVersion, @JsonProperty("creator") String creator,
			@JsonProperty("metadata") Metadata metadata, @JsonProperty("wpt") Set<Waypoint> waypoints,
			@JsonProperty("trk") Set<Tracking> trackings) {
		this.fileVersion = fileVersion;
		this.creator = creator;
		this.metadata = metadata;
		this.setWaypoints(waypoints);
		this.setTrackings(trackings);
	}

	public String getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public Set<Waypoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(Set<Waypoint> waypoints) {
		if (waypoints != null) {
			for (Waypoint waypoint : waypoints) {
				waypoint.setTrackProfile(this);
			}
		}
		this.waypoints = waypoints;
	}

	public Set<Tracking> getTrackings() {
		return trackings;
	}

	public void setTrackings(Set<Tracking> trackings) {
		if (trackings != null) {
			for (Tracking tracking : trackings) {
				tracking.setTrackProfile(this);
			}
		}
		this.trackings = trackings;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

}
