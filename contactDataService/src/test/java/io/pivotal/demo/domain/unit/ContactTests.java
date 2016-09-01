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
	
	/**
	 * Builds the Contact object to test
	 */
	@Before
	public void buildContact() {
		contact = new Contact("title", "firstName", "lastName", "email", new Phone(PhoneType.work,"312-555-1212"));
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

		TestCase.assertEquals(
				"The toString method should match the pattern [" 
						+ expectedToString + "].",
				expectedToString, contact.toString());
	}
}
