package ils.persistence.repositories;

import ils.json.SubjectPlain;
import ils.json.TopicPlain;
import ils.persistence.domainclasses.Level;
import ils.persistence.domainclasses.Role;
import ils.persistence.domainclasses.Subject;
import ils.persistence.domainclasses.Topic;
import ils.persistence.domainclasses.User;
import ils.persistence.domainclasses.UserProfile;
import ils.persistence.domainclasses.embeddables.Address;
import ils.persistence.domainclasses.embeddables.RecordStatus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//import ils.testspring.testing.SpringTest;

@Service("UserDataAccessBean")
@Repository
@Transactional
public class UserDataRepository {
	
	
	@PersistenceContext
	private EntityManager em;
     
	 @Transactional(readOnly=true)
	 public List<User> listAllUsers() throws Exception
	 {
		 
			 List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
			 return users;		 
	 }
	 
	@Transactional(readOnly=true)
	public User findUserById(Long id) throws Exception
	{
		TypedQuery<User> qry=em.createNamedQuery("User.findById",User.class);
		qry.setParameter("id", id);
		User user=(User)qry.getSingleResult();
		
		return user;
	}
	
	private final String UPLOADED_FILE_PATH = "D:\\workspace\\DevelopmentWS\\Simple\\src\\main\\webapp\\files\\";
	
	@Transactional
	public void updateUserPhotoUrl(Long id,String url) throws Exception
	{
		User user=(User)findUserById(id);
		//first get the existing photourl and remove the file currently existing file, cleaning activity
		String existingFile = user.getUserProfile().getPhotoUrl();
		if (existingFile!=null) {
		   File file=new File(UPLOADED_FILE_PATH+existingFile);
		   file.delete();
		}
		
		
		user.getUserProfile().setPhotoUrl(url);
	}
	
	
	@Transactional(readOnly=true)
	public List<SubjectPlain> getUserSubjects(Long levelid) throws Exception
	{
		TypedQuery<Subject> qry=em.createNamedQuery("Subject.getSubjectByLevelId",Subject.class);
		qry.setParameter("id", levelid);
		List<Subject> subjects=qry.getResultList();
		
		//preparing plain subject array
		List<SubjectPlain> sp = new ArrayList<SubjectPlain>();
		for(Subject sub:subjects) {
			SubjectPlain spobj=new SubjectPlain();
			spobj.setId(sub.getId().toString());
			spobj.setSubject(sub.getSubject());
			
			sp.add(spobj);
		}
			
		
		return sp;
	}
	
	@Transactional(readOnly=true)
	public User findUserByEmailId(String emailId) throws Exception
	{
		TypedQuery<User> qry=em.createNamedQuery("User.findByEmailId",User.class);
		qry.setParameter("email", emailId);
		User user=(User)qry.getSingleResult();
	
		return user;
	}
	
	
	@Transactional(readOnly=true)
	public Level findLevelById(String levelId) throws Exception
	{
			Level level=em.find(Level.class, new Long(levelId));
	
		return level;
	}	
	
	
	@Transactional
	public User updateUser(User userobj) throws Exception
	{
/*		TypedQuery<User> qry=em.createNamedQuery("User.findByEmailId",User.class);
		qry.setParameter("email", emailId);
		User user=(User)qry.getSingleResult();*/
		User user=em.find(User.class, userobj.getId());
		
		//user.setUserProfile(userobj.getUserProfile());
		em.merge(userobj);
		em.flush();
	
		return userobj;
	}	
	
	 // following line is to show how transaction propagation can be done
    //@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Transactional
	public User createUser(User userobj) throws Exception
	{
	      try {
	    	  
		      em.persist(userobj);
		      
	    	  UserProfile up =new UserProfile();
		      em.persist(up);		      
		      userobj.setUserProfile(up);
		      
		      TypedQuery<Level> qry = em.createNamedQuery("Level.getLevelById",Level.class);
		      qry.setParameter("id", new Long(1));
		      Level ll = qry.getSingleResult();
		      
		      userobj.setUserLevel(ll);
		      
	      }
	      catch(Exception e)
	      {
	    	 throw new Exception(e.getMessage());
	      }
            return userobj;
	}
	
	@Transactional
	public User createUser(User userobj,String roleid) throws Exception
	{
	      try {
	    	  Role role=em.find(Role.class, new Long(roleid));
	    	  userobj.setRole(role);
	    	  
		      em.persist(userobj);
		      
	    	  UserProfile up =new UserProfile();
		      em.persist(up);		      
		      userobj.setUserProfile(up);
		      
			  Address add=new Address();
			  add.setStreet("Not available");
			  add.setCity("Not available");
			  add.setState("Not available");
			  add.setCountry("Not available");
			  add.setZip("Not available");
			  
			  userobj.setAddress(add);
		      
		      TypedQuery<Level> qry = em.createNamedQuery("Level.getLevelById",Level.class);
		      qry.setParameter("id", new Long(1));
		      Level ll = qry.getSingleResult();
		      
		      userobj.setUserLevel(ll);
		      
		      userobj.setRecordStatus(userobj.getRecordStatus());
		      
	      }
	      catch(Exception e)
	      {
	    	 throw new Exception(e.getMessage());
	      }
            return userobj;
	}	
	
	
	
	@Transactional
	public UserProfile createUserProfile(UserProfile up) throws Exception
	{
	      try {
		      em.persist(up);
		      
		      
	      }
	      catch(Exception e)
	      {
	    	 throw new Exception(e.getMessage());
	      }
            return up;	
	} 
	
/*            		@Transactional
            		public User createUser(User userobj) throws Exception
            		{
            		      try {
            			      em.persist(userobj);
            		      }
            		      catch(Exception e)
            		      {
            		    	 throw new Exception(e.getMessage());
            		      }
            	            return userobj;
            		}
            		
            		
           		@Transactional
            		public User createUser(User userobj) throws Exception
            		{
            		      try {
            			      em.persist(userobj);
            		      }
            		      catch(Exception e)
            		      {
            		    	 throw new Exception(e.getMessage());
            		      }
            	            return userobj;
            		}		@Transactional
            		public User createUser(User userobj) throws Exception
            		{
            		      try {
            			      em.persist(userobj);
            		      }
            		      catch(Exception e)
            		      {
            		    	 throw new Exception(e.getMessage());
            		      }
            	            return userobj;
            		}		@Transactional
            		public User createUser(User userobj) throws Exception
            		{
            		      try {
            			      em.persist(userobj);
            		      }
            		      catch(Exception e)
            		      {
            		    	 throw new Exception(e.getMessage());
            		      }
            	            return userobj;
            		}		@Transactional
            		public User createUser(User userobj) throws Exception
            		{
            		      try {
            			      em.persist(userobj);
            		      }
            		      catch(Exception e)
            		      {
            		    	 throw new Exception(e.getMessage());
            		      }
            	            return userobj;
            		}	;
	}		*/
	
	@Transactional(readOnly=true)
	public Long getRoleId(String rolestr) throws Exception
	{
		TypedQuery<Role> qry=em.createNamedQuery("Role.getId",Role.class);
		qry.setParameter("role", rolestr);
		
		Role role=(Role)qry.getSingleResult();
		
		return role.getId();
	}
	
	@Transactional(readOnly=true)
	public List<SubjectPlain> getSubjects(Long userid)  throws Exception
	{
		
        User user=findUserById(userid);
        Level level=user.getUserLevel();
        Set<Subject> subjects = level.getSubjects();
        
		Iterator<Subject> ite=subjects.iterator();
		List<SubjectPlain> subjectList = new ArrayList<SubjectPlain>(); 
		
		while(ite.hasNext())
		{
			Subject sub=(Subject)ite.next();
			
			SubjectPlain spobj = new SubjectPlain();
			spobj.setId(sub.getId().toString());
			spobj.setSubject(sub.getSubject());
			
			//preparing the topic object
			Iterator<Topic> itetopic=sub.getTopics().iterator();
			while(itetopic.hasNext())
			{
				Topic topic = (Topic)itetopic.next();
				
				TopicPlain topicPlain=new TopicPlain();
				topicPlain.setId(topic.getId().toString());
				topicPlain.setTopic(topic.getTopic());
				
				//adding exams to topic
/*				Iterator<Exam> iteexam=topic.getExams().iterator();
				while(iteexam.hasNext())
				{
					Exam exam=(Exam)iteexam.next();
					
					ExamPlain examPlain = new ExamPlain();
					examPlain.setId(exam.getId().toString());
					examPlain.setExam(exam.getExam());
					examPlain.setMaxScore(exam.getMaxScore().toString());
					examPlain.setCurrentScore(exam.getCurrentScore().toString());
					examPlain.setPassPercent(exam.getPassPercentage().toString());
					
					topicPlain.getExams().add(examPlain);
					
				}*/
				
				spobj.getTopics().add(topicPlain);
				
			}

			subjectList.add(spobj);
		}        
        		
		return subjectList;
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
