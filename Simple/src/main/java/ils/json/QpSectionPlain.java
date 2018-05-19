package ils.json;

import java.util.ArrayList;
import java.util.List;

public class QpSectionPlain {
	private String id;
	private String sectionName;
	private String sectionType;
	private String sectionOrder;
	private String maxMarks;

	private List<QuestionPlainOnlyId> questions = new ArrayList<QuestionPlainOnlyId>();
	private List<QuestionPlain> questionsComplete = new ArrayList<QuestionPlain>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	public String getSectionOrder() {
		return sectionOrder;
	}

	public void setSectionOrder(String sectionOrder) {
		this.sectionOrder = sectionOrder;
	}

	public List<QuestionPlainOnlyId> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionPlainOnlyId> questions) {
		this.questions = questions;
	}
	

	public List<QuestionPlain> getQuestionsComplete() {
		return questionsComplete;
	}

	public void setQuestionsComplete(List<QuestionPlain> questionsComplete) {
		this.questionsComplete = questionsComplete;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}



}
