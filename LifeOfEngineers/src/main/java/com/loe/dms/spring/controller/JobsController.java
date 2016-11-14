package com.loe.dms.spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loe.dms.spring.model.data.Job;
import com.loe.dms.spring.model.data.Location;
import com.loe.dms.spring.service.JobsService;

@RestController
@RequestMapping("/jobs")
public class JobsController {
	private static final Logger logger = LoggerFactory.getLogger(JobsController.class);

	private JobsService jobsService;

	//Add user session validation
	
	@Autowired(required = true)
	@Qualifier(value = "jobsService")
	public void setJobsService(JobsService jobsService) {
		this.jobsService = jobsService;
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Void> postJob(@RequestBody Job job, BindingResult result) {
		logger.info(">>>>> Posting Job >>>>");
		if(jobsService.addJob(job))
			return new ResponseEntity<>(HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Job>> listJobs(
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "country", required = false) String country) {
		logger.info(">>>>> Listing Job >>>>");
		
		Location location = new Location();
		location.setCountry(country);
		location.setState(state);
		location.setCity(city);
		
		List<Job> jobs = jobsService.getJobsByLocation(location);
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
}