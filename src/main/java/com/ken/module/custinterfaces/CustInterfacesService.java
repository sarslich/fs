package com.ken.module.custinterfaces;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ken.core.repository.support.ibatis.IBatisModelSetup;
import com.ken.module.custinterfaces.entity.CustInterfaces;
import com.ken.module.custinterfaces.repository.CustInterfacesRepository;

@Service
public class CustInterfacesService {
	
	@Resource
	private CustInterfacesRepository dao;
	
	public CustInterfaces cacheGet(String interfaceId,String customerId){
		IBatisModelSetup  modelSetup = new IBatisModelSetup();
		modelSetup.addParameter("interfaceId", interfaceId);
		modelSetup.addParameter("customerId", customerId);
		modelSetup.setSqlName("hasPermission");		
		
		CustInterfaces ctif = dao.findUnique(modelSetup);
		return ctif;
	}
}
