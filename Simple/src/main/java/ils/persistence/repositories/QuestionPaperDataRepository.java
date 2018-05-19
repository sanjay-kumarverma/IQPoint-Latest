package ils.persistence.repositories;

import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import ils.json.ExamExecutionPlain;
import ils.json.ExamPlain;
import ils.json.LevelPlain;
import ils.json.QpSectionPlain;
import ils.json.QpSectionPlainV2;
import ils.json.QuestionPaperPlain;
import ils.json.QuestionPaperPlainV2;
import ils.json.QuestionPaperPlainV3;
import ils.json.QuestionPlain;
import ils.json.QuestionPlainForScore;
import ils.json.QuestionPlainOnlyId;
import ils.json.QuestionPlainV2;
import ils.json.SubjectPlain;
import ils.json.TopicPlain;
import ils.json.UserPlain;
import ils.persistence.domainclasses.*;
import ils.persistence.domainclasses.embeddables.RecordStatus;


//import ils.testspring.testing.SpringTest;

@Service("QuestionPaperDataAccessBean")
@Repository
@Transactional
public class QuestionPaperDataRepository {
	
	
	@PersistenceContext
	private EntityManager em;
     

	
	 
	 @Transactional(readOnly=true) 
	 public List<QuestionPaperPlain> getSearchedQuestionPapers(String userId,String levelId, String subjectId, String paperTypeId, String dateFrom, String dateTo, String freeText) throws Exception
	 {
		 
			//Prepare the search query
			//String searchStr="select q from QuestionPaper q where q.levelId="+levelId+" and q.subjectId="+subjectId+" and q.recordStatus.updatedBy="+userId;
		 
		    String searchStr="select q from QuestionPaper q where q.levelId="+levelId+" and q.subjectId="+subjectId;
			
/*			if (!paperTypeId.equals("")) {
			     searchStr+=" and q.paperTypeId="+paperTypeId;	
			}*/
			
			if (!dateFrom.equals("") && dateTo.equals("")) {
				//get all questions later than or equal to From date
				//java.sql.Timestamp ts = Timestamp.valueOf(dateFrom);
				searchStr+=" and q.recordStatus.updatedOn >= '"+dateFrom+"'"; 
			} else if (dateFrom.equals("") && !dateTo.equals("")) {
				searchStr+=" and q.recordStatus.updatedOn <= '"+dateTo+"'"; 
			} else if (! dateFrom.equals("") && !dateTo.equals("")) {
				searchStr+=" and q.recordStatus.updatedOn >= '"+dateFrom+"' and q.recordStatus.updatedOn <= '"+dateTo+"'"; 
			}
			
			if (!freeText.equals("")) {
				searchStr+=" and upper(q.qpName) like '%"+freeText+"%'";
			}
			
			searchStr+=" and q.recordStatus.isDeleted is false order by q.recordStatus.updatedOn desc";
			
		     TypedQuery<QuestionPaper> qry = em.createQuery(searchStr,QuestionPaper.class);
			 List<QuestionPaper> questionPaperList = qry.getResultList();
			 
			 List<QuestionPaperPlain> qPlainList = new ArrayList<QuestionPaperPlain>();
			 
			 for(QuestionPaper questionpaper:questionPaperList) {
				 QuestionPaperPlain qpp = new QuestionPaperPlain();
				 
				 qpp.setId(questionpaper.getId().toString());
				 qpp.setQpName(questionpaper.getQpName());
				 qpp.setQpPassPercent(questionpaper.getPassPercent().toString());
				 String updateDate = getDateString(questionpaper.getRecordStatus().getUpdatedOn());
				 String updateTime = getTimeString(questionpaper.getRecordStatus().getUpdatedOn());
				 
				 qpp.setUpdateDate(updateDate);
				 qpp.setUpdateTime(updateTime);
				 
				 qPlainList.add(qpp);
			 }
			 
             return qPlainList;	 
	 }	
	 
	 @Transactional(readOnly=true)
	 public QuestionPaperPlainV2 getQuestionPaper(Long paperId) throws Exception
	 {
		QuestionPaperPlainV2 qpp = new QuestionPaperPlainV2();
		
		QuestionPaper qp=em.find(QuestionPaper.class, paperId);
		
		qpp.setId(qp.getId().toString());
		qpp.setQpName(qp.getQpName());
		qpp.setQpPassPercent(qp.getPassPercent().toString());
		
		//setting topics information
		Set<Topic> topics=qp.getTopics();
		for(Topic topic:topics) {
			TopicPlain topicPlain = new TopicPlain();
			topicPlain.setId(topic.getId().toString());
			topicPlain.setTopic(topic.getTopic().toString());
			
			qpp.getQpTopics().add(topicPlain);
		}
		
		//setting sections information
		Set<QPSection> sections=qp.getQpSections();
		for(QPSection section:sections) {
			QpSectionPlainV2 sp = new QpSectionPlainV2();
			
			sp.setId(section.getId().toString());
			sp.setSectionName(section.getSectionName());
			
			QuestionTypeMaster qtm = em.find(QuestionTypeMaster.class,section.getSectionType());
			sp.setSectionType(qtm.getQuestionType());
			sp.setMaxMarks(section.getMaxMarks().toString());
			
			//adding questions to section
			Set<Question> questions = section.getQuestions();
			for(Question ques:questions) {
				QuestionPlainV2 quesplain = new QuestionPlainV2();
				quesplain.setId(ques.getId().toString());
				quesplain.setQuestion(ques.getQuestion());
				quesplain.setMaxMarks(ques.getMaxMarks().toString());
				
				sp.getQuestionsComplete().add(quesplain);
				
			}
			
			qpp.getQpSections().add(sp);
			
		}
		
		return qpp;
	 }
	
	 @Transactional(readOnly=true)
	 public QuestionPaperPlainV3 getBriefQuestionPaper(Long paperId) throws Exception
	 {
		QuestionPaperPlainV3 qpp = new QuestionPaperPlainV3();
		
		QuestionPaper qp=em.find(QuestionPaper.class, paperId);
		
		qpp.setId(qp.getId().toString());
		qpp.setQpName(qp.getQpName());
		qpp.setQpPassPercent(qp.getPassPercent().toString());
		
		//setting topics information
/*		Set<Topic> topics=qp.getTopics();
		for(Topic topic:topics) {
			TopicPlain topicPlain = new TopicPlain();
			topicPlain.setId(topic.getId().toString());
			topicPlain.setTopic(topic.getTopic().toString());
			
			qpp.getQpTopics().add(topicPlain);
		}*/
		qpp.setUpdatedBy(qp.getRecordStatus().getUpdatedBy().toString());
		qpp.setUpdateDate(getDateString(qp.getRecordStatus().getUpdatedOn()));
		qpp.setUpdateTime(getTimeString(qp.getRecordStatus().getUpdatedOn()));
		
		return qpp;
	 }	 
	 
	@Transactional
	//public ExamPlain scheduleExam(String userId,String examName,String examDate,String examDuration,String examType,String cummPercent,String paperId) throws Exception {
	public ExamPlain scheduleExam(String scheduleData) throws Exception {	
		

	    JsonParser parser = new JsonParser();
		JsonElement jsonTree = parser.parse(scheduleData);
		Exam exam = new Exam();
		//if(jsonTree.isJsonPrimitive()){
		    //JsonObject jsonObject = jsonTree.getAsJsonObject();
		    //String qpJsonStr = jsonObject.getAsString();
		    
		    //System.out.println(name);
		    
		    //jsonTree =parser.parse(qpJsonStr);
		    JsonObject scheduleInfo=jsonTree.getAsJsonObject();
		    

			exam.setExam(scheduleInfo.get("examname").getAsString());
			exam.setExamDate(getTimeInMilliSeconds(scheduleInfo.get("examdate").getAsString()));
			exam.setExamDuration(new Long(scheduleInfo.get("examduration").getAsString()));
			exam.setExamType(new Long(scheduleInfo.get("examtype").getAsString()));
			exam.setCummulativePercent(new Float(scheduleInfo.get("cummpercent").getAsString()));
			
			QuestionPaper qp = em.find(QuestionPaper.class,new Long(scheduleInfo.get("paperId").getAsString()));
			exam.setQuestionPaper(qp);
			exam.setRecordStatus(getRecordStatus());
			exam.getRecordStatus().setUpdatedBy(new Long(scheduleInfo.get("userId").getAsString()));
			
			em.persist(exam);	//exam is persisted
			
		    JsonArray studentArray = scheduleInfo.getAsJsonArray("students");
		    for (JsonElement stud:studentArray)
		    {
		    	//JsonObject jsonStud=stud.getAsJsonObject();
                ExamAssignment ea=new ExamAssignment();
                ea.setExam(exam);
                ea.setUserId(new Long(stud.getAsString()));
                ea.setRecordStatus(getRecordStatus());
                ea.getRecordStatus().setUpdatedBy(new Long(scheduleInfo.get("userId").getAsString()));
                em.persist(ea);
                
                //exam.getExamAssignments().add(ea);

		    }
		//}
			
		
		ExamPlain ep = new ExamPlain();
		ep.setId(exam.getId().toString());
		
		return ep;
		
	}
	
	
	@Transactional(readOnly=true)
	public List<UserPlain> getUsersByLevelRoleOwner(String ownerId,String roleId, String levelId) throws Exception
	{
         TypedQuery<User> query = em.createNamedQuery("User.findByCreatorRoleLevel",User.class);
         query.setParameter("ownerid", new Long(ownerId));
         query.setParameter("roleid",new Long(roleId));
         query.setParameter("levelid", new Long(levelId));
         
         List<User> users = query.getResultList();
         
         List<UserPlain> uplist = new ArrayList<UserPlain>();
         
         //preparing list of plain objects
         for(User user:users) {
        	 UserPlain up = new UserPlain();
        	 
        	 up.setId(user.getId().toString());
        	 up.setFirstName(user.getFirstName());
        	 up.setLastName(user.getLastName());
        	 up.setEmail(user.getEmail());
        	 up.setPhone(user.getPhone());
        	 
        	 uplist.add(up);
        	 
         }
         
		return uplist;
		
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
