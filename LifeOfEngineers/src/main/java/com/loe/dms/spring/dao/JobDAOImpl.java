package com.loe.dms.spring.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.loe.dms.spring.model.data.Contact;
import com.loe.dms.spring.model.data.ErrorInfoData;
import com.loe.dms.spring.model.data.Job;
import com.loe.dms.spring.model.data.Location;
import com.loe.dms.spring.model.entity.ContactEntity;
import com.loe.dms.spring.model.entity.JobEntity;
import com.loe.dms.spring.model.entity.LocationEntity;

@Repository
public class JobDAOImpl implements JobDAO {

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
	public Job addJob(Job job) {
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		JobEntity jobEntity = new JobEntity();
		try {
			System.out.println("At 1");
			jobEntity.setJobTitle(job.getTitle());
			jobEntity.setJobDescription(job.getDescription());
			jobEntity.setSharedByUser(job.getSharedByUser());
			session.save(jobEntity);
			System.out.println("At 2 " + jobEntity.getId());
			transaction.commit();
			session.flush();
		} catch(Exception e){
			if(transaction.isActive())
				transaction.rollback();

			e.printStackTrace();
		} finally {
			closeSession(session);
		}		
		return getJobById(jobEntity.getId());
	}

	@Override
	public void mergeJob(Job job) {
		Session session = null;
		try {
			session = openSession();
			session.merge(job);
			session.flush();
		} finally {
			closeSession(session);
		}
	}

	@Override
	public Job getJobById(Long jobId){
		Session session = null;
		Job job = new Job();
		try {
			session = openSession();
			JobEntity jobEntity = (JobEntity) session.get(JobEntity.class, jobId);
			if(jobEntity != null){
				job.setId(jobEntity.getId());
				job.setTitle(jobEntity.getTitle());
				job.setDescription(jobEntity.getDescription());
				job.setSharedByUser(jobEntity.getSharedByUser());
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return job;
	}
	
	@Override
	public List<Job> getJobs(Map<String, String> requestParams) {
		Session session = null;
		List<Job> listofJobs = new ArrayList<Job>();
		try {
			session = openSession();
			if(!requestParams.isEmpty()){
				Criteria criteria = session.createCriteria(Location.class);
				if(requestParams.containsKey("city") && requestParams.containsKey("state")){
					criteria.add(Restrictions.eq("city", requestParams.get("city")));
					criteria.add(Restrictions.eq("state", requestParams.get("state")));
				}
				
				if(requestParams.containsKey("country")){
					criteria.add(Restrictions.eq("country", requestParams.get("country")));
				}
				
				for(JobEntity jobEntity : (List<JobEntity>) criteria.list()){
					Job job = new Job();
					job.setId(jobEntity.getId());
					job.setTitle(jobEntity.getTitle());
					job.setDescription(jobEntity.getDescription());
					List<Location> locations = new ArrayList<Location>();
					for(LocationEntity le : jobEntity.getLocations()){
						Location location = new Location();
						location.setId(le.getId());
						location.setCountry(le.getCountry());
						location.setState(le.getState());
						location.setCity(le.getCity());
						locations.add(location);
					}
					List<Contact> contacts = new ArrayList<Contact>();
					for(ContactEntity ce : jobEntity.getContacts()){
						Contact contact = new Contact(ce.getId(),
								ce.getMethod(),
								ce.getType(),
								ce.getData(),null);
						contacts.add(contact);
					}
					job.setSharedByUser(jobEntity.getSharedByUser());
					listofJobs.add(job);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		finally {
			closeSession(session);
		}
		return listofJobs;
	}
}