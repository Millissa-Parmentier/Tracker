package tracker;

import java.time.LocalDateTime;

public class Change {

	protected static String[] fields = {"TICKET_ID", "CHANGE_DATE", "FIELD_NAME", "OLD_VALUE"};
	private int ticketID;
	private LocalDateTime changeDate;
	private String fieldName;
	private String oldValue;
	
	public Change(int t, LocalDateTime d, String f, String v){
		ticketID = t;
		changeDate = d;
		fieldName = f;
		oldValue = v;
	}
	
	public int getTicketID() {
		return ticketID;
	}

	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}

	public LocalDateTime getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(LocalDateTime changeDate) {
		this.changeDate = changeDate;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	@Override
	public String toString() {
		return "AddChange [ticketID=" + ticketID + ", changeDate=" + changeDate + ", fieldName=" + fieldName
				+ ", oldValue=" + oldValue + "]";
	}
	
	
}	
	