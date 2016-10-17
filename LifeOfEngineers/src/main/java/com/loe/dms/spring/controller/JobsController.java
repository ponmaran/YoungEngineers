package com.loe.dms.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.loe.dms.spring.model.data.Job;
import com.loe.dms.spring.model.data.Location;
import com.loe.dms.spring.model.data.ServiceResponse;
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

	@RequestMapping(value = "/post", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> postJob(@RequestBody Job job, BindingResult result) {
		logger.info(">>>>> Posting Job >>>>");
		ServiceResponse serviceResponse = jobsService.addJob(job);
		Response response = new Response();
		if(serviceResponse.hasErrors()){
			response.setStatus("E");
			response.setErrorInfoData(serviceResponse.getErrorInfoData());
		} else {
			response.setStatus("S");
		}
		return new ResponseEntity<JobsController.Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> listJobs(@RequestBody Location location, BindingResult result) {
		logger.info(">>>>> Listing Job >>>>");
		System.out.println("Called...");
		System.out.println(location.getCountry());
		System.out.println(location.getState());
		System.out.println(location.getCity());
		Map<String,String> requestParams = new HashMap<String, String>();
		if(location.getCountry() != null && !location.getCountry().isEmpty())
			requestParams.put("jobCountry", location.getCountry());
		
		if(location.getState() != null && !location.getState().isEmpty())
			requestParams.put("jobState", location.getState());
		
		if(location.getCity() != null && !location.getCity().isEmpty())
			requestParams.put("jobCity", location.getCity());
		
		List<Job> jobs = jobsService.getJobsByLocation(location);
		Response response = new Response();
		if(jobs.isEmpty()){
			response.setStatus("E");
		} else {
			System.out.println("jobs not empty");
			response.setStatus("S");
			response.setMessage("Jobs found");
			for(Job job : jobs)
				System.out.println(job.getId().toString());
			response.setData(jobs);
		}
		return new ResponseEntity<JobsController.Response>(response, HttpStatus.OK);
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private class Response extends ServiceResponse {

		private String status;
		private String message;
		private Object data;
		
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
		public void setData(Object data){
			this.data = data;
		}
		
		public Object getData(){
			return data;
		}
		
		public void setMessage(String message){
			this.message = message;
		}
		
		public String getMessage(){
			return message;
		}
	}
}