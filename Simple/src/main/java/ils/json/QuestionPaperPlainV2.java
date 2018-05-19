package ils.json;

import java.util.ArrayList;
import java.util.List;

public class QuestionPaperPlainV2 {
	private String id;
	private String qpName;
	private String qpPassPercent;

	private List<QpSectionPlainV2> qpSections = new ArrayList<QpSectionPlainV2>();
	private List<TopicPlain> qpTopics = new ArrayList<TopicPlain>();
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
	public List<QpSectionPlainV2> getQpSections() {
		return qpSections;
	}
	public void setQpSections(List<QpSectionPlainV2> qpSections) {
		this.qpSections = qpSections;
	}
	public List<TopicPlain> getQpTopics() {
		return qpTopics;
	}
	public void setQpTopics(List<TopicPlain> qpTopics) {
		this.qpTopics = qpTopics;
	}




}
