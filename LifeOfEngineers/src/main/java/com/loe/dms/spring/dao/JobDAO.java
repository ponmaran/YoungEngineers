package com.loe.dms.spring.dao;

import java.util.List;
import java.util.Map;

import com.loe.dms.spring.model.data.Job;

public interface JobDAO {
	
	public Job addJob(Job job);
	
	public Job getJobById(Long jobId);

	public List<Job> getJobs(Map<String, String> requestParams);
	
	public void mergeJob(Job job);

}
