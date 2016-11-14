package com.loe.dms.spring.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact  {

	private Long contactId;
	private String method;
	private String type;
	private String data;
	private Job job;
	
	public Contact(){
		
	}
	
	public Contact(Contact contact){
		this.contactId = contact.getId();
		this.method = contact.getMethod();
		this.type = contact.getType();
		this.data = contact.getData();
		this.job = contact.getJob();
	}
	
	public Contact(Long id, String method, String type, String data, Job job){
		this.contactId = id;
		this.method = method;
		this.type = type;
		this.data = data;
		this.job = job;
	}
	
	public void setId(Long contactId){
		this.contactId = contactId;
	}
	
	public Long getId(){
		return contactId;
	}
	
	public void setMethod(String method){
		this.method = method;
	}
	
	public String getMethod(){
		return method;
	}
	
	public void setType(String type){
		this.type = type;		
	}
	
	public String getType(){
		return type;
	}
	
	public void setData(String data){
		this.data = data;
	}
	
	public String getData(){
		return data;
	}
	
	public void setJob(Job job){
		this.job = job;
	}
	
	public Job getJob(){
		return job;
	}
}