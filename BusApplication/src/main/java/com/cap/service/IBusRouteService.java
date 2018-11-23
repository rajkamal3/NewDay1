package com.cap.service;

import java.time.LocalDate;
import java.util.List;

import com.cap.Model.RouteBean;
import com.cap.Model.TransactionBean;

public interface IBusRouteService {
	 public List<RouteBean> ViewRouteDetails();
	 public boolean AddBusDetails(RouteBean routeBean);
	 public abstract Integer transaction(TransactionBean transaction);
	 public List<TransactionBean> monthlyReport(LocalDate fromDate,LocalDate toDate);
}
