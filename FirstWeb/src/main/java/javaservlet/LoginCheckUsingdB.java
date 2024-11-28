package javaservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loginCheckUsingdB")
public class LoginCheckUsingdB extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoppingdb", "root", "shreya");
			System.out.println("Connection done");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Drier not found");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection Failed");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userid = request.getParameter("uid");
		String pswd = request.getParameter("pwd");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select u_id, password from users where u_id=? and password=?");
			ps.setString(1, userid);
			ps.setString(2, pswd);

			rs = ps.executeQuery();

			if (rs.next()) {
				response.getWriter().append("Login Successful");
			} else {
				response.sendRedirect("/FirstWeb/login.html");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
