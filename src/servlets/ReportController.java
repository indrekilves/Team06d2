package servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StateAdminUnitDao;
import dao.StateAdminUnitTypeDao;

import beans.StateAdminUnit;
import beans.StateAdminUnitType;


public class ReportController extends GenericController {

	private static final long 		serialVersionUID = 1L;
	private StateAdminUnitDao 		unitDao = new StateAdminUnitDao();
	private StateAdminUnitTypeDao 	typeDao = new StateAdminUnitTypeDao();
	private Integer					lastTypeId;
	private Date 					lastDate;
	
	
	
	// GET
    
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showUnitsReport(request, response);
	}

	
	
		
	// POST 
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatchRequest(request, response);
	}

	
	
	
	// Dispatcher

	
	
	
	private void dispatchRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getAction(request);
		
		if (action.equals("default") || action.equals("showUnitsReport")) 
		{
			showUnitsReport(request, response);
		} 		
		else if (action.equals("showUnitReadonlyForm")) 
		{
			showUnitReadonlyForm(request, response);
		} 
		
	}






	private String getAction(HttpServletRequest request) {
		String action 		= "default";
		String origin 		= request.getParameter("origin");
		String exitMode		= request.getParameter("exitMode");		
		String id	 		= request.getParameter("id");
		
		if (origin == null || origin.length() < 1 || exitMode == null || exitMode.length() <1) return action;
		
		// Coming from Report
		if (origin.equals("unitsReport"))
		{
			if (exitMode.equals("refreshReport") || exitMode.equals("cancelForm"))
			{
				action = "showUnitsReport";
			} 
			else if (exitMode.equals("showSelectedEntry") && id != null && id.length() > 0)
			{
				action = "showUnitReadonlyForm";
			}

		}
		
		return action;
	}
	

	
	// Show report
	



	private void showUnitsReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<StateAdminUnit> 		units = null;
		List<StateAdminUnitType> 	types = typeDao.getAllStateAdminUnitTypes();

		Date date = getDate(request);

		Integer typeId = getTypeId(request);
		if (typeId != null && date != null){
			units = unitDao.getAllUnitsWithSuboridinatesByTypeIdAndDate(typeId, date);
		}		
		
		
		request.setAttribute("lastTypeId", lastTypeId);
		request.setAttribute("lastDate", lastDate);		
		request.setAttribute("units", units);
		request.setAttribute("types", types);

		System.out.println("ShowReport for TypeID: " + typeId);
		
		request.getRequestDispatcher("pages/unitsReport.jsp").forward(request, response);	
	}
	
	
	




	private Integer getTypeId(HttpServletRequest request) {
		Integer typeId = null;
		
		String 	strTypeId = request.getParameter("selTypeId");
		if (strTypeId != null && strTypeId.length() > 0){
			typeId = Integer.parseInt(strTypeId);
			lastTypeId = typeId;
		}
				
		if (typeId == null){
			// TODO
			typeId = lastTypeId;
		}
		
		return typeId;	
	}
	
	
	

	private Date getDate(HttpServletRequest request) {
		Date date = getDateFromString(request.getParameter("date"));
		if (date != null){
			lastDate = date;
		} else {
			if (lastDate != null){
				date = lastDate;
			} else {
				date = new Date();
				lastDate = date;
			}
		}
			
		return date;
	}

	
	// Show Unit Form (readOnly)
	
	
	




	private void showUnitReadonlyForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StateAdminUnit unit = null;
		List<StateAdminUnit> bossUnits = null;		
		String strId = request.getParameter("id");

		System.out.println("showUnitForm - ID: " + strId);
		
		if (strId != null && strId.length() > 0){
			Integer id = Integer.parseInt(strId);
			unit = unitDao.getUnitWithRelationsById(id);
			bossUnits = unitDao.getAllPossibleBossUnitsByUnit(unit);			
		} else {
			System.out.println("showUnitForm in readOnly - ID missing");
			showUnitsReport(request, response);
			return;
		}
		
		request.setAttribute("unit", unit);
		request.setAttribute("bossUnits", bossUnits);
			
		request.getRequestDispatcher("pages/unitForm.jsp").forward(request, response);		
		

	}
}
