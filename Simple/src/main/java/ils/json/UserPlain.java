package ils.json;

import ils.persistence.domainclasses.embeddables.RecordStatus;

public class UserPlain implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6484927094536897156L;

	private String id;
	
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private ExamExecutionPlain eep;
	private RolePlain role;
	private UserProfilePlain userProfile;
	private LevelPlain userLevel;
	private AddressPlain address;
	private String updateDate;
	private String updateTime;
	private String updateBy;
	private String comments;
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public ExamExecutionPlain getEep() {
		return eep;
	}
	public void setEep(ExamExecutionPlain eep) {
		this.eep = eep;
	}
	public RolePlain getRole() {
		return role;
	}
	public void setRole(RolePlain role) {
		this.role = role;
	}
	public UserProfilePlain getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfilePlain userProfile) {
		this.userProfile = userProfile;
	}
	public LevelPlain getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(LevelPlain userLevel) {
		this.userLevel = userLevel;
	}
	public AddressPlain getAddress() {
		return address;
	}
	public void setAddress(AddressPlain address) {
		this.address = address;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	
	
	

}
