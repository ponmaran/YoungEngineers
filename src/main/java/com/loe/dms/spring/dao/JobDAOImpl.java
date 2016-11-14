package com.loe.dms.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.loe.dms.spring.model.data.Job;
import com.loe.dms.spring.model.entity.JobEntity;

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
		JobEntity jobEntity = new JobEntity();
		try {
			jobEntity.setTitle(job.getTitle());
			jobEntity.setDescription(job.getDescription());
			jobEntity.setSharedByUser(job.getSharedByUser());
			session.save(jobEntity);
			session.flush();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			closeSession(session);
		}		
		return getJobById(jobEntity.getId());
	}

	@Override
	public void mergeJob(Job job) {
		Session session = openSession();
		JobEntity jobEntity = new JobEntity();
		try {
			jobEntity.setId(job.getId());
			jobEntity.setTitle(job.getTitle());
			jobEntity.setDescription(job.getDescription());
			jobEntity.setSharedByUser(job.getSharedByUser());
			session.merge(jobEntity);
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
}