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
		String action = getAction(request);
	}

	
	
	
	private String getAction(HttpServletRequest request) {
		String action 	= "default";
		String origin 	= request.getParameter("origin");
		String exitMode	= request.getParameter("exitMode");		
		String id	 	= request.getParameter("id");
		String subId 	= request.getParameter("subId");
		
		if (origin == null || origin.length() < 1 || exitMode == null || exitMode.length() <1) return action;
		
		// Coming from List of all stateAdminUnitTypes
		if (origin.equals("listOfUnits"))
		{
			if (exitMode.equals("editUnit") &&  (id != null && id.length() > 0))
			{
				action = "showUnitForm";
			} 
			else if (exitMode.equals("removeUnit") &&  (id != null && id.length() > 0))
			{
				action = "removeUnit";
			}
			else if (exitMode.equals("addUnit"))
			{
				action = "showUnitForm";
			}
		}
		
		/*
		
		// Coming from Edit form
		if (origin.equals("stateAdminUnitTypeForm"))
		{
			if (exitMode != null && exitMode.length() > 0) 
			{
				action = exitMode;
			} 
			else if (exitMode.equals("removeSubOrdinate") && (subId == null || subId.length() < 1))
			{
				action = "default";
			}
		}
		
		
		// Coming from List of possible subOrds 
		if (origin.equals("listOfPossibileSubordinatesForStateAdminUnitType"))
		{
			if (exitMode != null && exitMode.equals("selectedSubOrdinate") && (subId != null && subId.length() > 0)) 
			{
				action = "saveSubOrdinate";
			} 
			else if (exitMode.equals("cancel") &&  (id != null && id.length() > 0)) 
			{
				action = "showStateAdminUnitTypeForm";
			}
		}
		*/
				
		return action;
	}

	

	// Show list of all units 
	private void showStateAdminUnitsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<StateAdminUnit> units = unitDao.getAllStateAdminUnits();

		request.setAttribute("units", units);
		request.getRequestDispatcher("stateAdminUnitsList.jsp").forward(request, response);		
	}


}
