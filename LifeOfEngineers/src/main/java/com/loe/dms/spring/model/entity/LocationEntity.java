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
@Table(name = "locations")
public class LocationEntity extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "country")
	private String country;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "city")
	private String city;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_id", nullable = false)
	private JobEntity job;

	public LocationEntity(){
		
	}
	
	public LocationEntity(LocationEntity location){
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
	
	public JobEntity getJob(){
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
	
	public void setJob(JobEntity job){
		this.job = job;
	}
}