package com.loe.dms.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loe.dms.spring.model.data.Contact;
import com.loe.dms.spring.model.data.Job;
import com.loe.dms.spring.model.entity.ContactEntity;
import com.loe.dms.spring.model.entity.JobEntity;

public class ContactsDAOImpl implements ContactsDAO {

	private static final Logger logger = LoggerFactory.getLogger(EventsDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	private Session openSession() {
		return this.sessionFactory.openSession();
	}
	
	public void closeSession(Session session) {
		if (session != null) {
			session.close();
		}
	}

	@Override
	public void addContactForJob(Contact contact, Job job) {
		Session session = openSession();
		ContactEntity ce = new ContactEntity();
		try{
			ce.setMethod(contact.getMethod());
			ce.setType(contact.getType());
			ce.setData(contact.getData());

			JobEntity jobEntity = new JobEntity();
			jobEntity.setId(job.getId());
			ce.setJob(jobEntity);

			session.save(ce);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
	}

	@Override
	public Contact fetchContactByID(Long contactId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getContactsOfJob(Job job) {
		Session session = openSession();
		List<Contact> contacts = new ArrayList<Contact>();
		Criteria criteria = session.createCriteria(ContactEntity.class);
		criteria.add(Restrictions.eq("job.id",job.getId()));
		for(ContactEntity ce : (List<ContactEntity>) criteria.list()){
			Contact contact = new Contact();
//			contact.setId(ce.getId());
			contact.setMethod(ce.getMethod());
			contact.setType(ce.getType());
			contact.setData(ce.getData());
			contacts.add(contact);
		}
		return contacts;
	}

	@Override
	public List<Job> getJobsByContact(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editContact(Contact contact) {
		// TODO Auto-generated method stub

	}
}