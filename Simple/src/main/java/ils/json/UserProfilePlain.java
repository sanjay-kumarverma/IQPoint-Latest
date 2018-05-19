package ils.json;




public class UserProfilePlain implements java.io.Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
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
	
	
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	
	

	

}
