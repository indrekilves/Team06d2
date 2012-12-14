package servlets;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class GenericController extends HttpServlet {
	
	private static final long serialVersionUID = 3801477829643454955L;

	
	
	
	// Helpers
	
	
	
	
	public Date getDateFromString(String strDate) {
		if (strDate == null) return null;
		
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {}
		
		return date;
	}

	
	
	// Form Integrity checking
	
	
	
	protected List<String> getValidationErrors(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();

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
}
