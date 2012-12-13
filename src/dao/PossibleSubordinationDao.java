package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

public class PossibleSubordinationDao extends BorderGuardDao {

	
	public PossibleSubordinationDao() {
		super();
	}
	
	
	public PossibleSubordinationDao(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	
	// Replace boss
	
	
	public void replaceBossRelation(Integer oldBossID, Integer newBossID, Integer stateAdminUnitTypeID) {
		Integer oldRelationId = getPossibleSubordinationIdByIDs(oldBossID, stateAdminUnitTypeID);
		
		if (oldRelationId != null){
			closePossibleSubordinationByRelationID(oldRelationId);
		}
		
		if (newBossID != null){
			addPossibleSubordinationByIDs(newBossID, stateAdminUnitTypeID);
		}
	}


	// Workers 
	
	
	private Integer getPossibleSubordinationIdByIDs(Integer bossID,	Integer stateAdminUnitTypeID) {
		if (bossID == null || stateAdminUnitTypeID == null) {
			return null;
		}
		
		Integer possibleSubordinationID = null;
		PreparedStatement ps 			= null;
		ResultSet rs 					= null;
		
		try {
			String sql = "SELECT 	possible_subordination_id " +
						 "FROM   	possible_subordination " +
						 "WHERE 	state_admin_unit_type_id = ? " +
						 "  AND		possible_subordinate_type_id = ? " +
						 "  AND		opened <= NOW() " +
						 "  AND		closed >= NOW() ";
			
		    ps = super.getConnection().prepareStatement(sql);	 
		    ps.setInt(1, bossID);
		    ps.setInt(2, stateAdminUnitTypeID);
		    rs = ps.executeQuery();

		    if (rs.next()) {
				possibleSubordinationID = rs.getInt("possible_subordination_id");
			} else {
				System.out.println("Possible_subordination not found for boss ID: " + bossID + " typeID:" + stateAdminUnitTypeID);
			}
	    
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}
		
		return possibleSubordinationID;
	}


	private void closePossibleSubordinationByRelationID(Integer possibleSubordinationID) {
		if (possibleSubordinationID == null)
		{
			return;
		}
		
		PreparedStatement ps = null;
		
		try {
			String sql = "UPDATE	possible_subordination  " 	+	
						 "SET    	closedBy = 'Admin', " 		+ 	
				 		 "	 		closed   = NOW() " 			+	
						 "WHERE 	possible_subordination_id = ?";  
			
		    ps = super.getConnection().prepareStatement(sql);	 
		    ps.setInt(1, possibleSubordinationID);
		    ps.executeUpdate();
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
		    DbUtils.closeQuietly(ps);
		}			
	}


	private void addPossibleSubordinationByIDs(Integer bossID, Integer stateAdminUnitTypeID) {
		if (bossID == null || stateAdminUnitTypeID == null) {
			return;
		}
		
		PreparedStatement ps = null;
		ResultSet rs 		 = null;
		
		try {
			String sql = "INSERT INTO possible_subordination " +
						 "(" +
						 "	openedby, " +
						 "  opened, " +
						 "  changedby, " +
						 "  changed, " +
						 "  closedby, " +
						 "  closed, " +
						 "  comment, " +
						 "  state_admin_unit_type_id, " +
						 "  possible_subordinate_type_id " +
						 ")" +
						 "VALUES " +
						 "(" +
						 "  'admin', " +
						 "  NOW(), " +
						 "  'admin', " +
						 "  NOW(), " +
						 "  'admin', " +
						 "  '2999-12-31', " +
						 "  '', " +
						 "  ?, " +
						 "  ?" +
						 ")";
					
		    ps = super.getConnection().prepareStatement(sql);	 
		    ps.setInt(1, bossID);
		    ps.setInt(2, stateAdminUnitTypeID);
		    ps.executeUpdate();
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}		
	}

	

	public void removeSubOrdinateRelation(Integer id, Integer subId) {
		if (id == null || subId == null) return;

		Integer oldRelationId = getPossibleSubordinationIdByIDs(id, subId);
		
		if (oldRelationId != null){
			closePossibleSubordinationByRelationID(oldRelationId);
		}
	}

	

	public void addSubOrdinateRelation(Integer id, Integer subId) {
		if (id == null || subId == null) return;
		
		addPossibleSubordinationByIDs(id, subId);
	}
}
