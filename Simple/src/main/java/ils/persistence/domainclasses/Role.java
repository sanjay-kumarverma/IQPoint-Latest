package ils.persistence.domainclasses;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ilsRole")
@NamedQueries({
	   @NamedQuery (name="Role.getId", query="select r from Role r where r.role=:role")
	})
public class Role {
	@Id
	private Long id;
	private String role;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
