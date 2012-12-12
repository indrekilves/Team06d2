package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.PortableInterceptor.INACTIVE;

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
		StateAdminUnitType type = null;
		List<StateAdminUnitType> types = null;		
		String strId = request.getParameter("id");

		if (strId != null){
			Integer id = Integer.parseInt(strId);
			type = typeDao.getStateAdminUnitTypeByIdWithRelations(id);
			types = typeDao.getAllPossibleBossStateAdminUnitTypesByType(type);			
		} else {
			types = typeDao.getAllStateAdminUnitTypes();	
		}
		
		request.setAttribute("unitType", type);
		request.setAttribute("unitTypes", types);
			
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
		} else if (action.equals("addSubOrdinate")){
			addSubStateAdminUnitType(request, response);
		} else if (action.equals("removeSubOrdinate")){
			removeSubStateAdminUnitType(request, response);
		}

	}


	private String getDoPostAction(HttpServletRequest request) {
		String action 	= "default";
		String form 	= request.getParameter("form");
		String exitMode	= request.getParameter("exitMode");		
		String subId 	= request.getParameter("subId");
		
		if (form == null || form.length() < 1) return action;
		
		if (form.equals("stateAdminUnitTypeForm")){
			if (exitMode != null && exitMode.length() > 0) {
				action = exitMode;
			} else if (exitMode.equals("removeSubOrdinate") && (subId == null || subId.length() < 1)){
				action = "default";
			}
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
	  
        Integer id = null;
        String strId = request.getParameter("id"); 
		
        if (strId == null || strId.length() < 1){
			id = insertStateAdminUnitType(request);
		} else {        
			id = Integer.parseInt(strId);
			updateStateAdminUnitTypeById(id, request);
		}
		
		updateStateAdminUnitTypeBossById(id, request); // Changing subOrdinates goes through removeSubStateAdminUnitType / addSubStateAdminUnitType
        showStateAdminUnitTypeSummaryById(id, request, response);
	}

	
	

	private void addSubStateAdminUnitType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("id"); 
		if (strId == null || strId.length() < 1){
			int id = insertStateAdminUnitType(request);
		}
		
		// TODO
		
		showStateAdminUnitTypeForm(request, response);
	}
	


	
	private List<String> getValidationErrors(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		
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


		Date fromDate = getDateFromString(request.getParameter("fromDate"));
		if (fromDate == null) {
			errors.add("From date must with format dd.mm.yyyy");
			return errors;
		}	
		
		Date toDate = getDateFromString(request.getParameter("toDate"));
		if (toDate == null) {
			errors.add("To date must with format dd.mm.yyyy");
			return errors;
		}	
		
		
		if (fromDate.after(toDate)){
			errors.add("From date must be before To date");
		}		
		
		return errors;
	}


	

	private Integer insertStateAdminUnitType(HttpServletRequest request) {
		StateAdminUnitType type = new StateAdminUnitType();

		type.setCode(request.getParameter("code"));
		type.setName(request.getParameter("name"));
		type.setComment(request.getParameter("comment"));
		
		Date fromDate = getDateFromString(request.getParameter("fromDate"));
		Date toDate = getDateFromString(request.getParameter("toDate"));
		
		type.setFromDate(fromDate);
		type.setToDate(toDate);
		
		Integer id = typeDao.insertStateAdminUnitTypeByType(type);
		
		return id;		
	}
	
	
	private void updateStateAdminUnitTypeById(Integer id, HttpServletRequest request) {
		if (id == null) return;

		StateAdminUnitType type = typeDao.getStateAdminUnitTypeById(id);
	
		type.setCode(request.getParameter("code"));
		type.setName(request.getParameter("name"));
		type.setComment(request.getParameter("comment"));
		
		Date fromDate = getDateFromString(request.getParameter("fromDate"));
		Date toDate = getDateFromString(request.getParameter("toDate"));
		
		type.setFromDate(fromDate);
		type.setToDate(toDate);
		
		typeDao.updateStateAdminUnitTypeByType(type);
	}

	
	private Date getDateFromString(String strDate) {
		if (strDate == null) return null;
		
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {}
		
		return date;
	}


	
	
	private void updateStateAdminUnitTypeBossById(Integer id, HttpServletRequest request) {
		if (id == null) return;
		
		StateAdminUnitType type = typeDao.getStateAdminUnitTypeByIdWithRelations(id);
		StateAdminUnitType oldBoss = type.getBossAdminUnitType();

		Integer oldBossId = null;
		Integer newBossId = null;
		String newBossIdStr = request.getParameter("bossAdminUnitTypeId");
		
		if (oldBoss != null)
		{
			oldBossId = oldBoss.getState_admin_unit_type_id();
		}
	
		if (newBossIdStr != null && newBossIdStr.length() > 0){
			newBossId = Integer.parseInt(newBossIdStr);
		}
		
		if (oldBossId == newBossId) return;
		
		posSubOrdDao.replaceBossRelation(oldBossId, newBossId, id);
	}


	private void showStateAdminUnitTypeSummaryById(Integer id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (id == null){
			showStateAdminUnitTypesList(request, response);
			return;
		}
		
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
