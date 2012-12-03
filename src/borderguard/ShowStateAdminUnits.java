package borderguard;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.StateAdminUnit;

import dao.StateAdminUnitDao;


public class ShowStateAdminUnits extends HttpServlet {

	 
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StateAdminUnitDao dao = new StateAdminUnitDao(request, response);
        List<StateAdminUnit> stateAdminUnits = dao.getAllStateAdminUnits();
        
        
        for (StateAdminUnit stateAdminUnit : stateAdminUnits) {
        	response.getWriter().println(stateAdminUnit);	
		}
	}

	
}
