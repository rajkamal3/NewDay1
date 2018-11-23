package com.cao.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.cap.Model.BusPassRequestBean;
import com.cap.Model.TransactionBean;

public class PendingRequestDaoImpl implements IPendingRequestDao{

	@Override
	public List<String> viewRequestDetails() {
		
		String sql = "select EmployeeId from buspassrequest where status=?";
		List<String> IdList = new ArrayList<>();
		try(
				PreparedStatement pmt =getSqlConnection().prepareStatement(sql)
				){
			@SuppressWarnings("unused")
			BusPassRequestBean busPassRequestBean = new BusPassRequestBean();
			
			pmt.setString(1, "Pending");
			ResultSet rs = pmt.executeQuery();
			while(rs.next()) {
				String id =  rs.getString("EmployeeId");
				System.out.println(id);
				IdList.add(id);
			}
		} catch(SQLException e) { 
			e.printStackTrace();
		}
		return IdList;
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
	public List<BusPassRequestBean> pendingDetailsOfEmp(String empid) {
		
		List<BusPassRequestBean> bean1 = new ArrayList<BusPassRequestBean>();
		String sql ="select * from buspassrequest where EmployeeId=?";
		BusPassRequestBean bean = new BusPassRequestBean();
		
		try(
				Connection connection= getSqlConnection();
				PreparedStatement pmt =getSqlConnection().prepareStatement(sql)
				
				){
			pmt.setString(1, empid);
			ResultSet resultSet= pmt.executeQuery();
			while(resultSet.next()) {
				populate(bean,resultSet);
				bean1.add(bean);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return bean1;
	}

	private void populate(BusPassRequestBean busPassRequestBean, ResultSet rs) throws SQLException {
		busPassRequestBean.setEmployeeId(rs.getString("EmployeeId"));
		busPassRequestBean.setFirstName(rs.getString("firstname"));
		busPassRequestBean.setLastName(rs.getString("lastname"));
		busPassRequestBean.setGender(rs.getString("gender"));
		busPassRequestBean.setAddress(rs.getString("address"));
		java.sql.Date sqlDate=rs.getDate("dateofjoin");
		java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
		Instant instant = Instant.ofEpochMilli(utilDate.getTime()); 
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()); 
		LocalDate localDate = localDateTime.toLocalDate();
		busPassRequestBean.setDateOfJoining(localDate);
		busPassRequestBean.setLocation(rs.getString("location"));
		busPassRequestBean.setPickUpLoc(rs.getString("pickuploc"));
		Time time=rs.getTime("pickuptime");
		LocalTime localTime=time.toLocalTime();
		busPassRequestBean.setPickUpTime(localTime);
		busPassRequestBean.setStatus(rs.getString("status"));
		busPassRequestBean.setDesignation(rs.getString("designation"));
		System.out.println(busPassRequestBean);
	}

	@Override
	public Integer transaction(TransactionBean transaction) {
		String sql="insert into transaction(employeeId,transaction_date,calculated_km,monthly_fare,route_id) values(?,?,?,?,?)";
		String sql1="update buspassrequest set status=? where employeeId=?";
		String sql2="update route set occupiedseats=occupiedseats+1 where route_id=?";
		try(PreparedStatement preparedStatement = getSqlConnection().prepareStatement(sql);
				PreparedStatement preparedStatement2 = getSqlConnection().prepareStatement(sql1);
				PreparedStatement preparedStatement1 = getSqlConnection().prepareStatement("select transaction_id from transaction where employeeId=?");
				PreparedStatement preparedStatement3 = getSqlConnection().prepareStatement(sql2);
				){
			preparedStatement.setString(1,transaction.getEmp_id());
			preparedStatement.setDate(2, Date.valueOf(transaction.getTransaction_date()));
			preparedStatement.setDouble(3, transaction.getTotal_km());
			preparedStatement.setInt(4, transaction.getMonthly_fare());
			preparedStatement.setInt(5, transaction.getRoute_id());
			preparedStatement1.setString(1,transaction.getEmp_id());
			preparedStatement2.setString(1,"Approved");
			preparedStatement2.setString(2, transaction.getEmp_id());
			preparedStatement3.setInt(1,transaction.getRoute_id());
			 
			int n=preparedStatement.executeUpdate();
			
			if(n>0) {
				ResultSet resultSet = preparedStatement1.executeQuery();
				if(resultSet.next()) {
					Integer transaction_id = resultSet.getInt(1);
					int n1=preparedStatement2.executeUpdate();
					int n2=preparedStatement3.executeUpdate();
					if(n1>0 && n2>0)
						return transaction_id;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
