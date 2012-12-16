package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.PlainJSON;
import beans.StateAdminUnitType;

import com.google.gson.Gson;
import dao.StateAdminUnitTypeDao;




public class TypesTreeController extends GenericController {
	
	private static final long serialVersionUID = 1L;
	private StateAdminUnitTypeDao typeDao = new StateAdminUnitTypeDao();

	
	
	
	// GET
	
	
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
		dispatchRequest(request, response);
    }

    
    
    
    // POST
	
	
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
    
	
	
	// Dispacher
	
	
	
	private void dispatchRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String root = request.getParameter("root");
		
		if (root == null || root.length() < 1){
			request.getRequestDispatcher("pages/typesTree.jsp").forward(request, response);
		} else {
	    	showTypeTree(request, response);
		}
	}

	
	
	
	// Show type tree
	
	
	
	
	private void showTypeTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setContentType("application/json");
		
		Collection<PlainJSON> jSONTypes = getJSONTypes(request);

		PrintWriter out 	= response.getWriter();
		Gson 		gson 	= new Gson();
		
		System.out.println("Types in JSON: " + gson.toJson(jSONTypes));

		out.println(gson.toJson(jSONTypes));
		out.flush();
		out.close();		

	}



	private Collection<PlainJSON> getJSONTypes(HttpServletRequest request) {
		Collection<PlainJSON> jSONTypes = new ArrayList<PlainJSON>();
		
		String root = request.getParameter("root");
		System.out.println("Show tree for ID: " + root);
	
		if (root != null && root.length() > 0){
			if (root.equals("source")){
				jSONTypes = getTopLevelJSONTypes();
			}
			else
			{
				Integer id = Integer.parseInt(root);
				jSONTypes = getJSONTypeChildrenById(id);
			}
		}

		return jSONTypes;
	}

	
	
	private Collection<PlainJSON> getTopLevelJSONTypes() {
		Collection<PlainJSON> jSONTypes = new ArrayList<PlainJSON>();
		
		List <StateAdminUnitType> topLevelTypes = typeDao.getTopLevelTypes();
		if (topLevelTypes == null || topLevelTypes.isEmpty()) return null;
		
		for (StateAdminUnitType type : topLevelTypes) {
			List <PlainJSON> children = getChildrenByTypeId(type.getState_admin_unit_type_id());
			PlainJSON jSON = getJSONByTypeAndChildren(type, children);
			jSONTypes.add(jSON);			
		}
		
		return jSONTypes;
	}
	

	private List<PlainJSON> getChildrenByTypeId(Integer typeId) {
		if (typeId == null) return null;
		
		StateAdminUnitType type = typeDao.getStateAdminUnitTypeByIdWithRelations(typeId);
		if (type == null) return null;
		
	    List<PlainJSON> children					= new ArrayList<PlainJSON>();
	    List<StateAdminUnitType> directSubOrdinates = type.getSubordinateAdminUnitTypes();
	    
	    if (directSubOrdinates == null || directSubOrdinates.isEmpty()) return null;
	    
	    for (StateAdminUnitType subType : directSubOrdinates) {
	    	if (subType != null){

	    		List<PlainJSON> subTypeChildren = getChildrenByTypeId(subType.getState_admin_unit_type_id());
		    	PlainJSON jSON = getJSONByTypeAndChildren(subType, subTypeChildren);
	    		
	    		children.add(jSON);
	    	}	    	
		}
	    
		return children;
	}
	


	private Collection<PlainJSON> getJSONTypeChildrenById(Integer id) {
		if (id == null) return null;
		
		Collection<PlainJSON> jSONTypes = new ArrayList<PlainJSON>();
		List<StateAdminUnitType> subOrdinates = typeDao.getSubOrdinateAdminUnitTypesById(id);
		
		if (subOrdinates != null && subOrdinates.isEmpty() == false){
		
			for (StateAdminUnitType subType : subOrdinates) {
				if (subType != null){
		    		List<PlainJSON> subTypeChildren = getChildrenByTypeId(subType.getState_admin_unit_type_id());
			    	PlainJSON jSON = getJSONByTypeAndChildren(subType, subTypeChildren);

					jSONTypes.add(jSON);
					
				}	
			}
			
		}	
		
		
		return jSONTypes;
	}

	
	private PlainJSON getJSONByTypeAndChildren(StateAdminUnitType type, List<PlainJSON> children) {
		if (type == null) return null;
		
		PlainJSON jSON = new PlainJSON();

		jSON.setText(type.getName());
		jSON.setExpanded(true);
		jSON.setId(type.getState_admin_unit_type_id().toString());
		jSON.setChildren(children);
		jSON.setHasChildren(hasChildren(children));

		return jSON;
	}


	private Boolean hasChildren(List<PlainJSON> children) {
		return children != null && children.isEmpty() == false;
	}

	
	
	

}
