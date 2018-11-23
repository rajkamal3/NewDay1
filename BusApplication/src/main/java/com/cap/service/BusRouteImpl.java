package com.cap.service;

import java.time.LocalDate;
import java.util.List;

import com.cao.Dao.BusRouteDaoImpl;
import com.cao.Dao.IBusRouteDao;
import com.cao.Dao.IPendingRequestDao;
import com.cao.Dao.PendingRequestDaoImpl;
import com.cap.Model.RouteBean;
import com.cap.Model.TransactionBean;

public class BusRouteImpl implements IBusRouteService{
	
	IPendingRequestDao pendingDao =new PendingRequestDaoImpl();

	IBusRouteDao dao= new BusRouteDaoImpl();
	
	@Override
	public List<RouteBean> ViewRouteDetails() {
		List<RouteBean> routebean = dao.ViewRouteDetails();
		return routebean;
	}

	@Override
	public boolean AddBusDetails(RouteBean routeBean) {
		if(dao.AddBusDetails(routeBean)) {
			return true;
		}
		return false;
	}

	@Override
	public Integer transaction(TransactionBean transaction) {
		Integer transaction_id=pendingDao.transaction(transaction);
		return transaction_id;
		
	}

	@Override
	public List<TransactionBean> monthlyReport(LocalDate fromDate, LocalDate toDate) {
		 List<TransactionBean> trans=dao.monthlyReport(fromDate, toDate);
		return trans;
	}


}
