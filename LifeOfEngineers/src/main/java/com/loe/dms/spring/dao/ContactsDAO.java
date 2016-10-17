package com.loe.dms.spring.dao;

import java.util.List;
import java.util.Map;

import com.loe.dms.spring.model.data.Contact;
import com.loe.dms.spring.model.data.Job;

public interface ContactsDAO {
	public void addContactForJob(Contact contact, Job job);
	
	public Contact fetchContactByID(Long contactId);

	public List<Contact> getContactsOfJob(Job job);
	
	public List<Job> getJobsByContact(Contact contact);
	
	public void editContact(Contact contact);
}
