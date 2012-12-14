package dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.DbUtils;

public class BorderGuardDao {


	private Connection connection;

    
    // Constructors 
    
    
	public void init() throws ServletException {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

    
	public BorderGuardDao() {
	    try {
            String db = "${user.home}/i377/Team06d/db;shutdown=true";
            connection = DriverManager.getConnection("jdbc:hsqldb:" + db, "sa", "");
	    } catch (Exception e) {
            throw new RuntimeException(e);
        }	
	}
	

	public BorderGuardDao(HttpServletRequest request, HttpServletResponse response) {
	    try {
	    	connection = createConnectionAndAddToSession(request);
	    } catch (Exception e) {
            throw new RuntimeException(e); 
        }		
	}

	

	public Connection createConnectionAndAddToSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Connection connection = (Connection) session.getAttribute("connection");

		if (connection == null) {
			try {
				Class.forName("org.hsqldb.jdbcDriver");
				String db = "${user.home}/i377/Team06d/db;shutdown=true";
				connection = DriverManager.getConnection("jdbc:hsqldb:" + db, "sa", "");
				
				session.setAttribute("connection", connection);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}	
		}
		
		return connection;
	}



	public Connection getConnection() {
		return connection;
	}
	
	
	
	
	// Helpers
		
	
	
	public java.sql.Date getSqlDateFromJavaDate(Date javaDate) {
		return new java.sql.Date(javaDate.getTime()); 
	}
	
	
	
	
	// Wrappers 
	
	

	public int executeUpdate(String expression) {

	    Statement st = null;
		try {
            st = connection.createStatement();
            return st.executeUpdate(expression);

		} catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(st);
        }		
	}
	
	
	public boolean execute(String sql){
	    Statement st = null;
		try {
            st = connection.createStatement();
            return st.execute(sql);

		} catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(st);
        }
	}

	
	// Create tables
	
	
	public void createTabels() {
		dropTables(); 
		createStateAdminUnitTypeTable();
		createStateAdminUnitTable();
		createPossibleSubordinationTable();
		createAdminSubordinationTable();
	}



	private void dropTables() {
		execute("DROP TABLE ADMIN_SUBORDINATION IF EXISTS");
		execute("DROP TABLE POSSIBLE_SUBORDINATION IF EXISTS");
		execute("DROP TABLE STATE_ADMIN_UNIT IF EXISTS");
		execute("DROP TABLE STATE_ADMIN_UNIT_TYPE IF EXISTS");
	}


	private void createStateAdminUnitTypeTable() {
		execute("CREATE TABLE STATE_ADMIN_UNIT_TYPE ( " +
		          "state_admin_unit_type_id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL," +
		          "openedBy                 VARCHAR(32) NOT NULL," +
		          "opened                   DATE NOT NULL," +
		          "changedBy                VARCHAR(32) NOT NULL," +
		          "changed                  DATE NOT NULL," +
		          "closedBy                 VARCHAR(32)," +
		          "closed                   DATE NOT NULL," +
		          "code                     VARCHAR(10) NOT NULL," +
		          "name                     VARCHAR(100) NOT NULL," +
		          "comment                  LONGVARCHAR," +
		          "fromDate                 DATE NOT NULL," +
		          "toDate                   DATE NOT NULL," +
		          "PRIMARY KEY (state_admin_unit_type_id)" +
				")");	
	}
	
	
	
	private void createStateAdminUnitTable() {
		execute("CREATE TABLE STATE_ADMIN_UNIT (" +
		          "state_admin_unit_id      INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL," +
		          "openedBy                 VARCHAR(32) NOT NULL," +
		          "opened                   DATE NOT NULL," +
		          "changedBy                VARCHAR(32) NOT NULL," +
		          "changed                  DATE NOT NULL," +
		          "closedBy                 VARCHAR(32)," +
		          "closed                   DATE NOT NULL," +
		          "code                     VARCHAR(10) NOT NULL," +
		          "name                     VARCHAR(100) NOT NULL," +
		          "comment                  LONGVARCHAR," +
		          "fromDate                 DATE NOT NULL," +
		          "toDate                   DATE NOT NULL," +       
		          "state_admin_unit_type_id INTEGER NOT NULL," +

		          "PRIMARY KEY (state_admin_unit_id), " +
		          "FOREIGN KEY (state_admin_unit_type_id)" +
		          "    REFERENCES STATE_ADMIN_UNIT_TYPE" +
		          "    ON DELETE RESTRICT" +
				")");	

	}


		
	private void createPossibleSubordinationTable(){
		execute("CREATE TABLE POSSIBLE_SUBORDINATION (" +
		          "possible_subordination_id    INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL," +
		          "openedBy                     VARCHAR(32) NOT NULL," +
		          "opened                       DATE NOT NULL," +
		          "changedBy                    VARCHAR(32) NOT NULL," +
		          "changed                      DATE NOT NULL," +
		          "closedBy                     VARCHAR(32)," +
		          "closed                       DATE NOT NULL," +
		          "comment                      LONGVARCHAR," +
		          "state_admin_unit_type_id     INTEGER NOT NULL," +
		          "possible_subordinate_type_id INTEGER NOT NULL," +

		          "PRIMARY KEY (possible_subordination_id)," +
		          "FOREIGN KEY (possible_subordinate_type_id)" +
		          "    REFERENCES STATE_ADMIN_UNIT_TYPE" +
		          "    ON DELETE RESTRICT," + 
		          "FOREIGN KEY (state_admin_unit_type_id)" +
		          "	   REFERENCES STATE_ADMIN_UNIT_TYPE" +
		          "	   ON DELETE RESTRICT" +
		        ")");
	}
	
	
	private void createAdminSubordinationTable() {
		execute("CREATE TABLE ADMIN_SUBORDINATION (" +
		          "admin_subordination_id     INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) NOT NULL," +
		          "openedBy                   VARCHAR(32) NOT NULL," +
		          "opened                     DATE NOT NULL," +
		          "changedBy                  VARCHAR(32) NOT NULL," +
		          "changed                    DATE NOT NULL," +
		          "closedBy                   VARCHAR(32)," +
		          "closed                     DATE NOT NULL," +
		          "comment                    LONGVARCHAR," +
		          "fromDate                   DATE NOT NULL," +
		          "toDate                     DATE NOT NULL," +       
		          "boss_unit_ID      INTEGER NOT NULL," +
		          "subordinate_unit_ID       INTEGER NOT NULL," +
		          "PRIMARY KEY (admin_subordination_id), " +
		          "FOREIGN KEY (subordinate_unit_ID)" +
		          "    REFERENCES STATE_ADMIN_UNIT" +
		          "    ON DELETE RESTRICT, " +
		          "FOREIGN KEY (boss_unit_ID)" +
		          "    REFERENCES STATE_ADMIN_UNIT" +
		          "    ON DELETE RESTRICT" +
		        ")");
	}
	
	
	// Populate tables
	

	public void insertDummyData() {
		insertDummyDataToStateAdminUnitType();
		insertDummyDataToStateAdminUnit();
		insertDummyDataToPossibleSubordination();
		insertDummyDataToAdminSubordination();
	}



	private void insertDummyDataToStateAdminUnitType() {
		executeUpdate("INSERT INTO STATE_ADMIN_UNIT_TYPE " +
						"(OPENEDBY, OPENED, CHANGEDBY, CHANGED, CLOSEDBY, CLOSED, CODE, NAME, COMMENT, FROMDATE, TODATE) " +
						"VALUES " +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', 'KY', 'Kyla', '', '2012-01-01', '2999-12-31')," +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', 'VA', 'Vald', '', '2012-01-01', '2999-12-31')," +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', 'KI', 'Kihelkond', '', '2012-01-01', '2999-12-31')," +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', 'MA', 'Maakond', '', '2012-01-01', '2999-12-31')," +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', 'RI', 'Riik', '', '2012-01-01', '2999-12-31')" 
					);
	}

	
	private void insertDummyDataToStateAdminUnit() {
		executeUpdate("INSERT INTO STATE_ADMIN_UNIT " +
						"(OPENEDBY, OPENED, CHANGEDBY, CHANGED, CLOSEDBY, CLOSED, CODE, NAME, COMMENT, FROMDATE, TODATE, STATE_ADMIN_UNIT_TYPE_ID)" +
						"VALUES " +
						"('admin','2012-12-01' , 'admin', '2012-12-01', '','2999-12-31' , 'KU01', 'Kassi kyla', '', '2012-12-01', '2999-12-31', 1)," +
						"('admin','2012-12-01' , 'admin', '2012-12-01', '','2999-12-31' , 'KU02', 'Toku kyla', '', '2012-12-01', '2999-12-31', 1)," +
		
						"('admin','2012-12-01' , 'admin', '2012-12-01', '','2999-12-31' , 'KU03', 'Londi kyla', '', '2012-12-01', '2999-12-31', 1)," +
						"('admin','2012-12-01' , 'admin', '2012-12-01', '','2999-12-31' , 'KU04', 'Lusti kyla', '', '2012-12-01', '2999-12-31', 1)," +
		
						"('admin','2012-12-01' , 'admin', '2012-12-01', '','2999-12-31' , 'VA01', 'Urvaste vald', '', '2012-12-01', '2999-12-31', 2)," +
						"('admin','2012-12-01' , 'admin', '2012-12-01', '','2999-12-31' , 'VA02', 'Karula vald', '', '2012-12-01', '2999-12-31', 2)," +
		
						"('admin','2012-12-01' , 'admin', '2012-12-01', '','2999-12-31' , 'KI01', 'Urvaste kihelkond', '', '2012-12-01', '2999-12-31', 3)," +
						"('admin','2012-12-01' , 'admin', '2012-12-01', '','2999-12-31' , 'KI02', 'Karula kihelkond', '', '2012-12-01', '2999-12-31', 3)," +
		
						"('admin','2012-12-01' , 'admin', '2012-12-01', '','2999-12-31' , 'MA01', 'Voru maakond', '', '2012-12-01', '2999-12-31', 4)," +
						"('admin','2012-12-01' , 'admin', '2012-12-01', '','2999-12-31' , 'RI01', 'Eesti riik', '', '2012-12-01', '2999-12-31', 5)" 
					);
	}

	


	private void insertDummyDataToPossibleSubordination() {
		executeUpdate("INSERT INTO POSSIBLE_SUBORDINATION " +
						"(OPENEDBY, OPENED, CHANGEDBY, CHANGED, CLOSEDBY, CLOSED, COMMENT, STATE_ADMIN_UNIT_TYPE_ID, POSSIBLE_SUBORDINATE_TYPE_ID )" +
						"VALUES " +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', 5, 4)," +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', 4, 3)," +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', 3, 2)," +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', 2, 1)"
					);
	}

	

	private void insertDummyDataToAdminSubordination() {
		executeUpdate("INSERT INTO ADMIN_SUBORDINATION" +
						"(OPENEDBY, OPENED, CHANGEDBY, CHANGED, CLOSEDBY, CLOSED, COMMENT, FROMDATE, TODATE, BOSS_UNIT_ID, SUBORDINATE_UNIT_ID)" +
						"VALUES " +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', '2012-12-01', '2999-12-31', 5, 1)," +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', '2012-12-01', '2999-12-31', 5, 2)," +
		
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', '2012-12-01', '2999-12-31', 6, 3)," +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', '2012-12-01', '2999-12-31', 6, 4)," +
		
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', '2012-12-01', '2999-12-31', 7, 5)," +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', '2012-12-01', '2999-12-31', 8, 6)," +
		
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', '2012-12-01', '2999-12-31', 9, 7)," +
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', '2012-12-01', '2999-12-31', 9, 8)," +
		
						"('admin', '2012-12-01', 'admin', '2012-12-01', '', '2999-12-31', '', '2012-12-01', '2999-12-31', 10, 9)" 
					);
	}


	
}
