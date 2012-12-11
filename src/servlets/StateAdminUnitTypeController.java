package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.StateAdminUnitType;
import dao.PossibleSubordinationDao;
import dao.StateAdminUnitTypeDao;



public class StateAdminUnitTypeController extends HttpServlet {

	
	StateAdminUnitTypeDao typeDao = new StateAdminUnitTypeDao();
	PossibleSubordinationDao posSubOrdDao = new PossibleSubordinationDao();

	
	// GET
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getDoGetAction(request);
		
		if (action.equals("default")) {
			showStateAdminUnitTypesList(request, response);
		} else if (action.equals("edit")) {
			showStateAdminUnitTypeForm(request, response);
		} else if (action.equals("add")) {
			showStateAdminUnitTypeForm(request, response);
		}

	}

	
	private String getDoGetAction(HttpServletRequest request) {
		String action	= request.getParameter("action");
		String id 		= request.getParameter("id");

		if ((action == null) || (action.length() < 1)) {
			action = "default";
		} else if (action.equals("editStateAdminUnitType") && id != null && id.length() > 0) {
			action = "edit";
		} else if (action.equals("addStateAdminUnitType") && id == null) {
			action = "add";
		} else { 
			action = "default";
		}

		return action;
	}

	
	private void showStateAdminUnitTypesList(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		List<StateAdminUnitType> types = typeDao.getAllStateAdminUnitTypes();

		request.setAttribute("stateAdminUnitTypes", types);
		request.getRequestDispatcher("stateAdminUnitTypesList.jsp").forward(request, response);
	}

	
	private void showStateAdminUnitTypeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		if (id != null){
			StateAdminUnitType type = typeDao.getStateAdminUnitTypeByIdWithRelations(id);
			List<StateAdminUnitType> types = typeDao.getAllStateAdminUnitTypes();
	
			request.setAttribute("unitType", type);
			request.setAttribute("unitTypes", types);
		}
		
		request.getRequestDispatcher("stateAdminUnitTypeForm.jsp").forward(request, response);		
	}
	

	// POST	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getDoPostAction(request);
		
		if (action.equals("default")) {
			showStateAdminUnitTypesList(request, response);
		} else if (action.equals("cancel")){
			showStateAdminUnitTypesList(request, response);
		} else if (action.equals("save")){
			saveStateAdminUnitType(request, response);
		} else if (action.equals("addSubType")){
			addSubStateAdminUnitType(request, response);
		} else if (action.equals("removeSubType")){
			removeSubStateAdminUnitType(request, response);
		}

	}


	private String getDoPostAction(HttpServletRequest request) {
		String action 	= request.getParameter("action");
		String id 		= request.getParameter("id");
		String subId	= request.getParameter("subId");
				
		if ((action == null) || (action.length() < 1)) {
			action = "default";
		} else if (action.equals("saveStateAdminUnitType") && id != null && id.length() > 0) {
			action = "save";
		} else if (action.equals("cancelStateAdminUnitType")) {
			action = "cancel";			
		} else if (action.equals("removeSubStateAdminUnitType") && id != null && id.length() > 0 && subId != null && subId.length() > 0) {
			action = "removeSubType";
		} else if (action.equals("addSubStateAdminUnitType") && id != null && id.length() > 0) {
			action = "addSubType";
		} else { 
			action = "default";
		}

		return action;
	}
		
	// Save the edit
	
	private void saveStateAdminUnitType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = getValidationErrors(request);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
			showStateAdminUnitTypeForm(request, response);
            return;
        }
	        
        updateStateAdminUnitType(request);
		updateStateAdminUnitTypeBoss(request); // Changing subOrdinates goes through removeSubStateAdminUnitType / addSubStateAdminUnitType
        showStateAdminUnitTypeSummary(request, response);
	}

	
	private void addSubStateAdminUnitType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showStateAdminUnitTypeForm(request, response);
	}
	


	
	private List<String> getValidationErrors(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();

		if ("".equals(request.getParameter("id"))) {
		    errors.add("ID is required.");
		}

		
		if ("".equals(request.getParameter("code"))) {
		    errors.add("Code is required.");
		}
		
		
		if ("".equals(request.getParameter("name"))) {
		    errors.add("Name is required.");
		}

		errors.addAll(getDateValidationErrors(request));
		
		
		return errors;
	}
	

	private List<String> getDateValidationErrors(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();

		if ("".equals(request.getParameter("fromDate"))) {
		    errors.add("From date is required.");
		    return errors;
		}

		if ("".equals(request.getParameter("toDate"))) {
		    errors.add("toDate is required.");
		    return errors;
		}

		DateFormat 	dateFormat	= new SimpleDateFormat("dd.MM.yyyy");
		String 		strFromDate = request.getParameter("fromDate");
		String 		strToDate 	= request.getParameter("toDate");
		Date 		fromDate 	= null;
		Date 		toDate   	= null;
		
		try {
			fromDate = dateFormat.parse(strFromDate);
		} catch (ParseException e) {
			errors.add("From date must with format dd.mm.yyyy");
		}  

		try {
			toDate = dateFormat.parse(strToDate);
		} catch (ParseException e) {
			errors.add("To date must with format dd.mm.yyyy");
		}  

		if (fromDate.after(toDate)){
			errors.add("From date must be before To date");
		}		
		
		return errors;
	}


	private void updateStateAdminUnitType(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		StateAdminUnitType type = typeDao.getStateAdminUnitTypeById(id);
	
		type.setCode(request.getParameter("code"));
		type.setName(request.getParameter("name"));
		type.setComment(request.getParameter("comment"));
		
		typeDao.updateStateAdminUnitTypeByType(type);
	}

	
	private void updateStateAdminUnitTypeBoss(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		StateAdminUnitType type = typeDao.getStateAdminUnitTypeByIdWithRelations(id);

		Integer oldBossId = type.getBossAdminUnitType().getState_admin_unit_type_id();
		Integer newBossId = Integer.parseInt(request.getParameter("bossAdminUnitTypeId"));
		
		if (oldBossId == newBossId) return;
		
		posSubOrdDao.replaceBossRelation(oldBossId, newBossId, id);
	}


	private void showStateAdminUnitTypeSummary(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		StateAdminUnitType type = typeDao.getStateAdminUnitTypeByIdWithRelations(id);

		request.setAttribute("stateAdmintUnitType", type);
        request.getRequestDispatcher("stateAdminUnitType.jsp").forward(request, response);
	}

	
	// Remove subType
	
	
	private void removeSubStateAdminUnitType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		updateStateAdminUnitTypeSubOrdinates(request);
		showStateAdminUnitTypeForm(request, response);
	}


	private void updateStateAdminUnitTypeSubOrdinates(HttpServletRequest request) {
		Integer id    = Integer.parseInt(request.getParameter("id"));
		Integer subId = Integer.parseInt(request.getParameter("subId"));
	
		posSubOrdDao.removeSubOrdinateRelation(id, subId);
	}



}
