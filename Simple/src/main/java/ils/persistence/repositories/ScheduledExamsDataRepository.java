package ils.persistence.repositories;

import ils.json.ExamExecutionPlain;
import ils.json.ExamPlainV2;
import ils.json.UserPlain;
import ils.persistence.domainclasses.Exam;
import ils.persistence.domainclasses.ExamAssignment;
import ils.persistence.domainclasses.ExamExecution;
import ils.persistence.domainclasses.User;
import ils.persistence.domainclasses.embeddables.RecordStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//import ils.testspring.testing.SpringTest;

@Service("ScheduledExamsDataAccessBean")
@Repository
@Transactional
public class ScheduledExamsDataRepository {
	
	
	@PersistenceContext
	private EntityManager em;
     

	
	 
	 @Transactional(readOnly=true) 
	 public List<ExamPlainV2> getSearchedExamsList(String userId,String levelId, String subjectId, String paperTypeId, String dateFrom, String dateTo, String freeText) throws Exception
	 {
		 
			//Prepare the search query
			String searchStr="select e from Exam e, QuestionPaper q where e.questionPaper.id = q.id and q.levelId="+levelId+" and q.subjectId="+subjectId+" and e.recordStatus.updatedBy="+userId;
			
			if (!dateFrom.equals("") && dateTo.equals("")) {
				searchStr+=" and e.recordStatus.updatedOn >= '"+dateFrom+"'"; 
			} else if (dateFrom.equals("") && !dateTo.equals("")) {
				searchStr+=" and e.recordStatus.updatedOn <= '"+dateTo+"'"; 
			} else if (! dateFrom.equals("") && !dateTo.equals("")) {
				searchStr+=" and e.recordStatus.updatedOn >= '"+dateFrom+"' and q.recordStatus.updatedOn <= '"+dateTo+"'"; 
			}
			
			if (!freeText.equals("")) {
				searchStr+=" and upper(e.exam) like '%"+freeText+"%'";
			}
			
			searchStr+=" and e.recordStatus.updatedBy ="+userId+" and e.recordStatus.isDeleted is false order by e.recordStatus.updatedOn desc";
			
		     TypedQuery<Exam> qry = em.createQuery(searchStr,Exam.class);
			 List<Exam> examList = qry.getResultList();
			 
			 List<ExamPlainV2> ePlainList = new ArrayList<ExamPlainV2>();
			 
			 for(Exam exam:examList) {
				 ExamPlainV2 ep = new ExamPlainV2();
				 
				 ep.setId(exam.getId().toString());
				 ep.setExam(exam.getExam());
				 ep.setExamDuration(exam.getExamDuration().toString());
				 String updateDate = getDateString(exam.getRecordStatus().getUpdatedOn());
				 String updateTime = getTimeString(exam.getRecordStatus().getUpdatedOn());
				 String examDate = getDateString(exam.getExamDate());
				 ep.setUpdateDate(updateDate);
				 ep.setUpdateTime(updateTime);
				 ep.setExamDate(examDate);
				 ep.setQpId(exam.getQuestionPaper().getId().toString());
				 
				 ePlainList.add(ep);
			 }
			 
             return ePlainList;	 
	 }	
	 
	@Transactional(readOnly=true)
	public List<UserPlain> getScheduledStudentsForExam(Long examId) throws Exception
	{
		List<UserPlain> studentsList = new ArrayList<UserPlain>();
		
		TypedQuery<ExamAssignment> query = em.createNamedQuery("ExamAssignment.findByExamId",ExamAssignment.class);
		query.setParameter("examid", examId);
		
		List<ExamAssignment> ealist = query.getResultList();
		
		for(ExamAssignment ea: ealist ) {
			
			//get student
			TypedQuery<User> userqry = em.createNamedQuery("User.findById",User.class);
			userqry.setParameter("id", ea.getUserId());
			
			User usr = userqry.getSingleResult();
			
			//creating a UserPlain object
			UserPlain up = new UserPlain();
			
			up.setId(usr.getId().toString());
			up.setFirstName(usr.getFirstName());
			up.setLastName(usr.getLastName());
			up.setEmail(usr.getEmail());
			up.setPhone(usr.getPhone());
			
			ExamExecutionPlain eep = getLatestAttemptByExamIdUserId(examId,usr.getId());
			
			up.setEep(eep);
			
			studentsList.add(up);
			
		}
		
		return studentsList;
		
		
	}
	
	
	 // this method returns the latest exam-execution object for a user, in case it exists
	 @Transactional(readOnly=true)
	 public ExamExecutionPlain getLatestAttemptByExamIdUserId(Long examId, Long userId) throws javax.persistence.NoResultException,Exception
	 {
		     TypedQuery<ExamExecution> qry = em.createNamedQuery("ExamExecution.findExamIdUserId", ExamExecution.class);
		     qry.setParameter("examId", examId);
		     qry.setParameter("userId", userId);
		     
			 List<ExamExecution> examExecutions = qry.getResultList();
			 
			 //preparing plain examExcution object array
			 //List<ExamExecutionPlain> eePlainList=new ArrayList<ExamExecutionPlain>();
			 ExamExecutionPlain eepObj = new ExamExecutionPlain();
			 eepObj.setId(new Long(-1).toString());
			    
			 if (examExecutions.size()>0) {
			 	 ExamExecution ee = examExecutions.get(0);
			 
				 
				 
				 eepObj.setId(ee.getId().toString());
				 eepObj.setExamId(ee.getExamId().toString());
				 eepObj.setQpId(ee.getQpId().toString());
				 eepObj.setTimeTaken(ee.getTimeTaken().toString());
				 
				 if (ee.getScore()==null)
				     eepObj.setScore(new Float("0").toString());
				 else
					 eepObj.setScore(ee.getScore().toString()); 
				 //separating date and time

				 String attemptDate = getDateString(ee.getRecordStatus().getUpdatedOn());
				 String attemptTime = getTimeString(ee.getRecordStatus().getUpdatedOn());				 
				 eepObj.setAttemptDate(attemptDate);
				 eepObj.setAttemptTime(attemptTime);
				 
				 eepObj.setVenue(ee.getVenue());
				 
				 Long duration=ee.getTimeTaken();
			     Long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			     Long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			     Long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
				 eepObj.setHours(diffInHours.toString());
				 eepObj.setMinutes(diffInMinutes.toString());
				 eepObj.setSeconds(diffInSeconds.toString());
			 }

			 
			 return eepObj;		 
	 }	
	
	
	
	
	@Transactional
	public boolean unassignStudentForExam(Long studentid) throws Exception
	{

		boolean unassigned=false;
		TypedQuery<ExamAssignment> query = em.createNamedQuery("ExamAssignment.findByStudentId",ExamAssignment.class);
		query.setParameter("studentid", studentid);
		
		ExamAssignment ea = query.getSingleResult();
		
		if (ea.getExamExecution() == null ) //if student has not already attempted the paper
		{
			em.remove(ea);
			unassigned=true;
		}
		
		return unassigned;
	}
	
	
	private java.sql.Timestamp getTimeInMilliSeconds(String datestr) throws Exception {
		
		java.sql.Timestamp ts = java.sql.Timestamp.valueOf(datestr+" 00:00:00");
		return ts;
		
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
