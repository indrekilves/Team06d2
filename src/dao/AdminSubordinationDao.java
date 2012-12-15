package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.dbutils.DbUtils;

public class AdminSubordinationDao extends BorderGuardDao {

	
	public AdminSubordinationDao() {
		super();
	}

	
	
	public void replaceBossRelation(Integer oldBossId, Integer newBossId, Integer unitId) {
		Integer oldRelationId = getAdminSubordinationIdByIDs(oldBossId, unitId);
		
		if (oldRelationId != null){
			closeAdminSubordinationByRelationID(oldRelationId);
		}
		
		if (newBossId != null){
			addAdminSubordinationByIDs(newBossId, unitId);
		}		
	}



	private Integer getAdminSubordinationIdByIDs(Integer bossId, Integer unitId) {
		if (bossId == null || unitId == null) return null;

		
		Integer 			adminSubordinationID	= null;
		PreparedStatement	ps 						= null;
		ResultSet 			rs 						= null;
		
		try {
			String sql = "SELECT 	admin_subordination_id " 	+
						 "FROM   	admin_subordination "	 	+
						 "WHERE 	boss_unit_id = ? " 			+	// 1
						 "  AND		subordinate_unit_id = ? " 	+	// 2
						 "  AND		opened <= NOW() " +
						 "  AND		closed >= NOW() ";
			
		    ps = getConnection().prepareStatement(sql);	 
		    ps.setInt(1, bossId);
		    ps.setInt(2, unitId);
		    rs = ps.executeQuery();

		    if (rs.next()) {
				adminSubordinationID = rs.getInt("admin_subordination_id");
			} else {
				System.out.println("Admin_subordination_id not found for boss ID: " + bossId + " typeID:" + unitId);
			}
	    
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		}
		
		return adminSubordinationID;
	}
	



	private void closeAdminSubordinationByRelationID(Integer adminSubordinationId) {
		if (adminSubordinationId == null) return;
		
		PreparedStatement ps = null;
		try {
			String sql = "UPDATE	admin_subordination  " 		+	
						 "SET    	closedBy = 'Admin', " 		+ 	
				 		 "	 		closed   = NOW() " 			+	
						 "WHERE 	admin_subordination_id = ?";	// 1  
			
		    ps = getConnection().prepareStatement(sql);	 
		    ps.setInt(1, adminSubordinationId);
		    ps.executeUpdate();
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
		    DbUtils.closeQuietly(ps);
		}			
	}


	
	
	private void addAdminSubordinationByIDs(Integer bossId, Integer unitId) {
		if (bossId == null || unitId == null) return;
				
		PreparedStatement ps = null;
	
		try {
			String sql = "INSERT INTO admin_subordination " +
						 "(" +
						 "	openedby, " +
						 "  opened, " +
						 "  changedby, " +
						 "  changed, " +
						 "  closedby, " +
						 "  closed, " +
						 "  fromDate, " +
						 "  toDate, " +
						 "  boss_unit_ID, " +
						 "  subordinate_unit_ID " +
						 ")" +
						 "VALUES " +
						 "(" +
						 "  'admin', " +
						 "  NOW(), " +
						 "  'admin', " +
						 "  NOW(), " +
						 "  'admin', " +
						 "  '2999-12-31', " +
						 "  '1900-01-01', " +
						 "  '2999-12-31', " +
						 "  ?, " +					// 1 	BossId
						 "  ?" +					// 2	SubOrdinateId
						 ")";
					
		    ps = getConnection().prepareStatement(sql);	 
		    ps.setInt(1, bossId);
		    ps.setInt(2, unitId);
		    ps.executeUpdate();
		    
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
		    DbUtils.closeQuietly(ps);
		}				
	}


	

	public void removeSubOrdinateRelation(Integer id, Integer subId) {
		if (id == null || subId == null) return;

		Integer oldRelationId = getAdminSubordinationIdByIDs(id, subId);
		
		if (oldRelationId != null){
			closeAdminSubordinationByRelationID(oldRelationId);
		}		
	}



	public void addSubOrdinateRelation(Integer id, Integer subId) {
		if (id == null || subId == null) return;
		
		addAdminSubordinationByIDs(id, subId);
	}


}