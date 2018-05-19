package ils.persistence.domainclasses;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/*import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;*/

@Entity
@Table(name="ilsTopic")
@NamedQueries({
	@NamedQuery (name="Topic.FindById", query="select q from Topic q where q.id=:Id")
})

public class Topic implements java.io.Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5019513026606720263L;

	@TableGenerator(name="TopicGen",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="TopicIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="TopicGen")
	private Long id;
	
	private String topic; // class, degree name
	
	@ManyToOne
	@JoinColumn(name="SubjectId")
	private Subject subject;
	
/*	@OneToMany(mappedBy="topic",cascade=CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<Exam> exams;*/
	
/*	@ManyToMany(fetch = FetchType.LAZY,mappedBy="topics")
	private Set<QuestionPaper> questionPapers;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

/*	public Set<QuestionPaper> getQuestionPapers() {
		return questionPapers;
	}

	public void setQuestionPapers(Set<QuestionPaper> questionPapers) {
		this.questionPapers = questionPapers;
	}*/

/*	public Set<Exam> getExams() {
		return exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}
*/
	



}
