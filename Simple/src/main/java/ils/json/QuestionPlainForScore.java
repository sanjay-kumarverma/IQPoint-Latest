package ils.json;

public class QuestionPlainForScore {
	
	private String id;
	private String question;
	private String answerGiven;
	private String correctAnswer;
	private String maxMarks;
	private String score;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}

	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswerGiven() {
		return answerGiven;
	}
	public void setAnswerGiven(String answerGiven) {
		this.answerGiven = answerGiven;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	

}
