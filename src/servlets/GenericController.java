package servlets;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;

public class GenericController extends HttpServlet {
	
	private static final long serialVersionUID = 3801477829643454955L;

	
	public Date getDateFromString(String strDate) {
		if (strDate == null) return null;
		
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {}
		
		return date;
	}

}
