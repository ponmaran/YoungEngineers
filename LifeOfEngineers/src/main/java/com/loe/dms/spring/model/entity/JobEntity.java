package com.loe.dms.spring.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "job")//@IdClass(JobPK.class)
public class JobEntity extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "job_id")
	private Long jobId;

	@Transient
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "job")
	private List<LocationEntity> locations;
	
	@Column(name = "job_title")
	private String jobTitle;
	
	@Column(name = "job_desc")
	private String jobDescription;
	
	@Transient
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "job")
	private List<ContactEntity> contacts;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "shared_by_user")
	private Users sharedByUser;
	
	public Long getId(){
		return this.jobId;
	}
	
	public void setJobId(Long jobId){
		this.jobId = jobId;
	}
	
	public List<LocationEntity> getLocations(){
		return locations;
	}

	public void setLocations(List<LocationEntity> locations){
		this.locations = locations;
	}
	
	public String getTitle(){
		return this.jobTitle;
	}
	
	public void setJobTitle(String jobTitle){
		this.jobTitle = jobTitle;
	}
	
	public String getDescription(){
		return jobDescription;
	}
	
	public void setJobDescription(String jobDescription){
		this.jobDescription = jobDescription;
	}
	
	public List<ContactEntity> getContacts(){
		return contacts;
	}

	public void setContacts(List<ContactEntity> contacts){
		this.contacts = contacts;
	}
	
	public Users getSharedByUser(){
		return sharedByUser;
	}
	
	public void setSharedByUser(Users sharedByUser){
		this.sharedByUser = sharedByUser;
	}
}