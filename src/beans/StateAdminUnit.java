package beans;

import java.util.Date;
import java.util.List;


public class StateAdminUnit {

	private Integer  				state_admin_unit_id;
	private String 					openedBy;
	private Date   					opened;
	private String 					changedBy;
	private Date   					changed;
	private String 					closedBy;
	private Date   					closed;
	private String 					code;
	private String 					name;
	private String 					comment;
	private Date   					fromDate;
	private Date   					toDate;
	private Integer          		state_admin_unit_type_id;
	private StateAdminUnitType		type;
	private StateAdminUnit			bossUnit;
	private List<StateAdminUnit> 	subordinateUnits;

	
	
	public StateAdminUnit() {
	}


	public Integer getState_admin_unit_id() {
		return state_admin_unit_id;
	}

	public void setState_admin_unit_id(Integer state_admin_unit_id) {
		this.state_admin_unit_id = state_admin_unit_id;
	}


	public String getOpenedBy() {
		return openedBy;
	}

	public void setOpenedBy(String openedBy) {
		this.openedBy = openedBy;
	}


	public Date getOpened() {
		return opened;
	}

	public void setOpened(Date opened) {
		this.opened = opened;
	}


	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}


	public Date getChanged() {
		return changed;
	}

	public void setChanged(Date changed) {
		this.changed = changed;
	}


	public String getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}


	public Date getClosed() {
		return closed;
	}

	public void setClosed(Date closed) {
		this.closed = closed;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	public Integer getState_admin_unit_type_id() {
		return state_admin_unit_type_id;
	}

	public void setState_admin_unit_type_id(Integer state_admin_unit_type_id) {
		this.state_admin_unit_type_id = state_admin_unit_type_id;
	}

	
	public StateAdminUnitType getType() {
		return type;
	}

	public void setType(StateAdminUnitType type) {
		this.type = type;
	}

	


	public StateAdminUnit getBossUnit() {
		return bossUnit;
	}

	public void setBossUnit(StateAdminUnit bossUnit) {
		this.bossUnit = bossUnit;
	}


	public List<StateAdminUnit> getSubordinateUnits() {
		return subordinateUnits;
	}

	public void setSubordinateUnits(List<StateAdminUnit> subordinateUnits) {
		this.subordinateUnits = subordinateUnits;
	}


	@Override 
	public String toString(){
		return	"ID: " + state_admin_unit_id + "   " +
				"Code: " + code + "   " +
				"Name: " + name + "   " + 		
				"Type: " + type.getName();
	}

	
}
