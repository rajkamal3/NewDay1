package com.cao.Dao;

import java.util.List;

import com.cap.Model.BusPassRequestBean;
import com.cap.Model.TransactionBean;

public interface IPendingRequestDao {
	public List<String> viewRequestDetails();
	public List<BusPassRequestBean> pendingDetailsOfEmp(String empid);
	public Integer transaction(TransactionBean transaction);
}
