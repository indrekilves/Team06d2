package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class TypesTreeController extends GenericController {
	private static final long serialVersionUID = 1L;

	
	// GET

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		shoeTypesTree(request, response);
	}


	private void shoeTypesTree(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("pages/typesTree.jsp").forward(request, response);       		
	}


}
