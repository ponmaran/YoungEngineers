package com.loe.dms.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loe.dms.spring.model.data.Contact;
import com.loe.dms.spring.model.data.Job;
import com.loe.dms.spring.model.entity.ContactEntity;
import com.loe.dms.spring.model.entity.JobEntity;
import com.loe.dms.spring.model.entity.LocationEntity;

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
			jobEntity.setJobId(job.getId());
			ce.setJob(jobEntity);

			session.save(ce);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		System.out.println(ce.getId());
	}

	@Override
	public Contact fetchContactByID(Long contactId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getContactsOfJob(Job job) {
		// TODO Auto-generated method stub
		return null;
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
