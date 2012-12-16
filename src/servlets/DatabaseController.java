package servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.BorderGuardDao;

public class DatabaseController extends HttpServlet {

	private static final long serialVersionUID = -391959563520044178L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String clearDbLock = request.getParameter("clearDbLock");
		if (clearDbLock != null && clearDbLock.equals("true")){
			clearDbLock(request, response);
		}
		else{
			BorderGuardDao dao = new BorderGuardDao(request, response);
	        dao.createTabels();
	        dao.insertDummyData();
			request.getRequestDispatcher("pages/database.jsp").forward(request, response);       
		}
	}

	
	private void clearDbLock(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clearResult = "";
		
		try {
			
			File lockfile = new File("/usr/share/tomcat7/i377/Team06d/db.lck");
			
			if (lockfile.delete()) {
				clearResult = lockfile.getName() + " is deleted.";
			} else {
				clearResult = "Lock file delete failed: "+lockfile.getAbsolutePath();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		request.setAttribute("clearResult", clearResult);
		request.getRequestDispatcher("pages/lockCleared.jsp").forward(request, response);       

	}
	
	
}
