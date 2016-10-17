package com.loe.dms.spring.service;

import java.util.List;

import com.loe.dms.spring.model.data.Job;
import com.loe.dms.spring.model.data.Location;
import com.loe.dms.spring.model.data.ServiceResponse;

public interface JobsService {
	public ServiceResponse addJob(Job job);

	public List<Job> getJobsByLocation(Location locatoin);

	public ServiceResponse editJob(Job job);
}
