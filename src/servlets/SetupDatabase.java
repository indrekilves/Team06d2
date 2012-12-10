package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.BorderGuardDao;

public class SetupDatabase extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BorderGuardDao dao = new BorderGuardDao(request, response);
        dao.createTabels();
        dao.insertDummyData();
        response.getWriter().println("Tables are created and dummy data inserted.");
	}

}
