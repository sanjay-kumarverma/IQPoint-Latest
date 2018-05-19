package ils.json;

public class QuestionPlain {
	
	private String id;
	private String questionType;
	private String question;
	private String optionFirst;
	private String optionSecond;
	private String optionThird;
	private String optionFourth;
	//private String optionType;
	private String answer;
	private String maxMarks;
	private String score;
	private String updateDate;
	private String updateTime;
	private String imageUrl;
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	private String subjectId;
	private String topicId;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOptionFirst() {
		return optionFirst;
	}
	public void setOptionFirst(String optionFirst) {
		this.optionFirst = optionFirst;
	}
	public String getOptionSecond() {
		return optionSecond;
	}
	public void setOptionSecond(String optionSecond) {
		this.optionSecond = optionSecond;
	}
	public String getOptionThird() {
		return optionThird;
	}
	public void setOptionThird(String optionThird) {
		this.optionThird = optionThird;
	}
	public String getOptionFourth() {
		return optionFourth;
	}
	public void setOptionFourth(String optionFourth) {
		this.optionFourth = optionFourth;
	}
/*	public String getOptionType() {
		return optionType;
	}
	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}*/
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	

}
