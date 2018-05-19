package ils.persistence.domainclasses;

import ils.persistence.domainclasses.embeddables.RecordStatus;

import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table (name="question")
@NamedQueries({
	@NamedQuery (name="Question.FindById", query="select q from Question q where q.id=:Id")
})

public class Question implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6734456945735673024L;
	
	@TableGenerator(name="Question",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="QuestionIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="Question")
	private Long id;
	
	private Long questionType;
	private String question;
	private String optionFirst;
	private String optionSecond;
	private String optionThird;
	private String optionFourth;
	//private String optionType;
	private String answer;
	private Float maxMarks;
	private Long subjectId;
	private Long topicId;
	private String imageUrl;
	
	@Embedded
	private RecordStatus recordStatus;
	
/*	@ManyToOne
	@JoinColumn(name="SectionId")
	private QPSection section;*/
	
/*	@ManyToMany(mappedBy="questions")
	private Set<QPSection> sections;*/
	
/*	public Set<QPSection> getSections() {
		return sections;
	}
	public void setSections(Set<QPSection> sections) {
		this.sections = sections;
	}*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Long questionType) {
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

	public Float getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(Float maxMarks) {
		this.maxMarks = maxMarks;
	}
/*	public QPSection getSection() {
		return section;
	}
	public void setSection(QPSection section) {
		this.section = section;
	}*/
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public RecordStatus getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	

}
