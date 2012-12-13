package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StateAdminUnitDao;
import dao.StateAdminUnitTypeDao;

import beans.StateAdminUnit;
import beans.StateAdminUnitType;

public class StateAdminUnitController extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	StateAdminUnitDao unitDao = new StateAdminUnitDao();
   
	// GET
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showStateAdminUnitsList(request, response);
	}


	// POST 
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	
	
	// Show list of all units 
	private void showStateAdminUnitsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<StateAdminUnit> units = unitDao.getAllStateAdminUnits();

		request.setAttribute("units", units);
		request.getRequestDispatcher("stateAdminUnitsList.jsp").forward(request, response);		
	}


}
