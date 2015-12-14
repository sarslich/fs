package com.ken.module.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.security.access.annotation.Secured;

import com.ken.core.web.action.GenericAction;

@Namespace("/")
@ParentPackage("default")
public class TestAction  extends GenericAction{

	@Action( value = "/assets/index", results = { @Result(name = SUCCESS, location = "/assets/view/index.html")  })
	public String index() {
		return SUCCESS;
	}
}
