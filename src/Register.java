import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {

	/**
	 * @author Prashantchitte
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// retrive the request parameter
		String username = req.getParameter("username");

		// retrive the context parameter
		ServletContext ctx = getServletContext();
		String dbdriver = ctx.getInitParameter("dbdriver");
		String dburl = ctx.getInitParameter("dburl");
		String dbuser = ctx.getInitParameter("dbuser");
		String dbpass = ctx.getInitParameter("dbpass");

		// generate the response
		PrintWriter out = res.getWriter();
		out.print("<html><body bgcolor='#CCFF99'><center>");
		try {
			Class.forName(dbdriver);
			Connection con = DriverManager.getConnection(dburl, dbuser, dbpass);
			String query = "select username from usertbl where username=?";
			PreparedStatement stat = con.prepareStatement(query);
			stat.setString(1, username);
			ResultSet rs = stat.executeQuery();
			if (rs.next()) {
				out.print("<h1 style='color:red'>Hello " + username + "!!!<br> How Are You???");
			} else {
				out.print("<h1 style='color:red'>User Not Found!!!</h1>");
			}
			stat.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.print("<br><br><a href='Register.html'>back</a>");

		res.setContentType("text/html");// setting the content type

		// writing html in the stream
		out.println("Welcome to servlet");
		out.println("</body></html>");

		out.close();// closing the stream
	}

}
