package servlets;

import java.io.IOException;
import java.util.ArrayList;
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

	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getAction(request);

		if (action.equals("default")) {
			showListOfStateAdminUnitTypes(request, response);
		} else if (action.equals("editStateAdminUnitType")) {
			showFormOfStateAdminUnitType(request, response);
		}
	}

	
	private String getAction(HttpServletRequest request) {
		String action = request.getParameter("action");
		String id = request.getParameter("id");

		if ((action == null) || (action.length() < 1)) {
			action = "default";
		} else if (action.equals("editStateAdminUnitType") && id != null && id.length() > 0) {
			action = "editStateAdminUnitType";
		} else if (action.equals("addStateAdminUnitType") && id == null) {
			action = "addStateAdminUnitType";
		} else { 
			action = "default";
		}

		return action;
	}

	
	private void showListOfStateAdminUnitTypes(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		List<StateAdminUnitType> types = typeDao.getAllStateAdminUnitTypes();

		request.setAttribute("stateAdminUnitTypes", types);
		request.getRequestDispatcher("stateAdminUnitTypesList.jsp").forward(request, response);
	}
	
	
	
	
	
	private void showFormOfStateAdminUnitType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		StateAdminUnitType type = typeDao.getStateAdminUnitTypeByIdWithRelations(id);
		List<StateAdminUnitType> types = typeDao.getAllStateAdminUnitTypes();

		request.setAttribute("unitType", type);
		request.setAttribute("unitTypes", types);
		
		request.getRequestDispatcher("stateAdminUnitTypeForm.jsp").forward(request, response);		
	}
	

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = getValidationErrors(request);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
			showFormOfStateAdminUnitType(request, response);
            return;
        }
	        
        updateStateAdminUnitType(request);
        updateStateAdminUnitTypeRelations(request);
        showStateAdminUnitTypeSummary(request, response);
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
	


	private void updateStateAdminUnitTypeRelations(HttpServletRequest request) {
		
		updateStateAdminUnitTypeBoss(request);
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


}
