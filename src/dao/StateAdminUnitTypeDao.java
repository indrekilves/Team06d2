package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import beans.StateAdminUnitType;


public class StateAdminUnitTypeDao extends BorderGuardDao{
	

	public StateAdminUnitTypeDao() {
		super();
	}

	public StateAdminUnitTypeDao(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}
	
	

	public List<StateAdminUnitType> getAllStateAdminUnitTypes() {
	    List<StateAdminUnitType> stateAdminUnitTypes = new ArrayList<StateAdminUnitType>();

	    Statement st = null;
	    ResultSet rs = null;
		try {
			st = super.getConnection().createStatement();
		    rs = st.executeQuery(	"SELECT * " +
		    						"FROM   state_admin_unit_type " +
		    						"WHERE 	opened <= NOW() " +
		    						"  AND	closed >= NOW() ");

		    while (rs.next()) {
		    	stateAdminUnitTypes.add(createStateAdminUniTypeFromResultSet(rs));
		    }

		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(st);
		}
		
	    return stateAdminUnitTypes;
	}

	
	
	public StateAdminUnitType getStateAdminUnitTypeById(Integer state_admin_unit_type_id) {
		if (state_admin_unit_type_id == null) {
			return null;
		}
		
	    StateAdminUnitType stateAdminUnitType = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * " +
						 "FROM   state_admin_unit_type " +
						 "WHERE  state_admin_unit_type_id = ? " +
						 "  AND	 opened <= NOW() " +
						 "  AND	 closed >= NOW() ";
			
			ps = super.getConnection().prepareStatement(sql);	 
		    ps.setInt(1, state_admin_unit_type_id);		    
		    rs = ps.executeQuery();

		    if (rs.next()) {
		    	stateAdminUnitType = createStateAdminUniTypeFromResultSet(rs);
			} else {
				System.out.println("State Admin Unit Type not found for ID: " + state_admin_unit_type_id);
			}

		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}


		return stateAdminUnitType;
	}

	
	private StateAdminUnitType createStateAdminUniTypeFromResultSet(ResultSet rs) throws SQLException {
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

	
	
	public StateAdminUnitType getStateAdminUnitTypeByIdWithRelations(Integer state_admin_unit_type_id) {
		if (state_admin_unit_type_id == null) {
			return null;
		}		
	
		// type
		StateAdminUnitType stateAdminUnitType = getStateAdminUnitTypeById(state_admin_unit_type_id);
	    if (stateAdminUnitType == null){
	    	return null;
	    }	    	
	    
		// boss
    	StateAdminUnitType bossAdminUnitType = getBossAdminUnitTypeById(state_admin_unit_type_id);
    	stateAdminUnitType.setBossAdminUnitType(bossAdminUnitType);
    	
    	// subordinates
    	List<StateAdminUnitType> subOrdinateAdminUnitTypes = getSubOrdinateAdminUnitTypesById(state_admin_unit_type_id);
		stateAdminUnitType.setSubordinateAdminUnitTypes(subOrdinateAdminUnitTypes);
		
		
    	return stateAdminUnitType;
	}
	

	private StateAdminUnitType getBossAdminUnitTypeById(Integer state_admin_unit_type_id) {
		if (state_admin_unit_type_id == null) {
			return null;
		}
		
		StateAdminUnitType bossAdminUnitType = null;
		Integer bossID = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT 	state_admin_unit_type_id " +
						 "FROM   	possible_subordination " +
						 "WHERE 	possible_subordinate_type_id = ? " +
						 "  AND		opened <= NOW() " +
						 "  AND		closed >= NOW() ";
			
		    ps = super.getConnection().prepareStatement(sql);	 
		    ps.setInt(1, state_admin_unit_type_id);		    
		    rs = ps.executeQuery();

		    if (rs.next()) {
				bossID = rs.getInt("state_admin_unit_type_id");
			} else {
				System.out.println("Boss not found for ID: " + state_admin_unit_type_id);
			}
	    
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}

		
		if (bossID != null) {
			bossAdminUnitType = getStateAdminUnitTypeById(bossID);
		}
		
		return bossAdminUnitType;
	}
	
	
	

	private List<StateAdminUnitType> getSubOrdinateAdminUnitTypesById(Integer state_admin_unit_type_id) {
		if (state_admin_unit_type_id == null) {
			return null;
		}
		List<StateAdminUnitType> subOrdinateAdminUnitTypes = new ArrayList<StateAdminUnitType>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT 	possible_subordinate_type_id " +
						 "FROM   	possible_subordination " +
						 "WHERE 	state_admin_unit_type_id = ? " +
						 "  AND		opened <= NOW() " +
						 "  AND		closed >= NOW() ";
			
		    ps = super.getConnection().prepareStatement(sql);	 
		    ps.setInt(1, state_admin_unit_type_id);		    
		    rs = ps.executeQuery();

		    while (rs.next()) {
		    	
				Integer subID = rs.getInt("possible_subordinate_type_id");
				StateAdminUnitType subType = getStateAdminUnitTypeById(subID); 
		    	
				subOrdinateAdminUnitTypes.add(subType);
		    	
				System.out.println("Subordinate for ID:" + state_admin_unit_type_id + " is ID:" + subID);				
		    }
		    

		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}		

		
		return subOrdinateAdminUnitTypes;
	}
	
	
	
	public List<StateAdminUnitType> getPossibleBossStateAdminUnitTypesById(Integer id) {
		// TODO leia k6ik kellele ma ise ei allu
		return null;
	}

	
	
	
	// Update
	
	
	public void updateStateAdminUnitTypeByType(StateAdminUnitType stateAdminUnitType) {
		if (stateAdminUnitType == null)
		{
			return;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sql = "UPDATE state_admin_unit_type  " 	+	
						 "SET   code      = ?,  "         	+	//  1
						 "		name      = ?,  " 			+ 	//  2
						 "		comment   = ?,  " 			+	//  3
						 "		fromDate  = ?, " 			+	//  4
						 "		toDate    = ?," 			+	//  5
						 "		changedBy = 'Admin', " 		+ 	
				 		 "		changed   = NOW() " 		+	
						 "WHERE state_admin_unit_type_id = ?";  //  6
			
		    ps = super.getConnection().prepareStatement(sql);	 
		   
		    ps.setString(1, stateAdminUnitType.getCode());
		    ps.setString(2, stateAdminUnitType.getName());
		    ps.setString(3, stateAdminUnitType.getComment());
		    
		    java.sql.Date fromDate = getSqlDateFromJavaDate(stateAdminUnitType.getFromDate());
		    java.sql.Date toDate = getSqlDateFromJavaDate(stateAdminUnitType.getToDate());
			ps.setDate(  4, fromDate);
		    ps.setDate(  5, toDate);
		
		    ps.setInt(   6, stateAdminUnitType.getState_admin_unit_type_id());
		    
		    ps.executeUpdate();
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}	
	}

	
	private java.sql.Date getSqlDateFromJavaDate(Date javaDate) {
		return new java.sql.Date(javaDate.getTime()); 
	}


	

	
	
}
