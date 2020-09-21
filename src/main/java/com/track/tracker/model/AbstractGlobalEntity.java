package com.track.tracker.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class AbstractGlobalEntity extends AbstractBaseEntity<UUID> implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	protected UUID id;
	@Version
	@JsonIgnore
	@Transient
	private Long version;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@JsonIgnore
	public Long getVersion() {
		return version;
	}

	@JsonIgnore
	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		else
			return this.getClass().getSimpleName().equals(other.getClass().getSimpleName())
					&& this.id.equals(((AbstractGlobalEntity) other).id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
