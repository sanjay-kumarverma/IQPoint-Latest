package ils.persistence.domainclasses;

import ils.persistence.domainclasses.embeddables.Address;


import ils.persistence.domainclasses.embeddables.RecordStatus;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="ilsUser")

@NamedQueries({
   @NamedQuery (name="User.findAll", query="select c from User c"),
   @NamedQuery (name="User.findById", query="select c from User c where c.id= :id"),
   @NamedQuery (name="User.findByEmailId", query="select c from User c where c.email=:email"),
   @NamedQuery (name="User.findByCreatorRoleLevel", query="select c from User c where c.recordStatus.updatedBy= :ownerid and c.role.id=:roleid and c.userLevel.id=:levelid")
})
public class User implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6484927094536897156L;

	@TableGenerator(name="UserGen",
			table="idgen",
			pkColumnName="genname",
			valueColumnName="genval",
			pkColumnValue="UserIds",
			initialValue=10000,
			allocationSize=1)		
	
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="UserGen")
	/*@Id @GeneratedValue(strategy=GenerationType.IDENTITY)*/
	private Long id;
	
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String password;

	

	@ManyToOne
	@JoinColumn(name="RoleId")
	private Role role;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ProfileId")
	private UserProfile userProfile;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="LevelId")
	private Level userLevel;
	
	@Embedded
	private Address address;
	
	@Embedded
	private RecordStatus recordStatus;
	
	private String comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	
	public Level getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Level userLevel) {
		this.userLevel = userLevel;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	

}
