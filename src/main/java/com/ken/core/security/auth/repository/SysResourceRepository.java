package com.ken.core.security.auth.repository;

import java.util.List;
import java.util.Map;

public interface SysResourceRepository {
	public List<Map<String,String>> getResourceMapping();
}
