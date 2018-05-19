package ils.json;

import java.util.ArrayList;
import java.util.List;

public class QpSectionPlainV2 {
	private String id;
	private String sectionName;
	private String sectionType;
	private String maxMarks;

	private List<QuestionPlainV2> questionsComplete = new ArrayList<QuestionPlainV2>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	public String getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}

	public List<QuestionPlainV2> getQuestionsComplete() {
		return questionsComplete;
	}

	public void setQuestionsComplete(List<QuestionPlainV2> questionsComplete) {
		this.questionsComplete = questionsComplete;
	}




}
