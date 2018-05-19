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
@Table(name="AnswerSheet")

@NamedQueries({
	   //find answer which is not finally save
	   @NamedQuery (name="AnswerSheet.findByUserPaperQuestion", query="select a from AnswerSheet a where a.userId=:userId and a.qpId=:qpId and a.questionId=:questionId and a.examExecution.id is null"),
	   //find all those answers those are not finally saved
	   @NamedQuery (name="AnswerSheet.findByUserPaper", query="select a from AnswerSheet a where a.userId=:userId and a.qpId=:qpId and a.examExecution.id is null"),
	   //find all answers by examExecutionId
	   @NamedQuery (name="AnswerSheet.findByExamExecutionId", query="select a from AnswerSheet a where a.examExecution.id=:examExecutionId")	   
	})
public class AnswerSheet implements java.io.Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -833987924009956041L;

	@TableGenerator(name="AnswerSheetGen",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="AnswerSheetIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="AnswerSheetGen")
	private Long id;
	private Long userId;
	private Long qpId;
	private Long questionId;
	private String answer;
	
	@ManyToOne
	@JoinColumn(name="examExecutionId")
	private ExamExecution examExecution;
	
	

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
	public Long getQpId() {
		return qpId;
	}
	public void setQpId(Long qpId) {
		this.qpId = qpId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public ExamExecution getExamExecution() {
		return examExecution;
	}
	public void setExamExecution(ExamExecution examExecution) {
		this.examExecution = examExecution;
	}
	
	
}
