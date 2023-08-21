package com.sowfte.user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
//    private String dbDriver = "com.mysql.cj.jdbc.Driver"; // Use updated driver class name
    private String dbDriver = "com.mysql.jdbc.Driver"; // Use updated driver class name
	private String dbURL = "jdbc:mysql://localhost:3306/usermgt";
	private String dbUname = "root";
	private String dbPsswd = "197714191";
	private String sql = "INSERT INTO contact (email, phone,name,password) VALUES (?,?,?,?)";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		String phone = request.getParameter("contact");
//		out.println("Server is good...\n");
		
//		out.println(name+email+password+phone);
		Connection con = null;
		RequestDispatcher dispatcher = null;
		try {
//			load the database driver class
			Class.forName(dbDriver);
//			create connection
			 con = DriverManager.getConnection(dbURL, dbUname, dbPsswd);
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, email);
			pst.setString(2, phone);
			pst.setString(3, name);
			pst.setString(4, password);
			
			int newItem = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			
			if(newItem > 0) {
				request.setAttribute("status", "success");
			}else {
				request.setAttribute("status", "failed");

			}
			dispatcher.forward(request, response);
		}catch(Exception error) {
			error.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
