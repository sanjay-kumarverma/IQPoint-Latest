package ils.persistence.repositories;

import ils.json.UserPlainV2;
import ils.persistence.domainclasses.Role;
import ils.persistence.domainclasses.User;
import ils.persistence.domainclasses.embeddables.RecordStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//import ils.testspring.testing.SpringTest;

@Service("PeopleDataAccessBean")
@Repository
@Transactional
public class PeopleDataRepository {
	
	
	@PersistenceContext
	private EntityManager em;
     
	 
	 @Transactional(readOnly=true) 
	 public List<UserPlainV2> getSearchedPeople(String level,String userid, String userRole,String role,String dateFrom, String dateTo, String freeText) throws Exception
	 {

		     if (role.equalsIgnoreCase("Student"))
		    	 role="ROLE_STUDENT";
		     else if (role.equalsIgnoreCase("Teacher"))
		    	 role="ROLE_TEACHER";
		     else if (role.equalsIgnoreCase("Organization"))
		    	 role="ROLE_ORGANIZATION";		     
		 
		 
		    String searchStr="";
			//Prepare the search query
		    if (userRole.equalsIgnoreCase("ROLE_SUPERUSER"))
		    	 searchStr="select q from User q where q.role.role = :role";
		    else
			     searchStr="select q from User q where q.recordStatus.updatedBy= :userid and q.role.role = :role";
		    
		    if (!level.equalsIgnoreCase(""))
		    {
		    	searchStr+=" and q.userLevel.id = :levelid";
		    }
			
			if (!dateFrom.equals("") && dateTo.equals("")) {

				searchStr+=" and q.recordStatus.updatedOn >= :dateFrom"; 
			} else if (dateFrom.equals("") && !dateTo.equals("")) {
				searchStr+=" and q.recordStatus.updatedOn <= :dateTo"; 
			} else if (! dateFrom.equals("") && !dateTo.equals("")) {
				searchStr+=" and q.recordStatus.updatedOn >= :dateFrom and q.recordStatus.updatedOn <= :dateTo"; 
			}
			
			if (!freeText.equals("")) {
				searchStr+=" and upper(q.firstName) like :freeText or ";
				searchStr+=" upper(q.lastName) like :freeText or ";
				searchStr+=" upper(q.email) like :freeText or ";
				searchStr+=" upper(q.phone) like :freeText ";
			}
			
			searchStr+=" and q.recordStatus.isDeleted is false order by q.recordStatus.updatedOn";
			
		     TypedQuery<User> qry = em.createQuery(searchStr,User.class);
		     
			     if (!userRole.equalsIgnoreCase("ROLE_SUPERUSER"))
				      qry.setParameter("userid", new Long(userid));
		     
			     qry.setParameter("role", role);
			     
			    if (!level.equalsIgnoreCase(""))
			    {
			    	qry.setParameter("levelid", new Long(level));
			    }			     
			     
				if (!dateFrom.equals("") && dateTo.equals("")) {
					qry.setParameter("dateFrom",convertToDate(dateFrom)); 
				} else if (dateFrom.equals("") && !dateTo.equals("")) {
					qry.setParameter("dateTo",convertToDate(dateTo));
				} else if (! dateFrom.equals("") && !dateTo.equals("")) {
					qry.setParameter("dateFrom",convertToDate(dateFrom)); 
					qry.setParameter("dateTo",convertToDate(dateTo));
				}
			    
				if (!freeText.equals("")) {
					qry.setParameter("freeText","%"+freeText+"%");
				}	     
		     
			//System.out.println("query--->"+convertToDate(dateFrom).toString());
		     
			 List<User> userList = qry.getResultList();
			 
			 List<UserPlainV2> userPlainList = new ArrayList<UserPlainV2>();
			 
			 for(User user:userList) {
				 UserPlainV2 up = new UserPlainV2();
				 
				 up.setId(user.getId().toString());
				 up.setFirstName(user.getFirstName().toString());
				 up.setLastName(user.getLastName().toString());
				 up.setEmail(user.getEmail());
				 up.setPhone(user.getPhone());
				 up.setUpdateDate(getDateString(user.getRecordStatus().getUpdatedOn()));
				 up.setUpdateBy(user.getRecordStatus().getUpdatedBy().toString());

				 
				 userPlainList.add(up);
			 }
			 
             return userPlainList;	 
	 }	
	 
	 @Transactional
	 public UserPlainV2 promoteUser(String userid, String userrole) throws Exception
	 {
		 TypedQuery<User> qry = em.createNamedQuery("User.findById",User.class);
		 qry.setParameter("id", new Long(userid));
		 
		 User user=qry.getSingleResult();
		 
		 //getting role
		 Role role=em.find(Role.class,new Long(userrole));
		 
		 //setting role
		 user.setRole(role);
		 
		 //prepare plain object
		 UserPlainV2 up = new UserPlainV2();
		 up.setId(user.getId().toString());
		 
		 return up;
		 
		 
		 
	 }
	 
    private java.sql.Date convertToDate(String dt) throws Exception
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date date = sdf.parse(dt);
    	java.sql.Date pdate=new java.sql.Date(date.getTime());
    	
    	return pdate;
    	
    }
	 

	private String getTimeString(java.sql.Timestamp ts) {
		SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
		 sdfTime.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
		 String timeStr=sdfTime.format(ts);
		return timeStr;
	}

	private String getDateString(java.sql.Timestamp ts) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yyyy");
		 sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
		 String dateStr=sdfDate.format(ts);
		return dateStr;
	}		 
	 
	  private RecordStatus getRecordStatus() 
	  {
		  RecordStatus rs=new RecordStatus();

		  long timeinmillis = System.currentTimeMillis();
		  java.sql.Timestamp dt = new java.sql.Timestamp(timeinmillis);
		  
		  rs.setUpdatedOn(dt);
		  rs.setUpdatedBy(new Long(10000));
		  rs.setDeleted(false);
		  
		  return rs;
	  }	
	  
	  
	
}
