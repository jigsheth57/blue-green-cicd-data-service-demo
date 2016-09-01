package io.pivotal.demo.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Phone {
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	PhoneType type;
	@Column(nullable=false)
	String value;
	
	public Phone() {}
	
	public Phone(PhoneType type, String value) {
		this.type = type;
		this.value = value;
	}
	public PhoneType getType() {
		return type;
	}
	public void setType(PhoneType type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return String.format(
				"{\"type\": \"%s\", \"value\": \"%s\"}",
				type, 
				value);
	}
}
