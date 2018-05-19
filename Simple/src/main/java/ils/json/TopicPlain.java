package ils.json;

import java.util.ArrayList;
import java.util.List;

public class TopicPlain implements Comparable<TopicPlain> {
	private String id;
	private String topic;
	private List<ExamPlain> exams = new ArrayList<ExamPlain>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public List<ExamPlain> getExams() {
		return exams;
	}
	public void setExams(List<ExamPlain> exams) {
		this.exams = exams;
	}
	@Override
	public int compareTo(TopicPlain topic) {
		// TODO Auto-generated method stub
		return this.getTopic().compareTo(topic.getTopic());
	}
	
	

}
