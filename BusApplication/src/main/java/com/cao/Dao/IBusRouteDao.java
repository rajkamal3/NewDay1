package com.cao.Dao;

import java.time.LocalDate;
import java.util.List;

import com.cap.Model.RouteBean;
import com.cap.Model.TransactionBean;

public interface IBusRouteDao {
	 public List<RouteBean> ViewRouteDetails();
	 public boolean AddBusDetails(RouteBean routeBean);
	 public List<TransactionBean> monthlyReport(LocalDate fromDate,LocalDate toDate);
}
