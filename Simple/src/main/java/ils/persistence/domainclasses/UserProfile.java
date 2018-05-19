package ils.persistence.domainclasses;

import ils.persistence.domainclasses.embeddables.Address;
import ils.persistence.domainclasses.embeddables.RecordStatus;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="ilsUserProfile")

/*@NamedQueries({
   @NamedQuery (name="User.findAll", query="select c from User c"),
   @NamedQuery (name="User.findById", query="select c from User c where c.id= :id"),
   @NamedQuery (name="User.findByEmailId", query="select c from User c where c.email=:email")
})*/
public class UserProfile implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 760653626534065766L;

	@TableGenerator(name="UserProfileGen",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="ProfileIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="UserProfileGen")
	/*@Id @GeneratedValue(strategy=GenerationType.IDENTITY)*/
	private Long id;
	
	private String photoUrl;
	private String bloodGroup;
	private String fatherName;
	private String fatherPhone;
	private String fatherEmail;
	private String motherName;
	private String motherPhone;
	private String motherEmail;
	private String studiesIn;
	private String studiesInSection;
	private String admissionNumber;
	private String employeeNumber;
	private String levelName;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getFatherPhone() {
		return fatherPhone;
	}
	public void setFatherPhone(String fatherPhone) {
		this.fatherPhone = fatherPhone;
	}
	public String getFatherEmail() {
		return fatherEmail;
	}
	public void setFatherEmail(String fatherEmail) {
		this.fatherEmail = fatherEmail;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getMotherPhone() {
		return motherPhone;
	}
	public void setMotherPhone(String motherPhone) {
		this.motherPhone = motherPhone;
	}
	public String getMotherEmail() {
		return motherEmail;
	}
	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}
	public String getStudiesIn() {
		return studiesIn;
	}
	public void setStudiesIn(String studiesIn) {
		this.studiesIn = studiesIn;
	}
	public String getStudiesInSection() {
		return studiesInSection;
	}
	public void setStudiesInSection(String studiesInSection) {
		this.studiesInSection = studiesInSection;
	}
	public String getAdmissionNumber() {
		return admissionNumber;
	}
	public void setAdmissionNumber(String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	

}
