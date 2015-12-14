package com.ken.module.phone;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ken.core.repository.support.ibatis.IBatisModelSetup;
import com.ken.module.phone.entity.PhoneHead;
import com.ken.module.phone.repository.PhoneHeadRepository;

@Service
public class PhoneHeadService {

	@Resource
	private PhoneHeadRepository dao;
	
	public PhoneHead findTempFist(String phoneNo){
		IBatisModelSetup  modelSetup = new IBatisModelSetup();
		modelSetup.addParameter("phoneNo", phoneNo);
		modelSetup.setSqlName("findTempFist");	
		
		PhoneHead phoneHead = dao.findUnique(modelSetup);
		return phoneHead;
	}
}
