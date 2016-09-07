package io.pivotal.demo.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.pivotal.demo.repository.LogListener;

@Entity
@EntityListeners(LogListener.class)
public class Contact {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	
	@Column(nullable=false)
	private String firstName, lastName;
	
	@Column(nullable=true)
	private String title, email;

	@Column(nullable=true)
	private String maritalStatus;
	
	@Embedded
	private Phone phone;
	
	public Contact() {}
	
	public Contact (String title, String firstName, String lastName, String email, Phone phone, String maritalStatus) {
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.maritalStatus = maritalStatus;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Override
	public String toString() {
		return String.format(
//				"{\"Id\": %d, \"title\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\", \"phone\": %s}",
				"{\"Id\": %d, \"title\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\", \"phone\": %s, \"maritalStatus\": \"%s\"}",
				Id, 
				title, 
				firstName,
				lastName,
				email,
//				phone);
				phone,
				maritalStatus);
	}
	
	

}
