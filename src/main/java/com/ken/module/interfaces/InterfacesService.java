package com.ken.module.interfaces;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ken.core.repository.support.ibatis.IBatisModelSetup;
import com.ken.module.interfaces.entity.Interfaces;
import com.ken.module.interfaces.repository.InterfacesRepository;

@Service
public class InterfacesService {
	@Resource
	private InterfacesRepository dao;
	
	public Interfaces cacheGet(String methodName){
		IBatisModelSetup  modelSetup = new IBatisModelSetup();
		modelSetup.addParameter("interfaceName", methodName);
		modelSetup.setSqlName("findInterfaceByName");		
		
		Interfaces itf = dao.findUnique(modelSetup);
		return itf;
	}
}
