package com.loe.dms.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loe.dms.spring.model.data.Job;
import com.loe.dms.spring.model.data.Location;
import com.loe.dms.spring.model.entity.JobEntity;
import com.loe.dms.spring.model.entity.LocationEntity;
import com.sun.istack.internal.NotNull;

public class LocationsDAOImpl implements LocationsDAO {

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
	public void addLocationForJob(Location location, Job job) {
		Session session = openSession();
		LocationEntity le = new LocationEntity();
		try{
			le.setCountry(location.getCountry());
			le.setState(location.getState());
			le.setCity(location.getCity());
			
			JobEntity jobEntity = new JobEntity();
			jobEntity.setId(job.getId());
			le.setJob(jobEntity);
			
			session.save(le);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
	}

	@Override
	public Location fetchLocationByID(Long locationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> getLocationsOfJob(Job job) {
		Session session = openSession();
		List<Location> locations = new ArrayList<Location>();
		Criteria criteria2 = session.createCriteria(LocationEntity.class);
		criteria2.add(Restrictions.eq("job.id",job.getId()));
		for(LocationEntity le : (List<LocationEntity>) criteria2.list()){
			Location location = new Location();
//			location.setId(le.getId());
			location.setCountry(le.getCountry());
			location.setState(le.getState());
			location.setCity(le.getCity());
			locations.add(location);
		}
		return locations;
	}

	@Override
	public List<Job> getJobsByLocation(@NotNull Location location) {
		Session session = openSession();
		List<Job> listofJobs = new ArrayList<Job>();
		try {
			Criteria criteria = session.createCriteria(LocationEntity.class);
			if(location.getCity() != null)
				criteria.add(Restrictions.eq("city", location.getCity()));
			if(location.getState() != null)
				criteria.add(Restrictions.eq("state", location.getState()));
			if(location.getCountry() != null)
				criteria.add(Restrictions.eq("country", location.getCountry()));
			
			for(LocationEntity locationEntity : (List<LocationEntity>) criteria.list()){
				JobEntity jobEntity = locationEntity.getJob();
				Job job = new Job();
				job.setId(jobEntity.getId());
				job.setTitle(jobEntity.getTitle());
				job.setDescription(jobEntity.getDescription());
				job.setSharedByUser(jobEntity.getSharedByUser());
				listofJobs.add(job);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		finally {
			closeSession(session);
		}
		return listofJobs;
	}

	@Override
	public void editLocation(Location location) {
		// TODO Auto-generated method stub
	}
}
