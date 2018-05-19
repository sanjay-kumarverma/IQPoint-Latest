package ils.persistence.domainclasses;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table (name="qptopic")

/*@NamedQueries({
	@NamedQuery (name="QPTopic.FindById", query="select q from QPTopic q where q.id=:Id")
})*/
public class QPTopic implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5061726202240535930L;
	
	@TableGenerator(name="QPTopics",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="QPTopicIds",
			initialValue=10000,
			allocationSize=1)	
	
	//@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="QPTopics")
	private Long id;
	private Long topicId;
	private Long qpId;
	
/*	@ManyToOne
	@JoinColumn(name="QPId")
	private QuestionPaper questionPaper;*/
	

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
/*	public QuestionPaper getQuestionPaper() {
		return questionPaper;
	}
	public void setQuestionPaper(QuestionPaper questionPaper) {
		this.questionPaper = questionPaper;
	}*/
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public Long getQpId() {
		return qpId;
	}
	public void setQpId(Long qpId) {
		this.qpId = qpId;
	}
	
	

}
