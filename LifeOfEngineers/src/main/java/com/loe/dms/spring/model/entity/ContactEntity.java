package com.loe.dms.spring.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contacts")
public class ContactEntity  extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "method")
	private String method;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "data")
	private String data;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_id", nullable = false)
	private JobEntity job;
	
	public ContactEntity(){
		
	}
	
	public ContactEntity(ContactEntity contact){
		this.id = contact.getId();
		this.method = contact.getMethod();
		this.type = contact.getType();
		this.data = contact.getData();
		this.job = contact.getJob();
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return id;
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
	
	public void setJob(JobEntity job){
		this.job = job;
	}
	
	public JobEntity getJob(){
		return job;
	}
}