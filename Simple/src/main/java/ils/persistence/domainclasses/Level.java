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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



@Entity
@Table(name="ilsLevel")

@NamedQueries({
		@NamedQuery (name="Level.getAllLevels", query="select q from Level q order by q.level,q.levelName"),
		@NamedQuery (name="Level.getLevelById", query="select q from Level q where q.id=:id"),
		@NamedQuery (name="Level.getClassesForLevel", query="select q from Level q where upper(q.level)=:level")
})

public class Level implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7518535160011385747L;

	@TableGenerator(name="LevelGen",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="LevelIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="LevelGen")
	private Long id;
	
	private String level; // Primary, Secondary, Graduate, Post Graduate, Doctorate
	
	private String levelName; //Class, B.Com, M.Com, the certificate or degree names
	
	@OneToMany(mappedBy="level",cascade=CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<Subject> subjects;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}



	
	

}
