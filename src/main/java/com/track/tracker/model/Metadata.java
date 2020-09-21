package com.track.tracker.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.track.tracker.constants.ValidatorConstants;

@Entity
@Table(name = "metadata")
public class Metadata extends AbstractLocalEntity {
	@NotBlank(message = ValidatorConstants.NOT_BLANK_VALIDATOR)
	private String name;
	@JacksonXmlProperty(localName = "desc")
	private String description;
	private String author;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "link_id", referencedColumnName = "id")
	private Link link;
	@NotNull(message = ValidatorConstants.NOT_NULL_VALIDATOR)
	private Date time;
	@JsonCreator
	public Metadata() {

	}

	@JsonCreator
	public Metadata(@JsonProperty("name") String name, @JsonProperty("desc") String description,
			@JsonProperty("author") String author, @JsonProperty("link") Link link, @JsonProperty("time") Date time) {
		this.name = name;
		this.description = description;
		this.author = author;
		this.link = link;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

}
