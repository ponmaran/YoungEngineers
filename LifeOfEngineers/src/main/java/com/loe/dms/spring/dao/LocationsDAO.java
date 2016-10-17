package com.loe.dms.spring.dao;

import java.util.List;

import com.loe.dms.spring.model.data.Job;
import com.loe.dms.spring.model.data.Location;

public interface LocationsDAO {

	public void addLocationForJob(Location location, Job job);
	
	public Location fetchLocationByID(Long locationId);

	public List<Location> getLocationsOfJob(Job job);
	
	public List<Job> getJobsByLocation(Location location);
	
	public void editLocation(Location location);
}