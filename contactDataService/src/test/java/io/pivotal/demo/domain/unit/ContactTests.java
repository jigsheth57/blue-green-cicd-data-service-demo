package io.pivotal.demo.domain.unit;

import org.junit.Before;
import org.junit.Test;

import io.pivotal.demo.domain.Contact;
import io.pivotal.demo.domain.Phone;
import io.pivotal.demo.domain.PhoneType;
import junit.framework.TestCase;

/**
 * Unit tests for the <code>Contact</code> domain object.
 * 
 * @author Jignesh Sheth
 *
 */
public class ContactTests {

	private Contact contact;
	
	private static final String CONTACT_TO_JSON_PATTERN = "{\"Id\": %d, \"title\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\", \"phone\": %s}";
//	private static final String CONTACT_TO_JSON_PATTERN = "{\"Id\": %d, \"title\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\", \"phone\": %s, \"maritalStatus\": \"%s\"}";
	
	/**
	 * Builds the Contact object to test
	 */
	@Before
	public void buildContact() {
		final String title = "title";
		final String firstName = "firstName";
		final String lastName = "lastName";
		final String email = "email";
		final Phone phone = new Phone(PhoneType.work,"312-555-1212");
		final String maritalStatus = "married";
//		contact = new Contact(title, firstName, lastName, email, phone, maritalStatus);
		contact = new Contact(title, firstName, lastName, email, phone);
	}
	
	/**
	 * Tests the overridden toString method is correct.
	 */
	@Test
	public void testToString() {
		String expectedToString = String.format(CONTACT_TO_JSON_PATTERN,
				contact.getId(), 
				contact.getTitle(), 
				contact.getFirstName(),
				contact.getLastName(),
				contact.getEmail(),
				contact.getPhone());
//				contact.getPhone(),
//				contact.getMaritalStatus());

		TestCase.assertEquals(
				"The toString method should match the pattern [" 
						+ expectedToString + "].",
				expectedToString, contact.toString());
	}
}
