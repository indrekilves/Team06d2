package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.BorderGuardDao;

public class DatabaseController extends HttpServlet {

	private static final long serialVersionUID = -391959563520044178L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BorderGuardDao dao = new BorderGuardDao(request, response);
        dao.createTabels();
        dao.insertDummyData();
		request.getRequestDispatcher("pages/database.jsp").forward(request, response);       
	}

}
