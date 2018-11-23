package com.cap.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cap.Model.BusPassRequestBean;
import com.cap.service.IPendingRequest;
import com.cap.service.PendingRequestImpl;

@WebServlet("/CompleteDetails")
public class CompleteDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	IPendingRequest busservice = new PendingRequestImpl();
    public CompleteDetails() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String empid=request.getParameter("empid");

		System.out.println(empid);
		response.setContentType("text/html");
		
		List<BusPassRequestBean> pendingList=busservice.pendingDetailsOfEmp(empid);
		System.out.println(pendingList);

		PrintWriter pw=response.getWriter();

		pw.println("<html><body><h3>All Routes</h3>");
		pw.println("<table>"
				+ "<tr>"
				+ "<th>Employee Id:</th>"
				+"<th>First Name:</th>"
				+ "</tr>");

		for(BusPassRequestBean emp:pendingList) {
			pw.println("<tr>"
					+ "<td>"+emp.getEmployeeId()+"</td>"
					+"<input type='hidden' value="+emp.getEmployeeId()+" name='empid'>"
					+ "<td>"+emp.getFirstName()+"</td>"
					+"<td>"+emp.getLastName()+"</td>"
					+"<td>"+emp.getAddress()+"</td>"
					);
		}
		
		pw.println("<form action='ApprovedServlet'>"
				+ "Route Number: <input type='text' name='routeno'>"
				+ "Total Kilometers:<input type='text' name='totalkm'>"
				+ "Total Fare:<input type='text' name='totalfare'>"
				+"<input type='submit' name='approve' value='Approve'>"
				+"<input type='hidden' value="+empid+" name='empid'>"
				+"</form>"
				);
		pw.println("</table></body></html>");
	}
}