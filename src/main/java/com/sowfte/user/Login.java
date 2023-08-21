package com.sowfte.user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String dbDriver = "com.mysql.cj.jdbc.Driver"; // Use updated driver class name
    private String dbURL = "jdbc:mysql://localhost:3306/usermgt";
    private String dbUname = "root";
    private String dbPsswd = "197714191";
    private String sql = "SELECT * FROM contact where email=? AND password=?";

    Connection con = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;
        
//        System.out.println(email + password);

        try {
            Class.forName(dbDriver);
            con = DriverManager.getConnection(dbURL, dbUname, dbPsswd);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            
            PrintWriter out = response.getWriter();
            out.println(email + password);

            ResultSet user = pst.executeQuery();
            if (user.next()) {
                session.setAttribute("name", user.getString("name"));
                dispatcher = request.getRequestDispatcher("index.jsp"); // Assign dispatcher here
            } else {
                session.setAttribute("name", null);
                dispatcher = request.getRequestDispatcher("login.jsp"); // Assign dispatcher here
            }

            dispatcher.forward(request, response); // Call forward after assigning dispatcher
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
