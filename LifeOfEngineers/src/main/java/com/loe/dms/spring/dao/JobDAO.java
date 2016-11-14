package com.loe.dms.spring.dao;

import com.loe.dms.spring.model.data.Job;

public interface JobDAO {
	
	public Job addJob(Job job);
	
	public Job getJobById(Long jobId);

	public void mergeJob(Job job);

}
