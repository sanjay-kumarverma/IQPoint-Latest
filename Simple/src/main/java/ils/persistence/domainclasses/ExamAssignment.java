package ils.persistence.domainclasses;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="examassignment")

@NamedQueries({
	@NamedQuery (name="ExamAssignment.findByExamId", query="select p from ExamAssignment p where p.exam.id=:examid"),
	@NamedQuery (name="ExamAssignment.findByStudentId", query="select p from ExamAssignment p where p.userId=:studentid")
})

public class ExamAssignment implements java.io.Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -833987924009956041L;

	@TableGenerator(name="ExamAssigmnent",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="ExamAssignmentIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="ExamAssigmnent")
	private Long id;
	
    //private Long examId;
    private Long userId;
	
	@Embedded
	private RecordStatus recordStatus;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ExamExecutionId")
	private ExamExecution examExecution;
	
	@ManyToOne
	@JoinColumn(name="ExamId")
	private Exam exam;
	
	public ExamExecution getExamExecution() {
		return examExecution;
	}

	public void setExamExecution(ExamExecution examExecution) {
		this.examExecution = examExecution;
	}
	
	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}



}
