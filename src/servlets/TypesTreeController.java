package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class TypesTreeController extends GenericController {
	private static final long serialVersionUID = 1L;

	
	// GET

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		showTypesTree(request, response);
//		dispatchRequest(request, response);
		request.getRequestDispatcher("pages/typesTree.jsp").forward(request, response);

	}

	
	
	
//
//	private void dispatchRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String action = getAction(request);
//	
////		if (action.equals("default"))
////		{
////			request.getRequestDispatcher("pages/typesTree.jsp").forward(request, response);
////		}
////		else if (action.equals("showTree"))
////		{
////			request.getRequestDispatcher("pages/typesTree.jsp").forward(request, response);       		
////		}
//	}
//
//
//	private String getAction(HttpServletRequest request) {
//		String action = request.getParameter("action");
//		if (action == null || action.length() < 1){
//			return "default";
//		} else {
//			return action;
//		}
//	}





//	private void showTypesTree(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
//		
//        //response.setContentType("text/html;charset=UTF-8");
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//		//response.sendRedirect("pages/typesTreeContent.jsp");		
//
//
////        PrintWriter out = response.getWriter();
//        try {
//        	List<StateAdminUnitType> types = getTopLevelTypesWithSubordinates(); 
//    		
//        	JSONArray jSONArray = new JSONArray();
//            for(int i=0; i<types.size();){
//                JSONObject jSONObject = new JSONObject();                
//                jSONObject.put("state","open");
//                jSONObject.put("data",types.get(i).getName());
//
//                JSONObject jsonAttr = new JSONObject();                
//                jsonAttr.put("name", types.get(i).getName());
//                jSONObject.put("attr", jsonAttr);
//                jsonAttr = null;
//
////                if(types.get(i+1).getId()==0){
////                    JSONArray jsonChildarray = new JSONArray();
////
////                    while(types.get(i+1).getId()==0){
////                        i++;
////                        JSONObject child = new JSONObject();
////                        child.put("data",types.get(i).getName());
////
////                        JSONObject jsonChildAttr = new JSONObject();                        
////                        jsonChildAttr.put("name", types.get(i).getName());
////                        child.put("attr", jsonChildAttr);
////                        jsonChildAttr = null;
////
////                        jsonChildarray.put(child);
////                        child=null;
////
////                        if(types.size()==(i+1)){
////                            break;
////                        }
////                    }
////                    jSONObject.put("children", jsonChildarray);
////                    jSONArray.put(jSONObject);
////                    jSONObject=null;
////                }
//                jSONArray.put(jSONObject);
//                i++;
//            }
//            
//            System.out.println(jSONArray);
////            out.print(jSONArray);
////            out.flush();
////            jSONArray=null;
//            
//        }catch(Exception e){
//            System.out.println(e);
//        }
//        finally {
////           out.close();
//        }
//		
//        request.setAttribute("JSON", jSONArray);
//		request.getRequestDispatcher("pages/typesTreeContent.jsp").forward(request, response);       		
//	}





}
