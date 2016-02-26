package tracker;
public class AddDefect {

	private int ticketID;
	private String summary;
	private String description;
	private int assignee;
	private int priority;
	private String dateEntered;
	private int submitter;
	private String status;
	private String comments;
	
	public AddDefect(String b, String d, int p, int s, String st, String c){
		summary = b;
		description = d;
		priority = p;
		submitter = s;
		status = st;
		comments = c;
	}
	
	public int getTicketID() {
		return ticketID;
	}
	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAssignee() {
		return assignee;
	}
	public void setAssignee(int assignee) {
		this.assignee = assignee;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getDateEntered() {
		return dateEntered;
	}
	public void setDateEntered(String dateEntered) {
		this.dateEntered = dateEntered;
	}
	public int getSubmitter() {
		return submitter;
	}
	public void setSubmitter(int submitter) {
		this.submitter = submitter;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "AddDefect [ticketID=" + ticketID + ", summary=" + summary + ", description=" + description
				+ ", assignee=" + assignee + ", priority=" + priority + ", dateEntered=" + dateEntered + ", submitter="
				+ submitter + ", status=" + status + ", comments=" + comments + "]";
	}
	
}	
	
	