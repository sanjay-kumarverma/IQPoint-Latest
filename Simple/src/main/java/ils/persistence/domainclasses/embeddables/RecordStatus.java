package ils.persistence.domainclasses.embeddables;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable @Access(AccessType.FIELD)
public class RecordStatus {
     
	private java.sql.Timestamp updatedOn;
	private Long updatedBy;
	private boolean isDeleted;
	
	public java.sql.Timestamp getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(java.sql.Timestamp date) {
		this.updatedOn = date;
	}
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Override
	public String toString() {
		return "RecordStatus [updatedOn=" + updatedOn + ", updatedBy="
				+ updatedBy + ", isDeleted=" + isDeleted + "]";
	}
	
	
}
