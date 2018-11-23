package com.cao.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.cap.Model.RouteBean;
import com.cap.Model.TransactionBean;

public class BusRouteDaoImpl implements IBusRouteDao{

	@SuppressWarnings("rawtypes")
	@Override
	public List<RouteBean> ViewRouteDetails() {
		@SuppressWarnings("unchecked")
		List<RouteBean> routeBean1 =new ArrayList();
		String sql ="Select * from route";
		try(
			PreparedStatement pmt = getSqlConnection().prepareStatement(sql);
			){
			ResultSet rs = pmt.executeQuery();
			while(rs.next()) {
				RouteBean routeBean2=new RouteBean();
				routeBean2.setRoutePath(rs.getString("route_path"));
				routeBean2.setRouteName(rs.getString("routename"));
				routeBean2.setOccupied(rs.getInt("occupiedseats"));
				routeBean2.setTotalSeats(rs.getInt("total_seats"));
				routeBean2.setBusNo(rs.getString("bus_no"));
				routeBean2.setDiverName(rs.getString("driver_name"));
				routeBean2.setTotalKm(rs.getInt("total_km"));
				routeBean2.setRouteId(rs.getInt("route_id"));
				routeBean1.add(routeBean2);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return routeBean1;
	}

	@SuppressWarnings("unused")
	private void populate(ResultSet rs, RouteBean routeBean) throws SQLException {
		routeBean.getRouteId();
		routeBean.setRoutePath(rs.getString("route_path"));
		routeBean.setRouteName(rs.getString("routename"));
		routeBean.setOccupied(rs.getInt("occupiedseats"));
		routeBean.setTotalSeats(rs.getInt("total_seats"));
		routeBean.setBusNo(rs.getString("bus_no"));
		routeBean.setDiverName(rs.getString("driver_name"));
		routeBean.setTotalKm(rs.getInt("total_km"));
	}

	public Connection getSqlConnection() throws SQLException {

		Connection con=null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/rajkamal","root","India123"); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  
		return con;

	}

	@Override
	public boolean AddBusDetails(RouteBean newroute) {
		String sql="insert into route(route_path,routename,occupiedseats,total_seats,bus_no,driver_name,total_km) values(?,?,?,?,?,?,?)" ; 
		try(PreparedStatement pst = getSqlConnection().prepareStatement(sql);){
			pst.setString(1, newroute.getRoutePath());
			pst.setString(2, newroute.getRouteName());
			pst.setInt(3, newroute.getOccupied());
			pst.setInt(4, newroute.getTotalSeats());
			pst.setString(5, newroute.getBusNo());
			pst.setString(6, newroute.getDiverName());
			pst.setDouble(7, newroute.getTotalKm());

			int n=pst.executeUpdate();
			System.out.println(n);
			if(n>0) {
				System.out.println(n);
				return true;
			}		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<TransactionBean> monthlyReport(LocalDate fromDate, LocalDate toDate) {
		String sql="select * from transaction where transaction_date between ? and ?";
		int tCount=0;
		try(
				PreparedStatement preparedStatement=getSqlConnection().prepareStatement(sql);
				){
			preparedStatement.setDate(1,Date.valueOf(fromDate));
			preparedStatement.setDate(2,Date.valueOf(toDate));

			ResultSet resultSet=preparedStatement.executeQuery();
			List<TransactionBean> tList=new ArrayList<>();
			while(resultSet.next()){
				tCount++;
				TransactionBean tBean=new TransactionBean();
				tBean.setTransaction_id(resultSet.getInt(1));
				tBean.setEmp_id(resultSet.getString(2));
				java.sql.Date sqlDate=resultSet.getDate("transaction_date");
				java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
				Instant instant = Instant.ofEpochMilli(utilDate.getTime()); 
				LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()); 
				LocalDate localDate = localDateTime.toLocalDate();
				tBean.setTransaction_date(localDate);
				tBean.setTotal_km(resultSet.getDouble(4));
				tBean.setMonthly_fare(resultSet.getInt(5));
				tBean.setRoute_id(resultSet.getInt(6));
				tList.add(tBean);
			}
			if(tCount>0){
				return tList;
			} else {
				return null;
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

}
