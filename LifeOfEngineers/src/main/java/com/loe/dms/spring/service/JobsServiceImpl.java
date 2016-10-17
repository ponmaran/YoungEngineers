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

@Service
public class JobsServiceImpl implements JobsService {

	private static final Logger logger = LoggerFactory.getLogger(EventsServiceImpl.class);
	
	@Inject
	private JobDAO jobDAO;

//	@Autowired(required = true)
//	@Qualifier(value = "jobDAO")
//	public void setJobDAO(JobDAO jobDAO) {
//		this.jobDAO = jobDAO;
//	}
	
	@Inject
	private LocationsDAO locationsDAO;

//	@Autowired(required = true)
//	@Qualifier(value = "locationsDAO")
//	public void setLocationsDAO(LocationsDAO locationsDAO) {
//		this.locationsDAO = locationsDAO;
//	}
	
	@Inject
	private ContactsDAO contactsDAO;
	
//	@Autowired(required = true)
//	@Qualifier(value = "locationsDAO")
//	public void setContactsDAO(ContactsDAO contactsDAO) {
//		this.contactsDAO = contactsDAO;
//	}
	
	@Transactional
	public ServiceResponse addJob(Job job) {
		ServiceResponse serviceResponse = new ServiceResponse();
		Job addedJob = jobDAO.addJob(job);
		if(addedJob != null && addedJob.getId() != null){
			List<Location> jobLocations = job.getLocations();
			System.out.println("At 4 " + jobLocations.toString());
			for(Location location : jobLocations){
				System.out.println("At 5");
				locationsDAO.addLocationForJob(location, addedJob);
			}
			
			List<Contact> jobContacts = job.getContacts();
			System.out.println("At 6 " + jobContacts.toString());
			for(Contact contact : jobContacts){
				System.out.println("At 7 ");
				contactsDAO.addContactForJob(contact, addedJob);
			}

		} else {
			ErrorInfoData errorInfoData = new ErrorInfoData();
			errorInfoData.addErrorInfo("Error while adding job");
			serviceResponse.setErrorInfoData(errorInfoData);
		}
		return serviceResponse;
	}

	@Override
	public List<Job> getJobsByLocation(Location location) {
		return locationsDAO.getJobsByLocation(location);
	}

	@Transactional
	public ServiceResponse editJob(Job job) {
		return null;
	}

}