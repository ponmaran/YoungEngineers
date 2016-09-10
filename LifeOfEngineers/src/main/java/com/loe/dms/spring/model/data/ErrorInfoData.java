package com.loe.dms.spring.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorInfoData implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> errorInfos = new ArrayList<String>();

	private List<String> infoMessages = new ArrayList<String>();

	public List<String> getErrorInfos() {
		return errorInfos;
	}

	public void setErrorInfos(List<String> errorInfos) {
		this.errorInfos = errorInfos;
	}

	public List<String> getInfoMessages() {
		return infoMessages;
	}

	public void setInfoMessages(List<String> infoMessages) {
		this.infoMessages = infoMessages;
	}

	public void addInfoMessaage(String infoMessage) {
		infoMessages.add(infoMessage);
	}

	public void addAllInfoMessaage(List<String> infoMessages) {
		infoMessages.addAll(infoMessages);
	}

	public void addErrorInfo(String errorInfo) {
		errorInfos.add(errorInfo);
	}

	public void addAllErrorInfo(List<String> errorInfos) {
		errorInfos.addAll(errorInfos);
	}

	public void addErrorInfoData(ErrorInfoData errorInfoData) {
		errorInfos.addAll(errorInfoData.getErrorInfos());
	}

	public boolean hasErrors() {
		if (!errorInfos.isEmpty()) {
			return true;
		}
		return false;
	}

}
