package io.pivotal.demo.repository.integration;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.demo.domain.Contact;
import io.pivotal.demo.domain.Phone;
import io.pivotal.demo.domain.PhoneType;
import io.pivotal.demo.repository.ContactRepository;
import junit.framework.TestCase;

/**
 * Integration tests for the <code>ContactRepository</code>
 * JPA repository interface.
 * 
 * The <code>SpringApplicationConfiguration</code> annotation
 * ensures that the embedded database is started and configured
 * for the integration tests.
 * 
 * Most of the methods tested (<code>findOne</code>, <code>save</code>),
 * are provided by the base JpaRepository class.
 * 
 * @author Jignesh Sheth
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactRepositoryTests {
	
	public static Contact contact = new Contact("title", "firstName", "lastName", "email", new Phone(PhoneType.work,"312-555-1212"));

	//The repository to test.
	@Autowired
	ContactRepository contactRepo;

	@Before
	public void setUp() {
		contact = contactRepo.save(contact);
	}
	
	/**
	 * Tests the repository's findAll method, by asserting that
	 * there is more than 0 replies returned.
	 */
	@Test
	public void testFindAll() {
		Iterable<Contact> contacts = contactRepo.findAll();
		TestCase.assertNotNull(
				"Find all should return at least 1 result.",
				contacts.iterator().next());
	}

	/**
	 * Tests the repository's findByFirstNameOrLastName method, by
	 * getting the first contact from findAll, and then 
	 * using that contact's firstName to call and assert the 
	 * findByFirstNameOrLastName method's results.
	 */
	@Test
	public void testfindByFirstNameOrLastName() {
		
		Contact firstContact = contactRepo.findAll().iterator().next();
		List<Contact> resultOfFindByFirstNameOrLastName = contactRepo.findByFirstNameOrLastName(firstContact.getFirstName(),firstContact.getLastName());
		TestCase.assertEquals(
				firstContact.getFirstName(), 
				resultOfFindByFirstNameOrLastName.get(0).getFirstName());
		
	}

	/**
	 * Tests the repository's save method, by
	 * creating a new Contact object, saving it,
	 * fetching it back from the repository, and
	 * asserting that it was fetched properly.
	 */
	@Test
	public void testSaveNew() {
		
		final String title = "title";
		final String firstName = "firstName";
		final String lastName = "lastName";
		final String email = "email";
		final Phone phone = new Phone(PhoneType.work,"312-555-1212");
		Contact newContact = new Contact(title, firstName, lastName, email, phone);
		newContact = contactRepo.save(newContact);
		Contact savedContact = 
				contactRepo.findOne(newContact.getId());
		TestCase.assertEquals(
				title, savedContact.getTitle());
		TestCase.assertEquals(
				firstName, savedContact.getFirstName());
		TestCase.assertEquals(
				lastName, savedContact.getLastName());
		TestCase.assertEquals(
				email, savedContact.getEmail());
		TestCase.assertEquals(
				phone.toString(), savedContact.getPhone().toString());
	}

	/**
	 * Tests the repository's update method, by
	 * creating a new Contact object, saving it,
	 * fetching it back from the repository, and 
	 * updating the attributes and asserting
	 * that it was fetched properly.
	 */
	@Test
	public void testUpdateExisting() {
		String title = contact.getTitle();
		String firstName = contact.getFirstName();
		String lastName = contact.getLastName();
		String email = contact.getEmail();
		Phone phone = contact.getPhone();
		Contact updatedContact = contact;
		Phone updatedPhone = new Phone(PhoneType.main,"312-555-1212");
		updatedContact.setTitle(title+"_updated");
		updatedContact.setFirstName(firstName+"_updated");
		updatedContact.setLastName(lastName+"_updated");
		updatedContact.setEmail(email+"_updated");
		updatedContact.setPhone(updatedPhone);
		updatedContact = contactRepo.save(updatedContact);
		Contact savedContact = 
				contactRepo.findOne(updatedContact.getId());
		TestCase.assertEquals(
				title+"_updated", savedContact.getTitle());
		TestCase.assertEquals(
				firstName+"_updated", savedContact.getFirstName());
		TestCase.assertEquals(
				lastName+"_updated", savedContact.getLastName());
		TestCase.assertEquals(
				email+"_updated", savedContact.getEmail());
		TestCase.assertEquals(
				updatedPhone.toString(), savedContact.getPhone().toString());
	}

	/**
	 * Tests the repository's delete method, by
	 * creating a new Contact object, saving it 
	 * and then deleting it and then trying to
	 * fetch it back from the repository, and
	 * asserting that it was not available.
	 */
	@Test
	public void testDelete() {
		
		contactRepo.delete(contact.getId());
		Contact savedContact = 
				contactRepo.findOne(contact.getId());
		TestCase.assertNull(savedContact);
	}
}
