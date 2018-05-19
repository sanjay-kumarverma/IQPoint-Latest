package ils.persistence.domainclasses;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="QPSection")

public class QPSection implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7639300290036243118L;
	
	@TableGenerator(name="QPSection",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="QPSectionIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="QPSection")
	private Long id;
	private String sectionName;
	private Long sectionType;
	//private Long sectionOrder;
	
	//private Long questionType;
	private Float maxMarks;
	
	@ManyToOne
	@JoinColumn(name="QPId")
	private QuestionPaper questionPaper;
	
	@OneToMany (cascade=CascadeType.ALL)
	@JoinTable(name="SectionQuestion",
	joinColumns=@JoinColumn(name="sectionid"),
	inverseJoinColumns=@JoinColumn(name="questionid"))
	private Set<Question> questions;
	
/*	@OneToMany(mappedBy="section",cascade=CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<Question> questions;*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public QuestionPaper getQuestionPaper() {
		return questionPaper;
	}
	public void setQuestionPaper(QuestionPaper questionPaper) {
		this.questionPaper = questionPaper;
	}
	public Set<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Float getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(Float maxMarks) {
		this.maxMarks = maxMarks;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public Long getSectionType() {
		return sectionType;
	}
	public void setSectionType(Long sectionType) {
		this.sectionType = sectionType;
	}
	

}
