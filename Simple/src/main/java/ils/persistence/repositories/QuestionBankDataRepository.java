package ils.persistence.repositories;

import ils.json.LevelPlain;
import ils.json.QuestionPlain;
import ils.json.SubjectPlain;
import ils.json.TopicPlain;
import ils.persistence.domainclasses.Level;
import ils.persistence.domainclasses.QPSection;
import ils.persistence.domainclasses.Question;
import ils.persistence.domainclasses.QuestionPaper;
import ils.persistence.domainclasses.QuestionTypeMaster;
import ils.persistence.domainclasses.Subject;
import ils.persistence.domainclasses.Topic;
import ils.persistence.domainclasses.embeddables.RecordStatus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;


//import ils.testspring.testing.SpringTest;

@Service("QuestionBankDataAccessBean")
@Repository
@Transactional
public class QuestionBankDataRepository {
	
	
	@PersistenceContext
	private EntityManager em;
     
	 @Transactional(readOnly=true)
	 public List<LevelPlain> getLevels() throws Exception
	 {
		     TypedQuery<Level> qry = em.createNamedQuery("Level.getAllLevels", Level.class);
     
			 List<Level> levels = qry.getResultList();
			 
             List<LevelPlain> levelPlainList= new ArrayList<LevelPlain>();
             
             for(Level l:levels)
             {
    			 //populating plain object for json
    			 LevelPlain lp = new LevelPlain();
    			 lp.setId(l.getId().toString());
    			 lp.setLevel(l.getLevel());
    			 lp.setLevelName(l.getLevelName());
    			 
    			 levelPlainList.add(lp);  
    			 
             }
			 
			 return levelPlainList;		 
	 }
	 
	 @Transactional(readOnly=true)
	 public List<LevelPlain> getLevelClasses(String level) throws Exception
	 {
		     TypedQuery<Level> qry = em.createNamedQuery("Level.getClassesForLevel", Level.class);
		     qry.setParameter("level", level);
     
			 List<Level> levels = qry.getResultList();
			 
             List<LevelPlain> levelPlainList= new ArrayList<LevelPlain>();
             
             for(Level l:levels)
             {
    			 //populating plain object for json
    			 LevelPlain lp = new LevelPlain();
    			 lp.setId(l.getId().toString());
    			 lp.setLevel(l.getLevel());
    			 lp.setLevelName(l.getLevelName());
    			 
    			 levelPlainList.add(lp);  
    			 
             }
			 
			 return levelPlainList;		 
	 }	 
	 
	 @Transactional(readOnly=true)
	 public List<SubjectPlain> getSubjectsForLevel(Long levelid) throws Exception
	 {
		     TypedQuery<Level> qry = em.createNamedQuery("Level.getLevelById", Level.class);
		     qry.setParameter("id", levelid);
     
			 Level level = qry.getSingleResult();
			 
			 Set<Subject> subjects=level.getSubjects();
			 
             List<SubjectPlain> subjectPlainList= new ArrayList<SubjectPlain>();
			
             for(Subject s:subjects)
             {
    			 //populating plain object for json
    			 SubjectPlain sp = new SubjectPlain();
    			 sp.setId(s.getId().toString());
    			 sp.setSubject(s.getSubject());
   			 
    			 subjectPlainList.add(sp);  
    			 
             }
			 Collections.sort(subjectPlainList);
			 return subjectPlainList;		 
	 }	 
	 
	 @Transactional(readOnly=true)
	 public List<TopicPlain> getTopicsForSubject(Long subjectid) throws Exception
	 {
		     TypedQuery<Subject> qry = em.createNamedQuery("Subject.getSubjectById", Subject.class);
		     qry.setParameter("id", subjectid);
     
			 Subject subject = qry.getSingleResult();
			 
			 Set<Topic> topics=subject.getTopics();
			 
             List<TopicPlain> topicPlainList= new ArrayList<TopicPlain>();
             
             for(Topic s:topics)
             {
    			 //populating plain object for json
    			 TopicPlain sp = new TopicPlain();
    			 sp.setId(s.getId().toString());
    			 sp.setTopic(s.getTopic());
   			 
    			 topicPlainList.add(sp);  
    			 
             }
			 Collections.sort(topicPlainList);
			 return topicPlainList;		 
	 }	
	 
	 @Transactional(readOnly=true) 
	 public List<QuestionTypeMaster> getQuestionTypes() throws Exception
	 {
		     TypedQuery<QuestionTypeMaster> qry = em.createNamedQuery("Level.getAllQuestionTypes", QuestionTypeMaster.class);
			 List<QuestionTypeMaster> qtplist = qry.getResultList();
			 Collections.sort(qtplist);
             return qtplist;	 
	 }		 
	
	 
	 @Transactional(readOnly=true) 
	 public List<QuestionPlain> getSearchedQuestions(String levelId, String subjectId, String topicId, String questionTypeId, String dateFrom, String dateTo, String freeText) throws Exception
	 {
		 
			//Prepare the search query
			String searchStr="select q from Question q, Subject s where q.subjectId=s.id and s.id="+subjectId+" and s.level.id="+levelId+" and q.topicId="+topicId;
			
			if (!questionTypeId.equals("")) {
			     searchStr+=" and q.questionType="+questionTypeId;	
			}
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
				searchStr+=" and ( upper(q.question) like '%"+freeText+"%' or ";
				searchStr+=" upper(q.optionFirst) like '%"+freeText+"%' or ";
				searchStr+=" upper(q.optionSecond) like '%"+freeText+"%' or ";
				searchStr+=" upper(q.optionThird) like '%"+freeText+"%' or ";
				searchStr+=" upper(q.optionFourth) like '%"+freeText+"%' or ";
				searchStr+=" upper(q.answer) like '%"+freeText+"%' ) ";
			}
			
			searchStr+=" and q.recordStatus.isDeleted is false order by q.recordStatus.updatedOn";
			
		     TypedQuery<Question> qry = em.createQuery(searchStr,Question.class);
			 List<Question> questionList = qry.getResultList();
			 
			 List<QuestionPlain> qPlainList = new ArrayList<QuestionPlain>();
			 
			 for(Question question:questionList) {
				 QuestionPlain qpp = new QuestionPlain();
				 
				 qpp.setId(question.getId().toString());
				 qpp.setQuestionType(question.getQuestionType().toString());
				 //qpp.setOptionType(question.getOptionType().toString());
				 qpp.setQuestion(question.getQuestion());
				 qpp.setOptionFirst(question.getOptionFirst());
				 qpp.setOptionSecond(question.getOptionSecond());
				 qpp.setOptionThird(question.getOptionThird());
				 qpp.setOptionFourth(question.getOptionFourth());
				 qpp.setAnswer(question.getAnswer());
				 qpp.setMaxMarks(question.getMaxMarks().toString());
                 qpp.setImageUrl(question.getImageUrl());
				 String updateDate = getDateString(question.getRecordStatus().getUpdatedOn());
				 String updateTime = getTimeString(question.getRecordStatus().getUpdatedOn());
				 
				 qpp.setUpdateDate(updateDate);
				 qpp.setUpdateTime(updateTime);
				 
				 qPlainList.add(qpp);
			 }
			 
             return qPlainList;	 
	 }	
	 
	 private final String UPLOADED_QUESTION_FILE_PATH = "C:\\Users\\sanjay.verma\\git\\IQPoint\\Simple\\src\\main\\webapp\\files\\questions\\";
	 
	 @Transactional
	 public QuestionPlain saveQuestion(String qId,String question,String optionFirst,String optionSecond,String optionThird,String optionFourth,String answer,String maxMarks,String imageUrl) throws Exception
	 {
		 
		     Question ques=em.find(Question.class,new Long(qId));
		     //ques.getSection();
		     
		     ques.setQuestion(question);
		     ques.setOptionFirst(optionFirst);
		     ques.setOptionSecond(optionSecond);
		     ques.setOptionThird(optionThird);
		     ques.setOptionFourth(optionFourth);
		     ques.setAnswer(answer);
		     ques.setMaxMarks(new Float(maxMarks));
		     
		     //before setting new image url, first check if the image file has changed
		     //if it has changed then remove the first one
		     
				//first get the existing photourl and remove the file currently existing file, cleaning activity
				String existingFile = ques.getImageUrl();
				if (existingFile!=null && !existingFile.equalsIgnoreCase(imageUrl)) {
				   File file=new File(UPLOADED_QUESTION_FILE_PATH+existingFile);
				   file.delete();
				}	     
		     
		     ques.setImageUrl(imageUrl);
		     
		     //preparing and sending plain question object
		     QuestionPlain qpp = new QuestionPlain();
		     
			 qpp.setId(ques.getId().toString());
			 qpp.setQuestionType(ques.getQuestionType().toString());
			 //qpp.setOptionType(ques.getOptionType().toString());
			 qpp.setQuestion(ques.getQuestion());
			 qpp.setOptionFirst(ques.getOptionFirst());
			 qpp.setOptionSecond(ques.getOptionSecond());
			 qpp.setOptionThird(ques.getOptionThird());
			 qpp.setOptionFourth(ques.getOptionFourth());
			 qpp.setAnswer(ques.getAnswer());
			 qpp.setMaxMarks(ques.getMaxMarks().toString());
			 qpp.setImageUrl(imageUrl);
		     

			 
             return qpp;	 
	 }	
	 
	 
	 @Transactional 
	 public LevelPlain createLevel(String qualification, String course) throws Exception
	 {
             Level level = new Level();
             level.setLevel(qualification);
             level.setLevelName(course);
             
             em.persist(level);
             
             //creating and populating plain object
             LevelPlain lp = new LevelPlain();
             lp.setId(level.getId().toString());
             lp.setLevel(level.getLevel());
             lp.setLevelName(level.getLevelName());
			 
             return lp;
	 }	
	 
	 @Transactional 
	 public SubjectPlain createSubject(String levelid, String subject) throws Exception
	 {
		     Level level = new Level();
		     level.setId(new Long(levelid));
             Subject subjectObj = new Subject();
             subjectObj.setLevel(level);
             subjectObj.setSubject(subject);
             
             em.persist(subjectObj);
             
             //creating and populating plain object
             SubjectPlain sp = new SubjectPlain();
             sp.setId(subjectObj.getId().toString());
             sp.setSubject(subject);
		 
             return sp;
	 }
	 
	 @Transactional 
	 public TopicPlain createTopic(String subjectid, String topic) throws Exception
	 {
             Topic topicObj = new Topic();
             Subject subjectObj = new Subject();
             subjectObj.setId(new Long(subjectid));
             
             topicObj.setSubject(subjectObj);
             topicObj.setTopic(topic);
                      
             em.persist(topicObj);
             
             //creating and populating plain object
             TopicPlain tp = new TopicPlain();
             tp.setId(topicObj.getId().toString());
             tp.setTopic(topic);
		 
             return tp;
	 }	 
	 
	 @Transactional 
	 public QuestionPlain createQuestion(Question question,String userid) throws Exception
	 {
             question.setRecordStatus(getRecordStatus());
             question.getRecordStatus().setUpdatedBy(new Long(userid));
             em.persist(question);
             
             //creating and populating plain object
             QuestionPlain qp = new QuestionPlain();
             qp.setId(question.getId().toString());
             qp.setSubjectId(question.getSubjectId().toString());
             qp.setTopicId(question.getTopicId().toString());
             qp.setQuestionType(question.getQuestionType().toString());
             qp.setQuestion(question.getQuestion());
             qp.setOptionFirst(question.getOptionFirst());
             qp.setOptionSecond(question.getOptionSecond());
             qp.setOptionThird(question.getOptionThird());
             qp.setOptionFourth(question.getOptionFourth());
             qp.setAnswer(question.getAnswer());
             qp.setMaxMarks(question.getMaxMarks().toString());
             qp.setImageUrl(question.getImageUrl());
             qp.setUpdateDate(this.getDateString(question.getRecordStatus().getUpdatedOn()));
             qp.setUpdateTime(this.getTimeString(question.getRecordStatus().getUpdatedOn()));
             
		 
             return qp;
	 }
	 
	 
	 @Transactional
	 public boolean saveQuestionPaper(String qpStr) throws Exception
	 {
			
		    JsonParser parser = new JsonParser();
			JsonElement jsonTree = parser.parse(qpStr);

			if(jsonTree.isJsonPrimitive()){
			    JsonPrimitive jsonObject = jsonTree.getAsJsonPrimitive();
			    String qpJsonStr = jsonObject.getAsString();
			    
			    //System.out.println(name);
			    
			    jsonTree =parser.parse(qpJsonStr);
			    JsonObject questionPaper=jsonTree.getAsJsonObject();
			    
			    //creating QuestionPaper bject
			    
			    QuestionPaper QP = new QuestionPaper();
			    QP.setLevelId(new Long(questionPaper.get("level").getAsString()));
			    QP.setSubjectId(new Long(questionPaper.get("subject").getAsString()));
			    QP.setQpName(questionPaper.get("name").getAsString());
			    QP.setPassPercent(new Float(questionPaper.get("passpercent").getAsString()));
			    QP.setRecordStatus(getRecordStatus());
			    QP.getRecordStatus().setUpdatedBy(new Long(questionPaper.get("userid").getAsString()));
			    
			    em.persist(QP);
			    
			    
                //get topic object
			    TypedQuery<Topic> topicQry = em.createNamedQuery("Topic.FindById", Topic.class);
			    topicQry.setParameter("Id", new Long(questionPaper.get("topic").getAsString()));
			    
			    Topic topic=topicQry.getSingleResult();
			    //QPTopic topic=em.find(QPTopic.class, new Long(questionPaper.get("topic").getAsString()));
			    //adding topic to question paper
			    if (QP.getTopics() == null)
			    {
			    	Set<Topic> topics= new HashSet<Topic>();
			    	QP.setTopics(topics);
			    	//now add topic to paper
			    	QP.getTopics().add(topic);
			    } else
			    {
			    	//now add topic to paper
			    	QP.getTopics().add(topic);
			    }
			    
			    //em.merge(QP);
			    
			    //System.out.println(questionPaper.get("name"));
			    //System.out.println(questionPaper.get("passpercent"));
			    
			    //extracting sections from question paper json
			    JsonArray sections=questionPaper.getAsJsonArray("sections");
			    
			    for(JsonElement sectionElement : sections) {
			    	JsonObject jsonSection=sectionElement.getAsJsonObject();
			    	
			    	QPSection section = new QPSection();
			    	section.setSectionName(jsonSection.get("sectionname").getAsString());
			    	section.setSectionType(new Long(jsonSection.get("sectiontype").getAsString()));
			    	section.setMaxMarks(new Float(jsonSection.get("maxmarks").getAsString()));
			    	section.setQuestionPaper(QP);
			    	
			    	em.persist(section);
			    	
				    JsonArray questions = jsonSection.getAsJsonArray("questions");
				    for (JsonElement quesId:questions)
				    {
                        Question ques = em.find(Question.class, new Long(quesId.getAsString()));
                        
                        if (section.getQuestions() == null)
                        {
                        	Set<Question> quesSet = new HashSet<Question>();
                        	section.setQuestions(quesSet);
                        	
                        	section.getQuestions().add(ques);
                        } else
                        {
                        	section.getQuestions().add(ques);
                        }
                        
				    	//System.out.println(element.getAsString()); 
				    }
				    
				    
				    if (QP.getQpSections() == null)
				    {
				    	Set<QPSection> qpsec = new HashSet<QPSection>();
				    	QP.setQpSections(qpsec);
				    	
					    //adding section to Question paper
					    QP.getQpSections().add(section);
				    	
				    }
				    else {
					    //adding section to Question paper
					    QP.getQpSections().add(section);
			    	
				    }
			    	
			    }
			    
			 //Persisting Question Paper
			   // em.persist(QP);
			    
			}
			
			return true;
	   }
			    
/*			    JsonObject firstSection = sections.get(0).getAsJsonObject();
			    
			    System.out.println(firstSection.get("sectionid"));
			    System.out.println(firstSection.get("sectionname"));
			    System.out.println(firstSection.get("sectiontype"));
			    System.out.println(firstSection.get("maxmarks"));
			    
			    JsonArray questions = firstSection.getAsJsonArray("questions");
			    for (JsonElement element:questions)
			    {
			       System.out.println(element.getAsString()); }
			    
			    

			    JsonElement passpercent = jsonObject.getAsJsonObject().get("passpercent");
			    
			    System.out.println(passpercent.getAsString()); */



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
