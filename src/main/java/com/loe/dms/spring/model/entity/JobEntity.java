package com.loe.dms.spring.model.entity;

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

import com.sun.jmx.snmp.Timestamp;

@Entity
@Table(name = "job")
public class JobEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "sharedByUser")
	private Users sharedByUser;
	
	@Column(name = "start_time")
	private Timestamp startTime;
	
	@Column(name = "end_time")
	private Timestamp endTime;

	@Column(name = "update_time")
	private Timestamp updatedTime;

	@Transient
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "job")
	private List<LocationEntity> locations;
	
	@Transient
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "job")
	private List<ContactEntity> contacts;
	
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<LocationEntity> getLocations(){
		return locations;
	}

	public void setLocations(List<LocationEntity> locations){
		this.locations = locations;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
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

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}
}