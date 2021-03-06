package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.StateAdminUnitType;
import dao.PossibleSubordinationDao;
import dao.StateAdminUnitTypeDao;



public class TypeController extends GenericController {

	private static final long serialVersionUID = 3711868332683834980L;
	StateAdminUnitTypeDao typeDao = new StateAdminUnitTypeDao();
	PossibleSubordinationDao posSubOrdDao = new PossibleSubordinationDao();

	
	// GET
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		showTypesList(request, response);
	}
	

	// POST	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		dispatchRequest(request, response);
	}

	
	
	
	// Dispatcher

	
	
	
	private void dispatchRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getAction(request);
		
		if (action.equals("default") || action.equals("cancelForm")) 
		{
			showTypesList(request, response);
		} 
		else if (action.equals("showTypeForm"))
		{
			showTypeForm(request, response);
		} 
		else if (action.equals("saveForm"))
		{
			saveTypeForm(request, response);
		} 
		else if (action.equals("removeType"))
		{
			removeType(request, response);
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
		
		// Coming from List of all stateAdminUnitTypes
		if (origin.equals("typesList"))
		{
			if (exitMode.equals("showSelectedEntry") &&  (id != null && id.length() > 0))
			{
				action = "showTypeForm";
			} 
			else if (exitMode.equals("removeSelectedEntry") &&  (id != null && id.length() > 0))
			{
				action = "removeType";
			}
			else if (exitMode.equals("addEntry"))
			{
				action = "showTypeForm";
			}
		}
		
		
		
		// Coming from Edit form
		if (origin.equals("typeForm"))
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
		
		
		// Coming from list of possible subOrds for a type 
		if (origin.equals("typePossibileSubordinatesList"))
		{
			if (exitMode != null && exitMode.equals("selectSubOrdinate") && (subId != null && subId.length() > 0)) 
			{
				action = "saveSubOrdinate";
			} 
			else if (exitMode.equals("cancelSubordinateSelect") &&  (id != null && id.length() > 0)) 
			{
				action = "showTypeForm";
			}
		}
		
		return action;		
	}
	
	
	
	
	// Actions 	
	
	
	// Add / edit type
	
	
	
	
	private void saveTypeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = getValidationErrors(request);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
			showTypeForm(request, response);
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
		showTypesList(request, response);
	}
	

	protected List<String> getValidationErrors(HttpServletRequest request) {

		List<String> errors = super.getValidationErrors(request);
		errors.addAll(getCodeValidationErrors(request));
		
		return errors;
	}


	private List<String> getCodeValidationErrors(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();

		String code = request.getParameter("code");
		if (code == null || code.length() < 1) {
		    errors.add("Code is required.");
		    return errors;
		}		

		// Check if code is used by some OTHER type
		String strId = request.getParameter("id");		
		if (strId != null && strId.length() > 0){
			Integer id = Integer.parseInt(strId);
			StateAdminUnitType type = typeDao.getStateAdminUnitTypeById(id);
			if (type != null) {
				if (!type.getCode().equals(code)) {
					if (typeDao.isCodeExisting(code)){
						errors.add("Code '" + code + "' is in use already.");
					}
				}
			}
		} else {
			if (typeDao.isCodeExisting(code)){
				errors.add("Code '" + code + "' is in use already.");
			}
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


	
	
	// Remove Type

	
	
	
	private void removeType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strId = request.getParameter("id");
		if (strId != null && strId.length() > 0){
			typeDao.closeStateAdminUnitTypeById(Integer.parseInt(strId));
		}

		showTypesList(request, response);			
	}
	
	
	
	
	// Remove subOrdinate
	
	
	
	
	private void removeSubOrdinate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		updateStateAdminUnitTypeSubOrdinates(request);
		showTypeForm(request, response);
	}


	private void updateStateAdminUnitTypeSubOrdinates(HttpServletRequest request) {
		Integer id    = Integer.parseInt(request.getParameter("id"));
		Integer subId = Integer.parseInt(request.getParameter("subId"));

		posSubOrdDao.removeSubOrdinateRelation(id, subId);
	}

	
	
	
	// Add subOrdinate

	
	
	
	private void addSubOrdinate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = null;
		String strId = request.getParameter("id"); 
		
		if (strId == null || strId.length() < 1){
			List<String> errors = getValidationErrors(request);
	        if (!errors.isEmpty()) {
	            request.setAttribute("errors", errors);
				showTypeForm(request, response);
	            return;
	        }
		  
			id = insertStateAdminUnitType(request);
		} else {
			id = Integer.parseInt(strId);
		}
		
		if (id == null){
			showTypesList(request, response);
			return;	
		}
		
		StateAdminUnitType type = typeDao.getStateAdminUnitTypeByIdWithRelations(id);		
		request.setAttribute("type", type);

		List<StateAdminUnitType> possibibleSubordinateTypes = typeDao.getAllPossibleSubordinateStateAdminUnitTypesByType(type);
		request.setAttribute("possibleSubordinateTypes", possibibleSubordinateTypes);

        request.getRequestDispatcher("pages/typePossibileSubordinatesList.jsp").forward(request, response);		
	}

	

	private void saveSubOrdinate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		insertStateAdminUnitTypeSubOrdinate(request);
		showTypeForm(request, response);
	}


	private void insertStateAdminUnitTypeSubOrdinate(HttpServletRequest request) {
		Integer id    = Integer.parseInt(request.getParameter("id"));
		Integer subId = Integer.parseInt(request.getParameter("subId"));

		posSubOrdDao.addSubOrdinateRelation(id, subId);		
	}

	
	
	
	//  Show form
	
	
	
	
	private void showTypeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StateAdminUnitType type = null;
		List<StateAdminUnitType> bossTypes = null;		
		String strId = request.getParameter("id");

		if (strId != null && strId.length() > 0){
			Integer id = Integer.parseInt(strId);
			type = typeDao.getStateAdminUnitTypeByIdWithRelations(id);
			bossTypes = typeDao.getAllPossibleBossStateAdminUnitTypesByType(type);			
		} else {
			type = fillTypeFromRequest(request);
			bossTypes = typeDao.getAllStateAdminUnitTypes();	
		}
		
		request.setAttribute("type", type);
		request.setAttribute("bossTypes", bossTypes);
			
		request.getRequestDispatcher("pages/typeForm.jsp").forward(request, response);		
	}
	

	private StateAdminUnitType fillTypeFromRequest(HttpServletRequest request) {
		StateAdminUnitType type = new StateAdminUnitType();
		
		type.setState_admin_unit_type_id(null);
		type.setCode(request.getParameter("code"));
		type.setName(request.getParameter("name"));
		type.setComment(request.getParameter("comment"));
		type.setFromDate(getDateFromString(request.getParameter("fromDate")));
		type.setToDate(getDateFromString(request.getParameter("toDate")));
		
		return type;
	}
	
	
	

	// Show list of all types
	
	
	
	
	private void showTypesList(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		List<StateAdminUnitType> types = typeDao.getAllStateAdminUnitTypes();

		request.setAttribute("types", types);
		request.getRequestDispatcher("pages/typesList.jsp").forward(request, response);
	}


}
