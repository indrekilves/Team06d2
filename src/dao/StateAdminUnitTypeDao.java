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
	
	
	
	
	public List<StateAdminUnitType> getAllPossibleBossStateAdminUnitTypesByType(StateAdminUnitType stateAdminUnitType) {
		List<StateAdminUnitType> possibleTypes = new ArrayList<StateAdminUnitType>();
		List<StateAdminUnitType> allTypes = getAllStateAdminUnitTypes();
		
		for (StateAdminUnitType validateType : allTypes) {
			if (isTypeValidForBoss(validateType, stateAdminUnitType)){
				possibleTypes.add(validateType);
			}
		}
		
		return possibleTypes;
	}
		

	private boolean isTypeValidForBoss(StateAdminUnitType validateType,	StateAdminUnitType stateAdminUnitType) {
		// can't be itself
		if (validateType.getState_admin_unit_type_id() == stateAdminUnitType.getState_admin_unit_type_id()){
			return false;
		}
		
		// can't be subordinate
		List<StateAdminUnitType> subOrdinates = stateAdminUnitType.getSubordinateAdminUnitTypes();
		boolean isSubordinate = isTypeASubordinate(validateType, subOrdinates);
		if (isSubordinate) {
			return false;
		}
			
		return true;
	}

	
	
	public List<StateAdminUnitType> getAllPossibleSubordinateStateAdminUnitTypesByType(StateAdminUnitType stateAdminUnitType) {
		List<StateAdminUnitType> possibleTypes = new ArrayList<StateAdminUnitType>();
		List<StateAdminUnitType> allTypes = getAllStateAdminUnitTypes();
		
		for (StateAdminUnitType validateType : allTypes) {
			if (isTypeValidForSubordinate(validateType, stateAdminUnitType)){
				possibleTypes.add(validateType);
			}
		}
		
		return possibleTypes;	
	}
	
	
	private boolean isTypeValidForSubordinate(StateAdminUnitType validateType, StateAdminUnitType stateAdminUnitType) {
		// can't be itself
		if (validateType.getState_admin_unit_type_id() == stateAdminUnitType.getState_admin_unit_type_id()){
			return false;
		}
		

		// can't be subordinate
		List<StateAdminUnitType> subOrdinates = stateAdminUnitType.getSubordinateAdminUnitTypes();
		boolean isSubordinate = isTypeASubordinate(validateType, subOrdinates);
		if (isSubordinate) {
			return false;
		}
		
		// can't be boss
		StateAdminUnitType boss = stateAdminUnitType.getBossAdminUnitType();
		boolean isBoss = isTypeABoss(validateType, boss);
		if (isBoss) {
			return false;
		}
		
		
		return true;	
	}
	
	
	private boolean isTypeASubordinate(StateAdminUnitType validateType,	List<StateAdminUnitType> subOrdinates) {
		boolean isSubOrdinate = false;
		
		if (subOrdinates != null) {
			for (StateAdminUnitType subOrdinate : subOrdinates) {
				if (validateType.getState_admin_unit_type_id() == subOrdinate.getState_admin_unit_type_id()){
					isSubOrdinate = true;
				} else {
					// is subOrdinate of subOrdinate
					List<StateAdminUnitType> subOrdinateSubOrdinates = getSubOrdinateAdminUnitTypesById(subOrdinate.getState_admin_unit_type_id());
					if (subOrdinateSubOrdinates != null) {
						isSubOrdinate = isTypeASubordinate(validateType, subOrdinateSubOrdinates);
					}
					 
				}
			}			
		}
		
		return isSubOrdinate;
	}
	
	
	private boolean isTypeABoss(StateAdminUnitType validateType, StateAdminUnitType boss) {
		boolean isBoss = false;
		
		if (boss != null){						
			if (validateType.getState_admin_unit_type_id() == boss.getState_admin_unit_type_id()){
				isBoss = true;
			} else {
				// is bosses boss				
				StateAdminUnitType bossesBoss = getBossAdminUnitTypeById(boss.getState_admin_unit_type_id());
				if (bossesBoss != null) {
					isBoss = isTypeABoss(validateType, bossesBoss);
				}
			}
		}

		return isBoss;
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
	
	

	
	
	// Insert
	
	
	
	
	public Integer insertStateAdminUnitTypeByType(StateAdminUnitType stateAdminUnitType) {
		if (stateAdminUnitType == null)
		{
			return null;
		}
		
		PreparedStatement ps      = null;
		PreparedStatement psId    = null;
		ResultSet         rs      = null;
		Integer           addedId = null; 
		
		try {
			String sql = 	"INSERT INTO state_admin_unit_type " +
							"(" +
							"  openedby, " +
							"  opened, " +
							"  changedby, " +
							"  changed, " +
							"  closedby, " +
							"  closed, " +
							"  code, " +
							"  name, " +
							"  comment, " +
							"  fromdate, " +
							"  todate" +
							") " +
							"VALUES " +
							"(" +
							"  'admin', " +
							"  NOW(), " +
							"  'admin', " +
							"  NOW(), " +
							"  '', " +
							"  '2999-12-31', " +
							"  ?, " +				// 1	code
							"  ?, " +				// 2	name
							"  ?, " + 				// 3	comment
							"  ?, " +				// 4	fromDate
							"  ?)";					// 5	toDate
		
		    java.sql.Date fromDate = getSqlDateFromJavaDate(stateAdminUnitType.getFromDate());
		    java.sql.Date toDate = getSqlDateFromJavaDate(stateAdminUnitType.getToDate());
		
		    ps = super.getConnection().prepareStatement(sql);	 
		   
		    ps.setString(1, stateAdminUnitType.getCode());
		    ps.setString(2, stateAdminUnitType.getName());
		    ps.setString(3, stateAdminUnitType.getComment());
			ps.setDate(  4, fromDate);
		    ps.setDate(  5, toDate);
		    
		    ps.executeUpdate();
		    
		    psId = super.getConnection().prepareStatement("CALL IDENTITY()");
			rs = psId.executeQuery();
			rs.next();
			addedId = rs.getInt(1);		    
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		    DbUtils.closeQuietly(psId);
		}
		
		return addedId;			
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
		
		    java.sql.Date fromDate = getSqlDateFromJavaDate(stateAdminUnitType.getFromDate());
		    java.sql.Date toDate = getSqlDateFromJavaDate(stateAdminUnitType.getToDate());
		
		    ps = super.getConnection().prepareStatement(sql);	 
		   
		    ps.setString(1, stateAdminUnitType.getCode());
		    ps.setString(2, stateAdminUnitType.getName());
		    ps.setString(3, stateAdminUnitType.getComment());
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

	
		
	
	public boolean isCodeExisting(String code) {
		boolean isCodeExisting = false;
		
		if (code == null || code.length() < 1) {
			return isCodeExisting;
		}
			
	    
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT 1 " +
						 "FROM   state_admin_unit_type " +
						 "WHERE  code = ? ";
			
			ps = super.getConnection().prepareStatement(sql);	 
		    ps.setString(1, code);		    
		    rs = ps.executeQuery();

		    if (rs.next()) {
		    	isCodeExisting = rs.getBoolean(1);
		    }

		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}

		return isCodeExisting;
	}

		
}
