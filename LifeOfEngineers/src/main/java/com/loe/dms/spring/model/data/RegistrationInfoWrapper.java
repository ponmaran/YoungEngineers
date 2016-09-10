package com.loe.dms.spring.model.data;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationInfoWrapper extends ServiceResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status = "S";

	private int registeredUsersCount = 0;

	private List<RegistrationInfo> registrationInfo = null;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRegisteredUsersCount() {
		return registeredUsersCount;
	}

	public void setRegisteredUsersCount(int registeredUsersCount) {
		this.registeredUsersCount = registeredUsersCount;
	}

	public List<RegistrationInfo> getRegistrationInfo() {
		return registrationInfo;
	}

	public void setRegistrationInfo(List<RegistrationInfo> registrationInfo) {
		this.registrationInfo = registrationInfo;
	}

}
