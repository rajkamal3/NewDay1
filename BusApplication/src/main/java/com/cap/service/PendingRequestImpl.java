package com.cap.service;

import java.util.ArrayList;
import java.util.List;

import com.cao.Dao.IPendingRequestDao;
import com.cao.Dao.PendingRequestDaoImpl;
import com.cap.Model.BusPassRequestBean;

public class PendingRequestImpl implements IPendingRequest{
	
	IPendingRequestDao pendingDao = new PendingRequestDaoImpl();

	@Override
	public List<String> viewRequestDetails() {
		
		List<String> idList = new ArrayList<>();
		idList=pendingDao.viewRequestDetails();
		
		return idList;
	}

	@Override
	public List<BusPassRequestBean> pendingDetailsOfEmp(String empid) {
		List<BusPassRequestBean> bean = pendingDao.pendingDetailsOfEmp(empid);
		return bean;
	}

}
