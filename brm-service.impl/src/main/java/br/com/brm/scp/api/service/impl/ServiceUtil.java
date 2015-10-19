package br.com.brm.scp.api.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ServiceUtil {

	static Pageable constructPageSpecification(int pageIndex, int size, Sort sort) {
		Pageable pageSpecification = new PageRequest(pageIndex, size, sort);
		return pageSpecification;
	}
	
}