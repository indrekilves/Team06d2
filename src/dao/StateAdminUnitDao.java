package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import beans.StateAdminUnit;
import beans.StateAdminUnitType;

public class StateAdminUnitDao extends BorderGuardDao{

	
	StateAdminUnitTypeDao typeDao = new StateAdminUnitTypeDao();
	
	public StateAdminUnitDao() {
		super();
	}


	public StateAdminUnitDao(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	
	

	public List<StateAdminUnit> getAllStateAdminUnits() {
	    List<StateAdminUnit> units = new ArrayList<StateAdminUnit>();

	    Statement st = null;
	    ResultSet rs = null;
		try {
			st = super.getConnection().createStatement();
		    rs = st.executeQuery(	"SELECT * " +
		    						"FROM   state_admin_unit " +
		    						"WHERE 	opened <= NOW() " +
		    						"  AND	closed >= NOW() ");

		    while (rs.next()) {
		    	StateAdminUnit unit = createUnitFromResultSet(rs);
		    	if (unit != null) {
		    		units.add(unit);
		    	}	
		    }

		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(st);
		}
		
	    return units;
	}

	
	private StateAdminUnit createUnitFromResultSet(ResultSet rs) throws SQLException {
		StateAdminUnit unit = new StateAdminUnit();
		
		unit.setState_admin_unit_id(rs.getInt("state_admin_unit_id"));
		unit.setOpenedBy(rs.getString("openedBy"));
		unit.setOpened(rs.getDate("opened"));
		unit.setChangedBy(rs.getString("changedBy"));
		unit.setChanged(rs.getDate("changed"));
		unit.setClosedBy(rs.getString("closedBy"));
		unit.setClosed(rs.getDate("closed"));
		unit.setCode(rs.getString("code"));
		unit.setName(rs.getString("name"));
		unit.setComment(rs.getString("comment"));
		unit.setFromDate(rs.getDate("fromDate"));
		unit.setToDate(rs.getDate("toDate"));
		unit.setState_admin_unit_type_id(rs.getInt("state_admin_unit_type_id"));

		// Add type
		Integer typeId = unit.getState_admin_unit_type_id();
		if (typeId != null){
			StateAdminUnitType type = typeDao.getStateAdminUnitTypeById(typeId);
			if (type != null){
				unit.setType(type);
			}
		}
		
		return unit;
	}
	

	
}
