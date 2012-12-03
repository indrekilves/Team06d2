package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import beans.StateAdminUnit;

public class StateAdminUnitDao extends BorderGuardDao{

	 private Statement st;
	 private ResultSet rs;
		
	
	public StateAdminUnitDao() {
		super();
	}


	public StateAdminUnitDao(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	
	

	public List<StateAdminUnit> getAllStateAdminUnits() {
		try {
			st = super.getConnection().createStatement();

			rs = st.executeQuery("SELECT 	state_admin_unit_id, " +				//  1
								"			openedBy,  " +  						//  2
								"			opened, " +								//  3
								"			changedBy, " + 							//  4
								"			changed, " +							//  5
								"			closedBy, " +							//  6
								"			closed, " + 							//  7
								"			code,  " +								//  8
								"			name,  " + 								//  9
								"			comment,  " +							// 10
								"			fromDate, " +							// 11
								"			toDate, " +								// 12
								"			state_admin_unit_type_id " +			// 13
								"FROM STATE_ADMIN_UNIT");

			List<StateAdminUnit> stateAdminUnits = new ArrayList<StateAdminUnit>();
			while (rs.next()) {
				StateAdminUnit stateAdminUnit = new StateAdminUnit();

				stateAdminUnit.setState_admin_unit_id(rs.getInt("state_admin_unit_id"));
				stateAdminUnit.setOpenedBy(rs.getString("openedBy"));
				stateAdminUnit.setOpened(rs.getDate("opened"));
				stateAdminUnit.setChangedBy(rs.getString("changedBy"));
				stateAdminUnit.setChanged(rs.getDate("changed"));
				stateAdminUnit.setClosedBy(rs.getString("closedBy"));
				stateAdminUnit.setClosed(rs.getDate("closed"));
				stateAdminUnit.setCode(rs.getString("code"));
				stateAdminUnit.setName(rs.getString("name"));
				stateAdminUnit.setComment(rs.getString("comment"));
				stateAdminUnit.setFromDate(rs.getDate("fromDate"));
				stateAdminUnit.setToDate(rs.getDate("toDate"));
				stateAdminUnit.setState_admin_unit_type_id(rs.getInt("state_admin_unit_type_id"));

				stateAdminUnits.add(stateAdminUnit);
			}

			return stateAdminUnits;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(st);
		}	
	}

	
}
