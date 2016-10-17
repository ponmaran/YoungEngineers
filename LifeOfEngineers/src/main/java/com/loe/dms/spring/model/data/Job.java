package com.loe.dms.spring.model.data;

import java.io.Serializable;
import java.util.List;

import com.loe.dms.spring.model.entity.Users;

public class Job implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private String description;
	private List<Location> locations;
	private List<Contact> contacts;
	private Users sharedByUser;
	
	public Long getId(){
		return this.id;
	}
	
	public List<Location> getLocations(){
		return locations;
	}

	public String getTitle(){
		return this.title;
	}
	
	public String getDescription(){
		return description;
	}
	
	public List<Contact> getContacts(){
		return contacts;
	}

	public Users getSharedByUser(){
		return sharedByUser;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public void setLocations(List<Location> locations){
		this.locations = locations;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setContacts(List<Contact> contacts){
		this.contacts = contacts;
	}
	
	public void setSharedByUser(Users sharedByUser){
		this.sharedByUser = sharedByUser;
	}
}
