package com.loe.dms.spring.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loe.dms.spring.data.util.DataTransformationUtil;
import com.loe.dms.spring.model.data.ErrorInfoData;
import com.loe.dms.spring.model.data.RegistrationInfo;
import com.loe.dms.spring.model.data.RegistrationInfoWrapper;
import com.loe.dms.spring.model.data.ServiceResponse;
import com.loe.dms.spring.model.entity.Users;
import com.loe.dms.spring.service.RegistrationService;
import com.loe.dms.spring.util.EmailSenderUtil;
import com.loe.dms.spring.util.SecurityUtil;
import com.loe.dms.spring.util.WebUtil;
import com.loe.dms.spring.validator.RegistrationValidator;

@RestController
@RequestMapping("/rest/registration")
public class RegistrationRestController {
	private static final Logger logger = LoggerFactory.getLogger(RegistrationRestController.class);

	private RegistrationService registrationService;

	@Autowired
	private RegistrationValidator validator;

	@Autowired(required = true)
	@Qualifier(value = "registrationService")
	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@RequestMapping(value = "/enroll", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegistrationInfoWrapper> continueRegistration(@RequestBody RegistrationInfo registrationInfo, BindingResult result) {
		logger.info(">>>>> continueRegistration >>>>");
		ErrorInfoData errorInfoData = new ErrorInfoData();
		RegistrationInfoWrapper registrationInfoWrapper = new RegistrationInfoWrapper();
		try {
			// Validation code
			validator.validate(registrationInfo, result);
			Users users = DataTransformationUtil.prepareRegistrationUsersBusinessData(registrationInfo);

			// Check validation errors
			if (result.hasErrors()) {
				errorInfoData.addErrorInfoData(WebUtil.prepareErrorInfoData(result));
				registrationInfoWrapper.setErrorInfoData(errorInfoData);
				registrationInfoWrapper.setStatus("E");
				return new ResponseEntity<RegistrationInfoWrapper>(registrationInfoWrapper, HttpStatus.OK);
			}

			ServiceResponse serviceResponse = registrationService.enrollRegistrationDetails(users);
			if (serviceResponse != null && serviceResponse.hasErrors()) {
				registrationInfoWrapper.setErrorInfoData(serviceResponse.getErrorInfoData());
				registrationInfoWrapper.setStatus("E");
				return new ResponseEntity<RegistrationInfoWrapper>(registrationInfoWrapper, HttpStatus.OK);
			}

			registrationService.emailVerification(users.getUser_id(), "M");
		} catch (Exception e) {
			logger.error("Exception occured in Service :::: continueRegistration  :::: " + e.getMessage());
			buildSystemFailureError(registrationInfoWrapper, errorInfoData, e);
		}

		return new ResponseEntity<RegistrationInfoWrapper>(registrationInfoWrapper, HttpStatus.OK);
	}

	@RequestMapping(value = "/performlogin", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegistrationInfoWrapper> performLogin(@RequestBody RegistrationInfo registrationInfo, BindingResult result) {
		logger.info(">>>>> performLogin >>>>");
		ErrorInfoData errorInfoData = new ErrorInfoData();
		RegistrationInfoWrapper registrationInfoWrapper = new RegistrationInfoWrapper();
		List<RegistrationInfo> listOfUsersInfo = new ArrayList<RegistrationInfo>();
		try {
			// Validation code
			validator.validateLogin(registrationInfo, result);

			// Check validation errors
			if (result.hasErrors()) {
				errorInfoData.addErrorInfoData(WebUtil.prepareErrorInfoData(result));
				registrationInfoWrapper.setErrorInfoData(errorInfoData);
				registrationInfoWrapper.setStatus("E");
				return new ResponseEntity<RegistrationInfoWrapper>(registrationInfoWrapper, HttpStatus.OK);
			}
			Users users = DataTransformationUtil.prepareLoginUsersBusinessData(registrationInfo);
			Users retrievedUser = registrationService.performUserLogin(users);
			if (retrievedUser == null) {
				errorInfoData.addErrorInfo("Entered email is not registered in system,please try to register or login with valid email id.");
				registrationInfoWrapper.setErrorInfoData(errorInfoData);
				registrationInfoWrapper.setStatus("E");
				return new ResponseEntity<RegistrationInfoWrapper>(registrationInfoWrapper, HttpStatus.OK);
			} else {
				if (registrationInfo.getUser_name().equalsIgnoreCase(retrievedUser.getUser_name()) && registrationInfo.getPassword().equalsIgnoreCase(SecurityUtil.decrypt(retrievedUser.getPassword()))) {
					List<Users> listOfRegisteredUsers = registrationService.fetchRegisteredUsers();
					if (listOfRegisteredUsers != null && !listOfRegisteredUsers.isEmpty()) {
						RegistrationInfo retrievedRegInf = null;
						for (Users user : listOfRegisteredUsers) {
							retrievedRegInf = new RegistrationInfo();
							retrievedRegInf.setFirst_name(user.getRegistrationDetails().getFirst_name());
							retrievedRegInf.setLast_name(user.getRegistrationDetails().getLast_name());
							retrievedRegInf.setUser_name(user.getUser_name());
							retrievedRegInf.setMobile_num(user.getMobile_num());
							listOfUsersInfo.add(retrievedRegInf);
						}
						registrationInfoWrapper.setRegisteredUsersCount(listOfUsersInfo.size());
						registrationInfoWrapper.setRegistrationInfo(listOfUsersInfo);
					}
				} else {
					errorInfoData.addErrorInfo("Entered password does not match with the registered password,please try with valid password.");
					registrationInfoWrapper.setErrorInfoData(errorInfoData);
					registrationInfoWrapper.setStatus("E");
					return new ResponseEntity<RegistrationInfoWrapper>(registrationInfoWrapper, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			logger.error("Exception occured in Service :::: performLogin  :::: " + e.getMessage());
			buildSystemFailureError(registrationInfoWrapper, errorInfoData, e);
		}
		return new ResponseEntity<RegistrationInfoWrapper>(registrationInfoWrapper, HttpStatus.OK);
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegistrationInfoWrapper> sendPassword(@RequestBody RegistrationInfo registrationInfo, BindingResult result) {
		logger.info(">>>>> sendPassword >>>>");
		ErrorInfoData errorInfoData = new ErrorInfoData();
		RegistrationInfoWrapper registrationInfoWrapper = new RegistrationInfoWrapper();
		try {
			// Validation code
			validator.validateEmailId(registrationInfo, result);

			// Check validation errors
			if (result.hasErrors()) {
				errorInfoData.addErrorInfoData(WebUtil.prepareErrorInfoData(result));
				registrationInfoWrapper.setErrorInfoData(errorInfoData);
				registrationInfoWrapper.setStatus("E");
				return new ResponseEntity<RegistrationInfoWrapper>(registrationInfoWrapper, HttpStatus.OK);
			}

			Users users = DataTransformationUtil.prepareLoginUsersBusinessData(registrationInfo);
			Users retrievedUser = registrationService.performUserLogin(users);
			if (retrievedUser == null) {
				errorInfoData.addErrorInfo("Entered email is not registered in system,please try to register or login with valid email id.");
				registrationInfoWrapper.setErrorInfoData(errorInfoData);
				registrationInfoWrapper.setStatus("E");
				return new ResponseEntity<RegistrationInfoWrapper>(registrationInfoWrapper, HttpStatus.OK);
			} else {
				boolean emailSuccess = EmailSenderUtil.sendPasswordMessage(retrievedUser.getUser_name(), SecurityUtil.decrypt(retrievedUser.getPassword()));
				if (emailSuccess) {
					errorInfoData.addInfoMessaage("System registered password mailed to your email,please try login with that or contact admin.");
					registrationInfoWrapper.setErrorInfoData(errorInfoData);
				} else {
					errorInfoData.addErrorInfo("We are facing system difficulties while sending password,please contact admin.");
					registrationInfoWrapper.setStatus("E");
					registrationInfoWrapper.setErrorInfoData(errorInfoData);
				}
			}
		} catch (Exception e) {
			logger.error("Exception occured in Service :::: sendPassword  :::: " + e.getMessage());
			buildSystemFailureError(registrationInfoWrapper, errorInfoData, e);
		}
		return new ResponseEntity<RegistrationInfoWrapper>(registrationInfoWrapper, HttpStatus.OK);
	}

	public void buildSystemFailureError(RegistrationInfoWrapper registrationInfoWrapper, ErrorInfoData errorInfoData, Exception e) {
		errorInfoData.addErrorInfo("Application Runtime Error Occurred, Please retry by providing correct data.");
		if (e != null && e.getMessage() != null) {
			if (e.getMessage().length() > 50) {
				errorInfoData.addErrorInfo(e.getMessage().substring(0, 50));
			} else {
				errorInfoData.addErrorInfo(e.getMessage());
			}
		}
		registrationInfoWrapper.setErrorInfoData(errorInfoData);
		registrationInfoWrapper.setStatus("E");
	}

}
