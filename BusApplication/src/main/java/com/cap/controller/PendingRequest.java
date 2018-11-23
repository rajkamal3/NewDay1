package com.cap.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cap.service.IPendingRequest;
import com.cap.service.PendingRequestImpl;

@WebServlet("/PendingRequest")
public class PendingRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IPendingRequest service = new PendingRequestImpl();
    public PendingRequest() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Im in");
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
		List<String> idList = new ArrayList<>();
		idList=service.viewRequestDetails();
		out.println("<html><body><h3>All Routes</h3>");
		out.println("<table>"
				+ "<tr>"
				+ "<th>Employee ID</th>"
				+ "</tr>");
		Iterator<String> iterator = idList.iterator();
		while(iterator.hasNext()){
			String s=iterator.next();
			out.println("<form action='CompleteDetails' method='post'>"
					+ "<p name='empid'>"+s+"</p>"
					+"<input type='hidden' value="+s+" name='empid'>"
					+"<input type='submit' value='View' name='view'>"
					+"</form>"
					);
		}
		out.println("</table></body></html>");
	}

}