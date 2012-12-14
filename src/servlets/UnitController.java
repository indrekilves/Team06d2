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

public class UnitController extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	StateAdminUnitDao unitDao = new StateAdminUnitDao();
   
	
	// GET
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showUnitsList(request, response);
	}


	// POST 
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatchRequest(request, response);
	}

	
	
	
	// Dispatcher
	
	
	
	
	private void dispatchRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getAction(request);
		
		if (action.equals("default") || action.equals("cancelForm")) 
		{
			showUnitsList(request, response);
		} 
		else if (action.equals("showUnitForm"))
		{
			showUnitForm(request, response);
		} 
		else if (action.equals("saveForm"))
		{
			saveUnitFrom(request, response);
		} 
		else if (action.equals("removeUnit"))
		{
			removeUnit(request, response);
		}
		else if (action.equals("removeSubOrdinate"))
		{
			removeSubOrdinate(request, response);
		} 
		else if (action.equals("addSubOrdinate"))
		{
			addSubOrdinate(request, response);
		} 
		else if (action.equals("saveSubOrdinate"))
		{
			saveSubOrdinate(request, response);
		} 
	}


	private String getAction(HttpServletRequest request) {
		String action 		= "default";
		String origin 		= request.getParameter("origin");
		String exitMode		= request.getParameter("exitMode");		
		String id	 		= request.getParameter("id");
		String subId 		= request.getParameter("subId");
		
		if (origin == null || origin.length() < 1 || exitMode == null || exitMode.length() <1) return action;
		
		// Coming from List of all stateAdminUnits
		if (origin.equals("unitsList"))
		{
			if (exitMode.equals("showSelectedEntry") &&  (id != null && id.length() > 0))
			{
				action = "showUnitForm";
			} 
			else if (exitMode.equals("removeSelectedEntry") &&  (id != null && id.length() > 0))
			{
				action = "removeUnit";
			}
			else if (exitMode.equals("addEntry"))
			{
				action = "showUnitForm";
			}
		}
		
		
		
		// Coming from Edit form
		if (origin.equals("unitForm"))
		{
			if (exitMode.equals("removeSubOrdinate") && (subId == null || subId.length() < 1))
			{
				action = "default";
			} 
			else 
			{
				action = exitMode;
			}
		}
		
		
		/*
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

	
	
	
	// Actions


	

	private void saveUnitFrom(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("saveFrom");
		
	}


	private void removeUnit(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("removeUnit");
		
	}


	private void removeSubOrdinate(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("removeSubOrdinate");
		
	}


	private void addSubOrdinate(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("addSubOrdinate");

		
	}


	private void saveSubOrdinate(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("saveSubOrdinate");
		
	}


	// Show unit form
	
	
	private void showUnitForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("showUnitForm");

		StateAdminUnit unit = null;
		List<StateAdminUnit> bossUnits = null;		
		String strId = request.getParameter("id");

		if (strId != null && strId.length() > 0){
			Integer id = Integer.parseInt(strId);
			unit = unitDao.getStateAdminUnitById(id);
			
			//unit = unitDao.getStateAdminUnitTypeByIdWithRelations(id);
			//bossUnits = unitDao.getAllPossibleBossStateAdminUnitTypesByType(unit);			
		} else {
			unit = fillUnitFromRequest(request);
			bossUnits = unitDao.getAllStateAdminUnits();	
		}
		
		request.setAttribute("unit", unit);
		request.setAttribute("bossUnits", bossUnits);
			
		request.getRequestDispatcher("pages/unitForm.jsp").forward(request, response);		
	}
	

	private StateAdminUnit fillUnitFromRequest(HttpServletRequest request) {
		StateAdminUnit unit = new StateAdminUnit();
		
		unit.setState_admin_unit_id(null);
		unit.setCode(request.getParameter("code"));
		unit.setName(request.getParameter("name"));
		unit.setComment(request.getParameter("comment"));
		//unit.setFromDate(getDateFromString(request.getParameter("fromDate")));
		//unit.setToDate(getDateFromString(request.getParameter("toDate")));
		
		return unit;
	}
	

	// Show list of all units 
	
	
	private void showUnitsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("showUnitsList");

		List<StateAdminUnit> units = unitDao.getAllStateAdminUnits();

		request.setAttribute("units", units);
		request.getRequestDispatcher("pages/unitsList.jsp").forward(request, response);	
	}


}