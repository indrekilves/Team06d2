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

	
	

	// Get all POSSIBLE subordinate units 

	
	
	
	public List<StateAdminUnit> getAllPossibleSubordinateUnitsByUnit(StateAdminUnit unit) {
		List<StateAdminUnit> possibleUnits = new ArrayList<StateAdminUnit>();
		List<StateAdminUnit> allUnits = getAllUnits();
		
		if (!allUnits.isEmpty()){
			for (StateAdminUnit validateUnit : allUnits) {
				if (isUnitValidForSubordinate(validateUnit, unit)){
					possibleUnits.add(validateUnit);
				}
			}		
		}
		
		return possibleUnits;		
	}
	
	

	
	private boolean isUnitValidForSubordinate(StateAdminUnit validateUnit, StateAdminUnit unit) {
		// can't be itself
		if (validateUnit.getState_admin_unit_id() == unit.getState_admin_unit_id()){
			return false;
		}
		

		// can't be subordinate
		List<StateAdminUnit> subOrdinates = unit.getSubordinateUnits();
		if (isUnitASubordinate(validateUnit, subOrdinates)) {
			return false;
		}
		
		// can't be boss
		StateAdminUnit bossUnit = unit.getBossUnit();
		if (isUnitABoss(validateUnit, bossUnit)) {
			return false;
		}
		
		// type can't be subOrdinated of current unit type
		if (isTypeValidForSubordinate(validateUnit, unit) == false){
			return false;
		}
		
		return true;	
	}
	

	private boolean isUnitABoss(StateAdminUnit validateUnit, StateAdminUnit bossUnit) {
		if (bossUnit != null){						
			if (validateUnit.getState_admin_unit_id() == bossUnit.getState_admin_unit_id()){
				return true;
			} else {
				// is bosses boss				
				StateAdminUnit bossesBoss = getBossUnitById(bossUnit.getState_admin_unit_id());
				if (bossesBoss != null) {
					if (isUnitABoss(validateUnit, bossesBoss)){
						return true;
					}
				}
			}
		}

		return false;
	}

	
	

	private boolean isTypeValidForSubordinate(StateAdminUnit validateUnit, StateAdminUnit unit) {
		StateAdminUnitType validateType = validateUnit.getType();
		StateAdminUnitType currentType 	= getTypeWithRelationsById(unit.getState_admin_unit_type_id());
		
		if (validateType == null || currentType == null){
			return true;
		}

		
		// can't be same type as current unit type
		if (validateType.getState_admin_unit_type_id() == currentType.getState_admin_unit_type_id()){
			return false;
		}
		
		
		// can't be with type that's boss for current unit type
		if (isTypeBossForCurrentType(validateType, currentType)) {
			return false;
		}
		
		return true;
	}
	
	
	private boolean isTypeBossForCurrentType(StateAdminUnitType validateType, StateAdminUnitType currentType) {
		if (validateType == null || currentType == null){
			return false;
		}
		
		if (typeDao.isTypeABoss(validateType, currentType)){
			return true;
		}
		
		return false;
	}
	
	
	
	
	// Get all POSSIBLE boss units 
	

	

	public List<StateAdminUnit> getAllPossibleBossUnitsByUnit(StateAdminUnit unit) {
		List<StateAdminUnit> possibleUnits = new ArrayList<StateAdminUnit>();
		List<StateAdminUnit> allUnits = getAllUnits();
		
		if (!allUnits.isEmpty()) {
			for (StateAdminUnit validateUnit : allUnits) {
				if (isUnitValidForBoss(validateUnit, unit)){
					possibleUnits.add(validateUnit);
				}
			}
		}
		
		return possibleUnits;
	}
	
	

	private boolean isUnitValidForBoss(StateAdminUnit validateUnit, StateAdminUnit unit) {
		// can't be itself
		if (validateUnit.getState_admin_unit_id() == unit.getState_admin_unit_id()){
			return false;
		}
		
		// can't be subordinate
		List<StateAdminUnit> subOrdinates = unit.getSubordinateUnits();
		if (isUnitASubordinate(validateUnit, subOrdinates)) {
			return false;
		}
		
		if (isTypeValidForBossUnit(validateUnit, unit) == false){
			return false;
		}
		
		return true;		
	}

	
	private boolean isUnitASubordinate(StateAdminUnit validateUnit,	List<StateAdminUnit> subOrdinates) {
		if (subOrdinates != null && !subOrdinates.isEmpty()) {
			for (StateAdminUnit subOrdinate : subOrdinates) {
				if (validateUnit.getState_admin_unit_id() == subOrdinate.getState_admin_unit_id()){
					return true;
				} else {
					// is subOrdinate of subOrdinate
					List<StateAdminUnit> subOrdinateSubOrdinates = getSubOrdinateUnitsById(subOrdinate.getState_admin_unit_id());
					if (subOrdinateSubOrdinates != null) {
						if (isUnitASubordinate(validateUnit, subOrdinateSubOrdinates)) {
							return true;
						}
					}
					 
				}
			}			
		}
		
		return false;
	}



	private boolean isTypeValidForBossUnit(StateAdminUnit validateUnit,StateAdminUnit unit) {
		StateAdminUnitType validateType = validateUnit.getType();
		StateAdminUnitType currentType 	= getTypeWithRelationsById(unit.getState_admin_unit_type_id());
		
		if (validateType == null || currentType == null){
			return true;
		}

		
		// can't be same type as current unit type
		if (validateType.getState_admin_unit_type_id() == currentType.getState_admin_unit_type_id()){
			return false;
		}
		
		
		// can't be with type that's subOrdinate for current unit type
		if (isTypeSubordinateToCurrentType(validateType, currentType)) {
			return false;
		}
		
		return true;
	}
	
	
	private StateAdminUnitType getTypeWithRelationsById(Integer typeId) {
		if (typeId == null) return null;
		
		return typeDao.getStateAdminUnitTypeByIdWithRelations(typeId);
	}


	private boolean isTypeSubordinateToCurrentType(StateAdminUnitType validateType, StateAdminUnitType currentType) {
		if (validateType == null || currentType == null){
			return false;
		}
		
		List<StateAdminUnitType> subOrdinateTypesForCurrentType = currentType.getSubordinateAdminUnitTypes();
		if (typeDao.isTypeASubordinate(validateType, subOrdinateTypesForCurrentType)){
			return true;
		}
		
		return false;
	}
	

	
	
	// Get all units

	
	

	public List<StateAdminUnit> getAllUnits() {
	    List<StateAdminUnit> units = new ArrayList<StateAdminUnit>();

	    Statement st = null;
	    ResultSet rs = null;
		try {
			st = getConnection().createStatement();
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

	

	// Get one unit - with relations 
	
	

	/**
	 * NOTE: only direct relations are attached. 
	 */
	public StateAdminUnit getUnitWithRelationsById(Integer id) {
		if (id == null) return null;
	
		// unit
		StateAdminUnit unit = getUnitById(id);
	    if (unit == null){
	    	return null;
	    }	    	
	    
		// boss
    	StateAdminUnit bossUnit = getBossUnitById(id);
    	unit.setBossUnit(bossUnit);
    	    	
    	// subordinates
    	List<StateAdminUnit> subOrdinateUnits = getSubOrdinateUnitsById(id);
		unit.setSubordinateUnits(subOrdinateUnits);
		
    	return unit;
    }


	private StateAdminUnit getBossUnitById(Integer id) {
		if (id == null) return null;
		
		Integer				bossID 		= null;
		StateAdminUnit 		bossUnit 	= null;
		PreparedStatement 	ps 			= null;
		ResultSet 			rs 			= null;
		
		try {
			String sql = "SELECT 	boss_unit_id " +
						 "FROM   	admin_subordination " +
						 "WHERE 	subordinate_unit_id = ? " +
						 "  AND		opened <= NOW() " +
						 "  AND		closed >= NOW() ";
			
		    ps = getConnection().prepareStatement(sql);	 
		    ps.setInt(1, id);		    
		    rs = ps.executeQuery();

		    if (rs.next()) {
				bossID = rs.getInt("boss_unit_id");
			} else {
				System.out.println("Boss not found for ID: " + id);
			}
	    
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}

		
		if (bossID != null) {
			bossUnit = getUnitById(bossID);
		}
		
		return bossUnit;
	}

	
	private List<StateAdminUnit> getSubOrdinateUnitsById(Integer id) {
		if (id == null) {
			return null;
		}
		
		List<StateAdminUnit> 	subOrdinateUnits 	= new ArrayList<StateAdminUnit>();
		PreparedStatement 		ps 					= null;
		ResultSet 				rs 					= null;
		
		try {
			String sql = "SELECT 	subordinate_unit_id " +
						 "FROM   	admin_subordination " +
						 "WHERE 	boss_unit_id = ? " +
						 "  AND		opened <= NOW() " +
						 "  AND		closed >= NOW() ";
			
		    ps = getConnection().prepareStatement(sql);	 
		    ps.setInt(1, id);		    
		    rs = ps.executeQuery();

		    while (rs.next()) {
		    	
				Integer subID = rs.getInt("subordinate_unit_id");
				StateAdminUnit subUnit = getUnitById(subID); 
		    	if (subUnit != null) {
					subOrdinateUnits.add(subUnit);
		    	}
		    	
				System.out.println("Subordinate for ID:" + id + " is ID:" + subID);				
		    }
		    		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}		

		
		return subOrdinateUnits;
	}
	
	
	
	
	// Get one unit - without relations 
	
	
	

	public StateAdminUnit getUnitById(Integer id) {
		if (id == null) {
			return null;
		}
		
	    StateAdminUnit 		unit = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		
		try {
			String sql = "SELECT * " +
						 "FROM   state_admin_unit " +
						 "WHERE  state_admin_unit_id = ? " +
						 "  AND	 opened <= NOW() " +
						 "  AND	 closed >= NOW() ";
			
			ps = getConnection().prepareStatement(sql);	 
		    ps.setInt(1, id);		    
		    rs = ps.executeQuery();

		    if (rs.next()) {
		    	unit = createUnitFromResultSet(rs);
			} 

		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}

		return unit;	
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

	
	
	// Insert unit
	
	
	
	public Integer insertUnitByUnit(StateAdminUnit unit) {
		if (unit == null) return null;
		
		
		PreparedStatement ps      = null;
		PreparedStatement psId    = null;
		ResultSet         rs      = null;
		Integer           addedId = null; 
		
		try {
			String sql = 	"INSERT INTO state_admin_unit " +
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
							"  todate," +
							"  state_admin_unit_type_id" +
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
							"  ?, " +				// 5	toDate
							"  ?  " +				// 6	typeId
							")";					
		
		    ps = getConnection().prepareStatement(sql);	 
		   
		    ps.setString(1, unit.getCode());
		    ps.setString(2, unit.getName());
		    ps.setString(3, unit.getComment());
			ps.setDate(  4, getSqlDateFromJavaDate(unit.getFromDate()));
		    ps.setDate(  5, getSqlDateFromJavaDate(unit.getToDate()));
		    ps.setInt(   6, unit.getState_admin_unit_type_id());

		    ps.executeUpdate();
		    
		    psId = getConnection().prepareStatement("CALL IDENTITY()");
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
	
	
	
	// Update unit

	
	
	
	public void updateUnitByUnit(StateAdminUnit unit) {
		if (unit == null) return;
		
		PreparedStatement ps = null;
		
		try {
			String sql = "UPDATE state_admin_unit  " 			+	
						 "SET   code      = ?,  "      			+	//  1
						 "		name      = ?,  " 				+ 	//  2
						 "		comment   = ?,  " 				+	//  3
						 "		fromDate  = ?, " 				+	//  4
						 "		toDate    = ?," 				+	//  5
						 "		changedBy = 'Admin', " 			+ 	
				 		 "		changed   = NOW(), " 			+
				 		 "      state_admin_unit_type_id = ?" 	+	//  6
						 "WHERE state_admin_unit_id = ?"; 			//  7
		
		    ps = getConnection().prepareStatement(sql);	 
		   
		    ps.setString(1, unit.getCode());
		    ps.setString(2, unit.getName());
		    ps.setString(3, unit.getComment());
			ps.setDate(  4, getSqlDateFromJavaDate(unit.getFromDate()));
		    ps.setDate(  5, getSqlDateFromJavaDate(unit.getToDate()));
		    ps.setInt(   6, unit.getState_admin_unit_type_id()); 
		    ps.setInt(   7, unit.getState_admin_unit_id());
		    
		    ps.executeUpdate();
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
		    DbUtils.closeQuietly(ps);
		}	
	}

	
	
	
	// Integrity checks 

	
	
	
	public boolean isCodeExisting(String code) {
		boolean isCodeExisting = false;
		
		if (code == null || code.length() < 1) {
			return isCodeExisting;
		}
			
	    
		PreparedStatement	ps = null;
		ResultSet 			rs = null;
		try {
			String sql = "SELECT 1 " +
						 "FROM   state_admin_unit " +
						 "WHERE  UPPER(code) = ? ";
			
			ps = getConnection().prepareStatement(sql);	 
		    ps.setString(1, code.toUpperCase());		    
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

	
	
	
	// Close Unit

	
	
	
	public void closeUnitById(int id) {
		PreparedStatement ps = null;
		try {
			String sql = "UPDATE	state_admin_unit  " 	+	
						 "SET    	closedBy = 'Admin', "	+ 	
				 		 "	 		closed   = NOW() " 		+	
						 "WHERE 	state_admin_unit_id = ?";  
			
		    ps = getConnection().prepareStatement(sql);	 
		    ps.setInt(1, id);
		    ps.executeUpdate();
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
		    DbUtils.closeQuietly(ps);
		}	
	}

	
	
	
	// Get all subordinates 

	
	
	
	public List<StateAdminUnit> getAllUnitsWithSuboridinatesByTypeIdAndDate(Integer typeId, Date date) {
		// TODO by date
		
		List<StateAdminUnit> plainUnits 		= getAllUnitsWithDirectRelationsByTypeIdAndDate(typeId, date);
		List<StateAdminUnit> unitsWithSubOrds 	= new ArrayList<StateAdminUnit>();
		
		if (plainUnits == null) return null;
		
		for (StateAdminUnit unit : plainUnits) {
			
			if (unit != null){
			
				List<StateAdminUnit> subOrdinates = getAllSubordinatesByUnitAndDate(unit, date);
				if (subOrdinates != null && !subOrdinates.isEmpty()){
					unit.setSubordinateUnits(subOrdinates);
				}
				
				unitsWithSubOrds.add(unit);
			}
		}		
		
		return unitsWithSubOrds;
	}
	


	private List<StateAdminUnit> getAllUnitsWithDirectRelationsByTypeIdAndDate(Integer typeId, Date date) {
		if (typeId == null || date == null) return null;
		
	    List<StateAdminUnit>	units = new ArrayList<StateAdminUnit>();
		PreparedStatement 		ps = null;
		ResultSet 				rs = null;
		
		try {
			String sql = "SELECT state_admin_unit_id " +
						 "FROM   state_admin_unit " +
						 "WHERE  state_admin_unit_type_id = ? " +	//	1
						 "  AND	 opened   <= ? " 				+	//	2		
						 "  AND	 closed   >= ? " 				+	//	3
						 "  AND  fromDate <= ? " 				+	// 	4
						 "  AND  toDate   >= ?" 				;	// 	5
			
			ps = getConnection().prepareStatement(sql);	 
		    ps.setInt( 1, typeId);		    
		    ps.setDate(2, getSqlDateFromJavaDate(date));
		    ps.setDate(3, getSqlDateFromJavaDate(date));
		    ps.setDate(4, getSqlDateFromJavaDate(date));
		    ps.setDate(5, getSqlDateFromJavaDate(date));
		    
		    rs = ps.executeQuery();

		    while (rs.next()) {
		    	Integer unitId = rs.getInt(1);
		    	if (unitId != null){
			    	StateAdminUnit unit = getUnitWithSubordinatesByIdAndDate(unitId, date);
			    	if (unit != null) {
			    		units.add(unit);
			    	} 	
		    	}
		    }

		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}

		return units;	
	}

	


	private StateAdminUnit getUnitWithSubordinatesByIdAndDate(Integer id, Date date) {
		if (id == null || date == null) return null;
		
		// unit
		StateAdminUnit unit = getUnitByIdAndDate(id, date);
	    if (unit == null){
	    	return null;
	    }	    	
    	    	
    	// subordinates
    	List<StateAdminUnit> subOrdinateUnits = getSubOrdinateUnitsByIdAndDate(id, date);
		unit.setSubordinateUnits(subOrdinateUnits);
		
    	return unit;
	}
	

	private StateAdminUnit getUnitByIdAndDate(Integer id, Date date) {
		if (id == null || date == null) return null;
		
		
	    StateAdminUnit 		unit = null;
		PreparedStatement 	ps = null;
		ResultSet 			rs = null;
		
		try {
			String sql = "SELECT * " +
						 "FROM   state_admin_unit " +
						 "WHERE  state_admin_unit_id = ? " +	//	1
						 "  AND	 opened             <= ? " +	//	2
						 "  AND	 closed             >= ? " +	//	3
						 "  AND  fromDate           <= ? " +	//	4
						 "  AND  toDate             >= ? " ;	//	5
			
			ps = getConnection().prepareStatement(sql);	 
		    ps.setInt( 1, id);	
		    ps.setDate(2, getSqlDateFromJavaDate(date));
		    ps.setDate(3, getSqlDateFromJavaDate(date));
		    ps.setDate(4, getSqlDateFromJavaDate(date));
		    ps.setDate(5, getSqlDateFromJavaDate(date));

		    rs = ps.executeQuery();

		    if (rs.next()) {
		    	unit = createUnitFromResultSet(rs);
			} 

		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}

		return unit;				
	}
	
	
	private List<StateAdminUnit> getSubOrdinateUnitsByIdAndDate(Integer id,	Date date) {
		if (id == null || date == null) return null;
		
		
		List<StateAdminUnit> 	subOrdinateUnits 	= new ArrayList<StateAdminUnit>();
		PreparedStatement 		ps 					= null;
		ResultSet 				rs 					= null;
		
		try {
			String sql = "SELECT 	subordinate_unit_id " +
						 "FROM   	admin_subordination " +
						 "WHERE 	boss_unit_id = ? "    +	//	1
						 "  AND		opened      <= ? "    +	//	2
						 "  AND		closed      >= ? "    ;	//	3
			
		    ps = getConnection().prepareStatement(sql);	 
		    ps.setInt(1, id);		 
		    ps.setDate(2, getSqlDateFromJavaDate(date));
		    ps.setDate(3, getSqlDateFromJavaDate(date));

		    rs = ps.executeQuery();

		    while (rs.next()) {
		    	
				Integer subID = rs.getInt("subordinate_unit_id");
				StateAdminUnit subUnit = getUnitByIdAndDate(subID, date);
		    	if (subUnit != null) {
					subOrdinateUnits.add(subUnit);
		    	}
		    	
				System.out.println("Subordinate for ID:" + id + " is ID:" + subID + " on date: " + date);				
		    }
		    		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}		

		
		return subOrdinateUnits;
	}
	
	

	private List<StateAdminUnit> getAllSubordinatesByUnitAndDate(StateAdminUnit unit, Date date) {
		if (unit == null) return null;
		
	    List<StateAdminUnit> allSubOrdinates	= new ArrayList<StateAdminUnit>();
	    List<StateAdminUnit> directSubOrdinates = unit.getSubordinateUnits();
	    
	    if (directSubOrdinates == null || directSubOrdinates.isEmpty()) return null;
	    
	    for (StateAdminUnit subUnit : directSubOrdinates) {
	    	if (subUnit != null){
	        	allSubOrdinates.add(subUnit);
		    	
		    	StateAdminUnit subUnitWithRelations = getUnitWithSubordinatesByIdAndDate(subUnit.getState_admin_unit_id(), date);
		    	List<StateAdminUnit> subUnitSubOrdinates = getAllSubordinatesByUnitAndDate(subUnitWithRelations, date);
		    	
		    	if (subUnitSubOrdinates != null && !subUnitSubOrdinates.isEmpty()){
		    		allSubOrdinates.addAll(subUnitSubOrdinates);
		    	}
	    	}
	    	
		}
	    
		return allSubOrdinates;
	}










	
}
