package ils.json;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SubjectPlain implements Comparable<SubjectPlain> {
	
	private String id;
	private String subject;

	private List<TopicPlain> topics = new ArrayList<TopicPlain>();
	
	
	public List<TopicPlain> getTopics() {
		return topics;
	}
	public void setTopics(List<TopicPlain> topics) {
		this.topics = topics;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Override
	public int compareTo(SubjectPlain o) {
		// TODO Auto-generated method stub
		return this.getSubject().compareTo(o.getSubject());
	}
	


}
