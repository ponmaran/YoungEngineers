package com.loe.dms.spring.service;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.loe.dms.spring.dao.ContactsDAO;
import com.loe.dms.spring.dao.JobDAO;
import com.loe.dms.spring.dao.LocationsDAO;
import com.loe.dms.spring.model.data.Contact;
import com.loe.dms.spring.model.data.ErrorInfoData;
import com.loe.dms.spring.model.data.Job;
import com.loe.dms.spring.model.data.Location;
import com.loe.dms.spring.model.data.ServiceResponse;
import com.loe.dms.spring.model.entity.JobEntity;

@Service
public class JobsServiceImpl implements JobsService {

	private static final Logger logger = LoggerFactory.getLogger(EventsServiceImpl.class);
	
	@Inject
	private JobDAO jobDAO;

	@Inject
	private LocationsDAO locationsDAO;

	@Inject
	private ContactsDAO contactsDAO;
	
	@Transactional
	public boolean addJob(Job job) {
		Job addedJob = jobDAO.addJob(job);
		if(addedJob != null && addedJob.getId() != null){
			List<Location> jobLocations = job.getLocations();
			for(Location location : jobLocations){
				locationsDAO.addLocationForJob(location, addedJob);
			}
			
			List<Contact> jobContacts = job.getContacts();
			for(Contact contact : jobContacts){
				contactsDAO.addContactForJob(contact, addedJob);
			}

		} else {
			return false;
		}
		
		return true;
	}

	@Override
	public List<Job> getJobsByLocation(Location location) {
		List<Job> jobs = locationsDAO.getJobsByLocation(location);
		for(Job job : jobs){
			job.setLocations(locationsDAO.getLocationsOfJob(job));
			job.setContacts(contactsDAO.getContactsOfJob(job));
		}
		return jobs;
	}

	@Transactional
	public ServiceResponse editJob(Job job) {
		return null;
	}

}