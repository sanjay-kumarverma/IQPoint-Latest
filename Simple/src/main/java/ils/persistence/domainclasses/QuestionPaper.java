package ils.persistence.domainclasses;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;

import ils.persistence.domainclasses.embeddables.RecordStatus;

@Entity
@Table(name="QuestionPaper")
   @NamedQueries({
	@NamedQuery (name="Exam.findByQuestionPaperId", query="select p from QuestionPaper p, QPSection s where p.id=s.questionPaper.id and s.questionPaper.id=:qpId")
   })

public class QuestionPaper implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5283570250722653675L;
	
	@TableGenerator(name="QuestionPaper",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="QPIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="QuestionPaper")
	private Long id;
	private Long levelId;
	private String qpName;
	private Long subjectId;
	
	private Float passPercent;
	
	@Embedded
	private RecordStatus recordStatus;
	
	@OneToMany(mappedBy="questionPaper",cascade=CascadeType.ALL)
	//@Fetch(value = FetchMode.SUBSELECT)
	private Set<QPSection> qpSections;
	

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="QPTopic",
	joinColumns=@JoinColumn(name="qpid"),
	inverseJoinColumns=@JoinColumn(name="topicid"))
	private Set<Topic> topics;
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQpName() {
		return qpName;
	}

	public void setQpName(String qpName) {
		this.qpName = qpName;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}


	public Float getPassPercent() {
		return passPercent;
	}

	public void setPassPercent(Float passPercent) {
		this.passPercent = passPercent;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Set<QPSection> getQpSections() {
		return qpSections;
	}

	public void setQpSections(Set<QPSection> qpSections) {
		this.qpSections = qpSections;
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}
	
	
}
