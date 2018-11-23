package com.cap.service;

import java.util.List;

import com.cap.Model.BusPassRequestBean;

public interface IPendingRequest {
	public List<String> viewRequestDetails();
	public List<BusPassRequestBean> pendingDetailsOfEmp(String empid);
}
