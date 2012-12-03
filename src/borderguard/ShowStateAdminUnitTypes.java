package borderguard;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.StateAdminUnitType;

import dao.StateAdminUnitTypeDao;


public class ShowStateAdminUnitTypes extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     		
		StateAdminUnitTypeDao dao = new StateAdminUnitTypeDao(request, response);
        List<StateAdminUnitType> stateAdminUnitTypes = dao.getAllStateAdminUnitTypes();
        
        
        for (StateAdminUnitType stateAdminUnitType : stateAdminUnitTypes) {
        	response.getWriter().println(stateAdminUnitType);
		}
        
//        request.setAttribute("stateAdminUnitTypes", stateAdminUnitTypes);
//		request.getRequestDispatcher("showStateAdminUnitTypes.jsp").forward(request, response);
	    
	}
	
}
