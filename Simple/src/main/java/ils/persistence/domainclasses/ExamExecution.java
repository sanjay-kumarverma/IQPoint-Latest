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
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="ExamExecution")
@NamedQueries({
	@NamedQuery (name="ExamExecution.findExamIdUserId", query="select e from ExamExecution e where e.examId=:examId and e.userId=:userId and e.recordStatus.isDeleted is false order by e.recordStatus.updatedOn desc"),
	@NamedQuery (name="ExamExecution.findById", query="select e from ExamExecution e where e.id=:execId and e.recordStatus.isDeleted is false order by e.recordStatus.updatedOn desc"),
})

public class ExamExecution implements java.io.Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -833987924009956041L;

	@TableGenerator(name="ExamExecution",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="ExamExecutionIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="ExamExecution")
	private Long id;
	
	private String venue;
	private Long timeTaken;

	@Embedded
	private RecordStatus recordStatus;
	
	@OneToMany(mappedBy="examExecution",cascade=CascadeType.ALL)
	private Set<AnswerSheet> answerSheet;
	
	private Long qpId;
	private Long examId;
	private Long userId;
	private Float score;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public Long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(Long timeTaken) {
		this.timeTaken = timeTaken;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}


	public Long getQpId() {
		return qpId;
	}

	public void setQpId(Long qpId) {
		this.qpId = qpId;
	}

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Set<AnswerSheet> getAnswerSheet() {
		return answerSheet;
	}

	public void setAnswerSheet(Set<AnswerSheet> answerSheet) {
		this.answerSheet = answerSheet;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}


	


}
