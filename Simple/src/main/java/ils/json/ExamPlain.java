package ils.json;

public class ExamPlain {
	private String id;
	private String exam;
	private String examDate;
	private String examDuration;
	private String updatedBy;
	private String updatedOn;
	private String isDeleted;
	private String updateDate;
	private String updateTime;
	
	private QuestionPaperPlain questionPaperPlain = new QuestionPaperPlain();

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExam() {
		return exam;
	}
	public void setExam(String exam) {
		this.exam = exam;
	}
	public String getExamDate() {
		return examDate;
	}
	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}
	public String getExamDuration() {
		return examDuration;
	}
	public void setExamDuration(String examDuration) {
		this.examDuration = examDuration;
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
	public QuestionPaperPlain getQuestionPaperPlain() {
		return questionPaperPlain;
	}
	public void setQuestionPaperPlain(QuestionPaperPlain questionPaperPlain) {
		this.questionPaperPlain = questionPaperPlain;
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
