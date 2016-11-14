package com.loe.dms.spring.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {

	private Long id;
	private String country;
	private String state;
	private String city;
	private Job job;
	public Location(){		
	}
	
	public Location(Location location){
		this.id = location.getId();
		this.city = location.getCity();
		this.state = location.getState();
		this.country = location.getCountry();
		this.job = location.getJob();
	}
	
	public Long getId(){
		return id;
	}

	public String getCountry(){
		return country;
	}

	public String getState(){
		return state;
	}

	public String getCity(){
		return city;
	}
	
	public Job getJob(){
		return job;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public void setCountry(String country){
		this.country = country;
	}
	
	public void setState(String state){
		this.state = state;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public void setJob(Job job){
		this.job = job;
	}
}