package ils.json;

import java.util.ArrayList;
import java.util.List;

public class QuestionPaperPlain {
	private String id;
	private String qpName;
	private String qpPassPercent;
	private String updatedBy;
	private String updatedOn;
	private String isDeleted;
	private List<QpSectionPlain> qpSections = new ArrayList<QpSectionPlain>();
	private List<TopicPlain> qpTopics = new ArrayList<TopicPlain>();
	private String examExecutionId;
	
	private String updateDate;
	private String updateTime;
	
	public String getExamExecutionId() {
		return examExecutionId;
	}
	public void setExamExecutionId(String examExecutionId) {
		this.examExecutionId = examExecutionId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQpName() {
		return qpName;
	}
	public void setQpName(String qpName) {
		this.qpName = qpName;
	}
	public String getQpPassPercent() {
		return qpPassPercent;
	}
	public void setQpPassPercent(String qpPassPercent) {
		this.qpPassPercent = qpPassPercent;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public List<QpSectionPlain> getQpSections() {
		return qpSections;
	}
	public void setQpSections(List<QpSectionPlain> qpSections) {
		this.qpSections = qpSections;
	}
	
	public List<TopicPlain> getQpTopics() {
		return qpTopics;
	}
	
	public void setQpTopics(List<TopicPlain> qpTopics) {
		this.qpTopics = qpTopics;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


}
