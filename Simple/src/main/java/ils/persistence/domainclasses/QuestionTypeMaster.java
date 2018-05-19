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
@Table(name="questionTypeMaster")

@NamedQueries({
		@NamedQuery (name="Level.getAllQuestionTypes", query="select q from QuestionTypeMaster q")
})

public class QuestionTypeMaster implements java.io.Serializable, Comparable<QuestionTypeMaster> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7518535160011385747L;

	@TableGenerator(name="QuestionTypeMasterGen",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="QuestionTypeIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="QuestionTypeMasterGen")
	private Long id;
	
	private String questionType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	@Override
	public int compareTo(QuestionTypeMaster qtm) {
		// TODO Auto-generated method stub
		return this.getQuestionType().compareTo(qtm.getQuestionType());
	} 
	

}
