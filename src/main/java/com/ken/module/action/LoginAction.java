package com.ken.module.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.ken.core.web.action.GenericAction;

public class LoginAction extends GenericAction{
	
	@Action( value = "/assets/loginauth", results = { @Result(name = SUCCESS, location = "/assets/login.ftl")  })
	public String loginfaile() {
		return SUCCESS;
	}

}
