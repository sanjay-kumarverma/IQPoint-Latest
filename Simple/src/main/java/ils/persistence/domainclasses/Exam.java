package ils.persistence.domainclasses;

import java.util.Set;

import ils.persistence.domainclasses.embeddables.RecordStatus;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="ilsExam")

@NamedQueries({
	   @NamedQuery (name="Exam.findAll", query="select c from Exam c"),
	   @NamedQuery (name="Exam.findBySubjectId", query="select c from Exam c, QuestionPaper p, ExamAssignment e where c.questionPaper.id=p.id and p.subjectId=:subjectid" +
	   		" and e.exam.id = c.id and e.userId =:userid")
	})

public class Exam implements java.io.Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -833987924009956041L;

	@TableGenerator(name="ExamGen",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="ExamIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="ExamGen")
	private Long id;
	
	private String exam; // class, degree name
	
/*	private Long maxScore;
	private Long currentScore;
	private Float passPercentage;*/
	
	private java.sql.Timestamp examDate;
	private Long examDuration;
	private Long examType;
	private Float cummulativePercent;
	
	@Embedded
	private RecordStatus recordStatus;
	
	@OneToMany(mappedBy="exam",cascade=CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<ExamAssignment> examAssignments;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="qpId")
	private QuestionPaper questionPaper;
	
	
	public Set<ExamAssignment> getExamAssignments() {
		return examAssignments;
	}

	public void setExamAssignments(Set<ExamAssignment> examAssignments) {
		this.examAssignments = examAssignments;
	}

	public QuestionPaper getQuestionPaper() {
		return questionPaper;
	}

	public void setQuestionPaper(QuestionPaper questionPaper) {
		this.questionPaper = questionPaper;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExam() {
		return exam;
	}

	public void setExam(String exam) {
		this.exam = exam;
	}

/*	public Long getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Long maxScore) {
		this.maxScore = maxScore;
	}

	public Long getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(Long currentScore) {
		this.currentScore = currentScore;
	}

	public Float getPassPercentage() {
		return passPercentage;
	}

	public void setPassPercentage(Float passPercentage) {
		this.passPercentage = passPercentage;
	}
*/
	public java.sql.Timestamp getExamDate() {
		return examDate;
	}

	public void setExamDate(java.sql.Timestamp examDate) {
		this.examDate = examDate;
	}

	public Long getExamDuration() {
		return examDuration;
	}

	public void setExamDuration(Long examDuration) {
		this.examDuration = examDuration;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Long getExamType() {
		return examType;
	}

	public void setExamType(Long examType) {
		this.examType = examType;
	}

	public Float getCummulativePercent() {
		return cummulativePercent;
	}

	public void setCummulativePercent(Float cummulativePercent) {
		this.cummulativePercent = cummulativePercent;
	}
	
	

}
