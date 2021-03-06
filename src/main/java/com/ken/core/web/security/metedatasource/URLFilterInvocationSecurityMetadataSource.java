package com.ken.core.web.security.metedatasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.ken.core.security.auth.repository.SysResourceRepository;


public class URLFilterInvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource, InitializingBean {

	protected final Log logger = LogFactory.getLog(getClass());

	private final static List<ConfigAttribute> NULL_CONFIG_ATTRIBUTE = Collections
			.emptyList();

	private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;
	@Autowired
	private SysResourceRepository sysResourceRepository;

	@Value("${RES_KEY}")
	private String RES_KEY;
	@Value("${AUTH_KEY}")
	private String AUTH_KEY;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.SecurityMetadataSource#getAttributes
	 * (java.lang.Object)
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		final HttpServletRequest request = ((FilterInvocation) object)
				.getRequest();

		Collection<ConfigAttribute> attrs = NULL_CONFIG_ATTRIBUTE;

		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
				.entrySet()) {
			if (entry.getKey().matches(request)) {
				attrs = entry.getValue();
				break;
			}
		}
		logger.info("Request Url资源" + request.getRequestURI() + " -> " + attrs);
		return attrs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.access.SecurityMetadataSource#
	 * getAllConfigAttributes()
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
				.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}

		return allAttributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.SecurityMetadataSource#supports(java
	 * .lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	private Map<String, String> loadResuorce() {
		Map<String, String> map = new LinkedHashMap<String, String>();

		List<Map<String, String>> list = this.sysResourceRepository
				.getResourceMapping();
		Iterator<Map<String, String>> it = list.iterator();
		while (it.hasNext()) {
			Map<String, String> rs = it.next();
			String resourcePath = rs.get(RES_KEY);
			String authorityMark = rs.get(AUTH_KEY);

			if (map.containsKey(resourcePath)) {
				String mark = map.get(RES_KEY);
				map.put(resourcePath, mark + "," + authorityMark);
			} else {
				map.put(resourcePath, authorityMark);
			}
		}
		return map;
	}

	protected Map<RequestMatcher, Collection<ConfigAttribute>> bindRequestMap() {
		Map<RequestMatcher, Collection<ConfigAttribute>> map = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

		Map<String, String> resMap = this.loadResuorce();
		for (Map.Entry<String, String> entry : resMap.entrySet()) {
			String key = entry.getKey();
			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
			atts = SecurityConfig.createListFromCommaDelimitedString(entry
					.getValue());
			map.put(new AntPathRequestMatcher(key), atts);
		}

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		this.requestMap = this.bindRequestMap();
		logger.info("资源权限列表" + this.requestMap);
	}

	public void refreshResuorceMap() {
		this.requestMap = this.bindRequestMap();
	}

}
