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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="ilsSubject")

@NamedQueries({
	@NamedQuery (name="Subject.getAllSubjects", query="select q from Subject q"),
	@NamedQuery (name="Subject.getSubjectById", query="select q from Subject q where q.id=:id"),
	@NamedQuery (name="Subject.getSubjectByLevelId", query="select q from Subject q where q.level.id=:id"),
})

public class Subject implements java.io.Serializable {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -1225605901655392089L;

	@TableGenerator(name="SubjectGen",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="SubjectIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="SubjectGen")
	private Long id;
	
	private String subject; // class, degree name
	
	@ManyToOne
	@JoinColumn(name="LevelId")
    private Level level;
	
	@OneToMany(mappedBy="subject",cascade=CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<Topic> topics;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}


}
