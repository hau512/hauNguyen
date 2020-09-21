package com.track.tracker.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@Entity
@Table(name = "link")
public class Link extends AbstractLocalEntity {
	@JacksonXmlProperty(isAttribute = true)
	private String href;
	private String text;

	public Link() {

	}

	@JsonCreator
	public Link(@JsonProperty("href") String href, @JsonProperty("text") String text) {
		this.href = href;
		this.text = text;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
