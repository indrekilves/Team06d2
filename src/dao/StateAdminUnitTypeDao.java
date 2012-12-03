package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import beans.StateAdminUnitType;


public class StateAdminUnitTypeDao extends BorderGuardDao{
	
	private Statement st;
	private PreparedStatement ps;
	private ResultSet rs;
		
	
	public StateAdminUnitTypeDao() {
		super();
	}

	public StateAdminUnitTypeDao(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}
	
	

	public List<StateAdminUnitType> getAllStateAdminUnitTypes() {
		try {
			st = super.getConnection().createStatement();
		    rs = st.executeQuery("SELECT 	state_admin_unit_type_id, " +	//  1
		    					 "			openedBy,  " +  				//  2
					 			 "			opened, " +						//  3
						  	 	 "			changedBy, " + 					//  4
						 		 "			changed, " +					//  5
								 "			closedBy, " +					//  6
								 "			closed, " + 					//  7
								 "			code,  " +						//  8
								 "			name,  " + 						//  9
								 "			comment,  " +					// 10
								 "			fromDate, " +					// 11
								 "			toDate  " +						// 12
		    					 "FROM STATE_ADMIN_UNIT_TYPE");

		    List<StateAdminUnitType> stateAdminUnitTypes = new ArrayList<StateAdminUnitType>();
		    while (rs.next()) {
		    	stateAdminUnitTypes.add(createStateAdminUniTypeFromResultSet());
		    }

		    return stateAdminUnitTypes;
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(st);
		}	
	}

	
	
	public StateAdminUnitType getStateAdminUnitTypeById(int state_admin_unit_type_id) {
		try {
			String sql = "SELECT 	state_admin_unit_type_id, " +	//  1
						 "			openedBy,  " +  				//  2
			 			 "			opened, " +						//  3
				  	 	 "			changedBy, " + 					//  4
				 		 "			changed, " +					//  5
						 "			closedBy, " +					//  6
						 "			closed, " + 					//  7
						 "			code,  " +						//  8
						 "			name,  " + 						//  9
						 "			comment,  " +					// 10
						 "			fromDate, " +					// 11
						 "			toDate  " +						// 12
						 "FROM STATE_ADMIN_UNIT_TYPE " +
						 "WHERE state_admin_unit_type_id = ?";
			
		    ps = super.getConnection().prepareStatement(sql);	 
		    ps.setInt(1, state_admin_unit_type_id);		    
		    rs = ps.executeQuery();
		    StateAdminUnitType stateAdminUnitType = null;

		    while (rs.next()) {
		    	stateAdminUnitType = createStateAdminUniTypeFromResultSet();
		    }

			return stateAdminUnitType;
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}	

	}

	
	private StateAdminUnitType createStateAdminUniTypeFromResultSet() throws SQLException {
    	StateAdminUnitType stateAdminUnitType = new StateAdminUnitType();
    	
    	stateAdminUnitType.setState_admin_unit_type_id(rs.getInt("state_admin_unit_type_id"));
    	stateAdminUnitType.setOpenedBy(rs.getString("openedBy"));
    	stateAdminUnitType.setOpened(rs.getDate("opened"));
    	stateAdminUnitType.setChangedBy(rs.getString("changedBy"));
    	stateAdminUnitType.setChanged(rs.getDate("changed"));
    	stateAdminUnitType.setClosedBy(rs.getString("closedBy"));
    	stateAdminUnitType.setClosed(rs.getDate("closed"));
    	stateAdminUnitType.setCode(rs.getString("code"));
    	stateAdminUnitType.setName(rs.getString("name"));
    	stateAdminUnitType.setComment(rs.getString("comment"));
    	stateAdminUnitType.setFromDate(rs.getDate("fromDate"));
    	stateAdminUnitType.setToDate(rs.getDate("toDate"));
    	
		return stateAdminUnitType;
	}

	
	
}
