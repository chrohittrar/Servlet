
package random;

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

@WebServlet("/registrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		String path = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/shoppingdb";
		String user = "root";
		String pwd = "shreya";
		try {
			Class.forName(path);
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("Driver class Founded and dB Connected Succesfully");
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

		String query = "insert into users values (?,?,?,?,?,?,?)";
		String u_id = request.getParameter("u_id");
		String pwd = request.getParameter("pwd");
		String fname = request.getParameter("fname");
		String mname = request.getParameter("mname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(query);

			ps.setString(1, u_id);
			ps.setString(2, pwd);
			ps.setString(3, fname);
			ps.setString(4, mname);
			ps.setString(5, lname);
			ps.setString(6, email);
			ps.setString(7, contact);

			int n = ps.executeUpdate();

			if (n > 0) {
				response.getWriter().append("Registered Succesfully");
			} else {
				response.getWriter().append("Registered Failed");
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
