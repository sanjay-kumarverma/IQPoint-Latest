package ils.persistence.repositories;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import ils.json.ExamExecutionPlain;
import ils.json.ExamPlain;
import ils.json.QpSectionPlain;
import ils.json.QuestionPaperPlain;
import ils.json.QuestionPlain;
import ils.json.QuestionPlainForScore;
import ils.json.QuestionPlainOnlyId;
import ils.json.SubjectPlain;
import ils.json.TopicPlain;
import ils.persistence.domainclasses.*;
import ils.persistence.domainclasses.embeddables.RecordStatus;


//import ils.testspring.testing.SpringTest;

@Service("ExamDataAccessBean")
@Repository
@Transactional
public class ExamDataRepository {
	
	
	@PersistenceContext
	private EntityManager em;
     
	 @Transactional(readOnly=true)
	 public List<ExamPlain> listAllExamsForSubject(Long subjectid,Long userid) throws Exception
	 {
		     TypedQuery<Exam> qry = em.createNamedQuery("Exam.findBySubjectId", Exam.class);
		     qry.setParameter("subjectid", subjectid);
		     qry.setParameter("userid", userid);
		     
			 List<Exam> exams = qry.getResultList();
			 
             List<ExamPlain> examPlainList= new ArrayList<ExamPlain>();
             for(Exam ex:exams)
             {
    			 //populating plain object for json
    			 ExamPlain ep = new ExamPlain();
    			 ep.setId(ex.getId().toString());
    			 ep.setExam(ex.getExam());
    			 ep.setExamDate(getDateString(ex.getExamDate()));
    			 ep.setExamDuration(ex.getExamDuration().toString());
    			 ep.setUpdatedBy(ex.getRecordStatus().getUpdatedBy().toString());
    			 ep.setUpdatedOn(ex.getRecordStatus().getUpdatedOn().toString());
    			 
    			 
    			 //preparing question paper
    			 QuestionPaperPlain qpp = new QuestionPaperPlain();
    			 
    			 qpp.setId(ex.getQuestionPaper().getId().toString());
    			 qpp.setQpName(ex.getQuestionPaper().getQpName());
    			 qpp.setQpPassPercent(ex.getQuestionPaper().getPassPercent().toString());
    			 qpp.setUpdatedBy(ex.getQuestionPaper().getRecordStatus().getUpdatedBy().toString());
    			 qpp.setUpdatedOn(ex.getQuestionPaper().getRecordStatus().getUpdatedOn().toString());
    			 
    			 //getting question paper sections
 /*   			 Set<QPSection> qpsections=ex.getQuestionPaper().getQpSections();
    			 for(QPSection section:qpsections)
    			 {
    				QpSectionPlain qsp = new  QpSectionPlain();
    				qsp.setId(section.getId().toString());
    				qsp.setSectionType(section.getSectionType());
    				qsp.setSectionOrder(qsp.getSectionOrder());
    				
    				//getting questions of a section and only setting the question IDs initially
    				
    				Set<Question> questions=section.getQuestions();
    				for(Question question:questions)
    				{
    					QuestionPlain qp=new QuestionPlain();
    					qp.setId(question.getId().toString());
    					qp.setQuestionType(question.getQuestionType().toString());
    					
    					qsp.getQuestions().add(qp);
    				}
    				
    				qpp.getQpSections().add(qsp);
    				
    			 }*/
    			 
    			 ep.setQuestionPaperPlain(qpp);  
    			 
    			 
    			 
    			 examPlainList.add(ep);
    			 
             }
			 
			 return examPlainList;		 
	 }
	 
	 @Transactional
	 public QuestionPaperPlain listQuestionsByQPId(Long QPId, Long examId, Long userId) throws Exception
	 {
		     TypedQuery<QuestionPaper> qry = em.createNamedQuery("Exam.findByQuestionPaperId", QuestionPaper.class);
		     qry.setParameter("qpId", QPId);
		     
			 QuestionPaper qpaper = qry.getSingleResult();
			 
			 //preparing question paper
			 QuestionPaperPlain qpp = new QuestionPaperPlain();
			 
			 qpp.setId(qpaper.getId().toString());
			 qpp.setQpName(qpaper.getQpName());
			 qpp.setQpPassPercent(qpaper.getPassPercent().toString());
			 qpp.setUpdatedBy(qpaper.getRecordStatus().getUpdatedBy().toString());
			 qpp.setUpdatedOn(qpaper.getRecordStatus().getUpdatedOn().toString());		 
			 
   			 //getting question paper sections
			 Set<QPSection> qpsections=qpaper.getQpSections();
			 for(QPSection section:qpsections)
			 {
				QpSectionPlain qsp = new  QpSectionPlain();
				qsp.setId(section.getId().toString());
				qsp.setSectionType(section.getSectionType().toString());
				qsp.setSectionOrder(qsp.getSectionOrder());
				
				//getting questions of a section and only setting the question IDs initially
				
				Set<Question> questions=section.getQuestions();
				for(Question question:questions)
				{
					QuestionPlainOnlyId qp=new QuestionPlainOnlyId();
					qp.setId(question.getId().toString());
						
					qsp.getQuestions().add(qp);
				}
				
				qpp.getQpSections().add(qsp);
				
			 }
			 
			 //Here you have got all the questions, now make an entry in exam execution
			 //QuestionPaperPlain carries the exam execution id
			 ExamExecution examExec=examExecutionStarts(QPId, examId, userId);
			 qpp.setExamExecutionId(examExec.getId().toString());
			 
			 
			 return qpp;		 
	 }	
	 
	 //entry is made when exam execution started
	 @Transactional
	 public ExamExecution examExecutionStarts(Long qpId, Long examId, Long userId) throws Exception
	 {
		  ExamExecution examExec=new ExamExecution();
		  
		  examExec.setVenue("Default");
		  examExec.setTimeTaken(new Long(0));
		  examExec.setQpId(qpId);
		  examExec.setExamId(examId);
		  examExec.setUserId(userId);
		  examExec.setRecordStatus(getRecordStatus());
		  
		  em.persist(examExec);
		  
		  return examExec;
		  
		 
	 }
	 
	 @Transactional(readOnly=true)
	 public QuestionPlain getQuestionById(Long qId) throws Exception
	 {
		     TypedQuery<Question> qry = em.createNamedQuery("Question.FindById", Question.class);
		     qry.setParameter("Id", qId);
		     
			 Question question = qry.getSingleResult();
			 
			 //preparing question paper
			 QuestionPlain qpp = new QuestionPlain();
			 
			 qpp.setId(question.getId().toString());
			 qpp.setQuestionType(question.getQuestionType().toString());
//			 qpp.setOptionType(question.getOptionType().toString());
			 qpp.setQuestion(question.getQuestion());
			 qpp.setOptionFirst(question.getOptionFirst());
			 qpp.setOptionSecond(question.getOptionSecond());
			 qpp.setOptionThird(question.getOptionThird());
			 qpp.setOptionFourth(question.getOptionFourth());
			 qpp.setMaxMarks(question.getMaxMarks().toString());
			 if (question.getImageUrl()==null || question.getImageUrl()=="") 
				 qpp.setImageUrl("");
			  else
			     qpp.setImageUrl(question.getImageUrl());
			 
			 
			 return qpp;		 
	 }	
	 
	 
	 @Transactional
	 public AnswerSheet saveOrUpdateAnswer(Long userId,Long qPaperId,Long qId,String option) throws Exception
	 {
		 AnswerSheet answer=null;
		 
	     TypedQuery<AnswerSheet> qry = em.createNamedQuery("AnswerSheet.findByUserPaperQuestion", AnswerSheet.class);
	     qry.setParameter("userId", userId);
	     qry.setParameter("qpId", qPaperId);
	     qry.setParameter("questionId", qId);
	     
	     try {
		      answer = qry.getSingleResult();
              answer.setAnswer(option);		      
		      
	     } catch(javax.persistence.NoResultException nr)
	     {
	    	answer=new AnswerSheet();
	    	answer.setUserId(userId);
	    	answer.setQpId(qPaperId);
	    	answer.setQuestionId(qId);
	    	answer.setAnswer(option);
	    	
	    	em.persist(answer);
	    	
	     } catch(Exception ex)
	     {
	    	 throw new Exception("Exception while saving the answer.");
	     }
		 
		 return answer;
		 
	 }
	 
	 @Transactional
	 public void saveTestSession(Long userId,Long qPaperId,Long executionId) throws Exception
	 {
		 List<AnswerSheet> answers=null;
		 
	     TypedQuery<AnswerSheet> qry = em.createNamedQuery("AnswerSheet.findByUserPaper", AnswerSheet.class);
	     qry.setParameter("userId", userId);
	     qry.setParameter("qpId", qPaperId);
	     
	    	 
	    	  ExamExecution examExecution= em.find(ExamExecution.class, executionId);
		      answers = qry.getResultList();
		      
		      for(AnswerSheet as:answers)
		      {
		    	  as.setExamExecution(examExecution);
		    	  //examExecution.getAnswerSheet().add(as);
		      }
		      
             //setting duration in examExecution
		     java.sql.Timestamp prevtime=examExecution.getRecordStatus().getUpdatedOn();
		     
		     //set the current timeStamp to execution object
		     examExecution.setRecordStatus(getRecordStatus());
		     
		     //calculate the duration of test taken by candidate
		     Long duration = examExecution.getRecordStatus().getUpdatedOn().getTime() - prevtime.getTime();
		     examExecution.setTimeTaken(duration);
		     
		     //for display purpose, temporary code
		     long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		     long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		     long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
		     System.out.println("Hours :"+diffInHours+" Minutes :"+diffInMinutes+" Seconds :"+diffInSeconds);
		      
	 }
	 
	 
	 @Transactional
	 public List<QuestionPlainForScore> getScoreByExamExecutionId(Long executionId) throws Exception
	 {   
	         TypedQuery<Question> qryQuestion = em.createNamedQuery("Question.FindById", Question.class);
	         List<QuestionPlainForScore> questionPlainList=new ArrayList<QuestionPlainForScore>();
		 
			 List<AnswerSheet> answers=null;
			 
		     TypedQuery<AnswerSheet> qry = em.createNamedQuery("AnswerSheet.findByExamExecutionId", AnswerSheet.class);
		     qry.setParameter("examExecutionId", executionId);

		      answers = qry.getResultList();
		      
		      //get all the questions in question paper
		      Long qpid=answers.get(0).getQpId();
		      List<Question> totalQuestions = getQuestionsOfPaper(qpid);
		      
		      float totalMarks=0;
		      float score=0;
		      
		      for(AnswerSheet as:answers)
		      {

		    	  
	                 //getting the question
				     qryQuestion.setParameter("Id", as.getQuestionId());
					 Question question = qryQuestion.getSingleResult();
					 
		    	     //remove question from totalQuestions if it is found in answers 
		    	     totalQuestions.remove(question);					 
					 
		    	     QuestionPlainForScore qp = new QuestionPlainForScore();
					 qp.setId(question.getId().toString());
					 qp.setMaxMarks(question.getMaxMarks().toString());
					 
					 totalMarks+=question.getMaxMarks();
					 //checking the answer and setting the score
					 if (question.getAnswer().equalsIgnoreCase(as.getAnswer())) {
						 qp.setScore(question.getMaxMarks().toString());
						 score+=question.getMaxMarks();
					 } else {
						 qp.setScore(new Long("0").toString());
					 }
						 
					 questionPlainList.add(qp); 
					 
		      }
		      
		      //now we are left with those questions that were not answered
		      for(Question question:totalQuestions)
		      {
		    	     QuestionPlainForScore qp = new QuestionPlainForScore();
					 qp.setId(question.getId().toString());
					 qp.setMaxMarks(question.getMaxMarks().toString());
					 qp.setScore(new Long("0").toString()); //setting score of all such questions to 0
 
					 questionPlainList.add(qp); 		    	  
		    	  
					 totalMarks+=question.getMaxMarks();
		      }
		      
		      float percentage = (score/totalMarks)*100;
		      
		      //rounding off to two decimal places
		      DecimalFormat df = new DecimalFormat("#.##");
		      df.setRoundingMode(RoundingMode.CEILING);
		      String roundedPercentage=df.format(percentage);
		      
		      //this percentage is to be updated in examExcution object
		      ExamExecution ee=getExamExecutionById(executionId);
		      ee.setScore(new Float(roundedPercentage));
		      
		   return questionPlainList;
	 }
	 
	 @Transactional(readOnly=true)
	 public List<QuestionPlainForScore> getAnswerSheetByExamExecutionId(Long executionId) throws Exception
	 {   
	         TypedQuery<Question> qryQuestion = em.createNamedQuery("Question.FindById", Question.class);
	         List<QuestionPlainForScore> questionPlainList=new ArrayList<QuestionPlainForScore>();
		 
			 List<AnswerSheet> answers=null;
			 
		     TypedQuery<AnswerSheet> qry = em.createNamedQuery("AnswerSheet.findByExamExecutionId", AnswerSheet.class);
		     qry.setParameter("examExecutionId", executionId);

		      answers = qry.getResultList();
		      
		      //get all the questions in question paper
		      Long qpid=answers.get(0).getQpId();
		      List<Question> totalQuestions = getQuestionsOfPaper(qpid);
		      
		      float totalMarks=0;
		      float score=0;
		      
		      for(AnswerSheet as:answers)
		      {

		    	  
	                 //getting the question
				     qryQuestion.setParameter("Id", as.getQuestionId());
					 Question question = qryQuestion.getSingleResult();
					 
		    	     //remove question from totalQuestions if it is found in answers 
		    	     totalQuestions.remove(question);					 
					 
		    	     QuestionPlainForScore qp = new QuestionPlainForScore();
					 qp.setId(question.getId().toString());
					 qp.setQuestion(question.getQuestion());
					 qp.setMaxMarks(question.getMaxMarks().toString());
					 qp.setCorrectAnswer(question.getAnswer());
					 qp.setAnswerGiven(as.getAnswer());
					 
					 totalMarks+=question.getMaxMarks();
					 //checking the answer and setting the score
					 if (question.getAnswer().equalsIgnoreCase(as.getAnswer())) {
						 qp.setScore(question.getMaxMarks().toString());
						 score+=question.getMaxMarks();
					 } else {
						 qp.setScore(new Long("0").toString());
					 }
						 
					 questionPlainList.add(qp); 
					 
		      }
		      
		      //now we are left with those questions that were not answered
		      for(Question question:totalQuestions)
		      {
		    	     QuestionPlainForScore qp = new QuestionPlainForScore();
					 qp.setId(question.getId().toString());
					 qp.setCorrectAnswer(question.getAnswer());
					 qp.setMaxMarks(question.getMaxMarks().toString());
					 qp.setScore(new Long("0").toString()); //setting score of all such questions to 0
 
					 questionPlainList.add(qp); 		    	  
		    	  
					 totalMarks+=question.getMaxMarks();
		      }
		      
		      float percentage = (score/totalMarks)*100;
		      
		      //rounding off to two decimal places
		      DecimalFormat df = new DecimalFormat("#.##");
		      df.setRoundingMode(RoundingMode.CEILING);
		      String roundedPercentage=df.format(percentage);
		      
		   return questionPlainList;
	 }	 
	 
	 @Transactional
	 public ExamExecution getExamExecutionById(Long executionId) throws Exception
	 {
	     TypedQuery<ExamExecution> qry = em.createNamedQuery("ExamExecution.findById", ExamExecution.class);
	     qry.setParameter("execId", executionId);
	     
		 ExamExecution ee = qry.getSingleResult(); 
		 
		 return ee;
	 }
	 
	 @Transactional
	 public ExamExecutionPlain deleteExamExecutionById(Long executionId) throws Exception
	 {
	     TypedQuery<ExamExecution> qry = em.createNamedQuery("ExamExecution.findById", ExamExecution.class);
	     qry.setParameter("execId", executionId);
	     
		 ExamExecution ee = qry.getSingleResult(); 
		 ee.getRecordStatus().setDeleted(true);
		 
         ExamExecutionPlain  eep = new ExamExecutionPlain();
         eep.setId(ee.getId().toString());
		 
		 return eep;
	 }
	 
	 @Transactional
	 public List<Question> getQuestionsOfPaper(Long QPId) throws Exception
	 {
		     List<Question> questionsOfPaper = new ArrayList<Question>();
		 
		     TypedQuery<QuestionPaper> qry = em.createNamedQuery("Exam.findByQuestionPaperId", QuestionPaper.class);
		     qry.setParameter("qpId", QPId);
		     
			 QuestionPaper qpaper = qry.getSingleResult();
			 
   			 //getting question paper sections
			 Set<QPSection> qpsections=qpaper.getQpSections();
			 for(QPSection section:qpsections)
			 {
				//getting questions of a section and only setting the question IDs initially
				Set<Question> questions=section.getQuestions();
				for(Question question:questions)
				{
					questionsOfPaper.add(question);
				}
				
			 }
			 
			 return questionsOfPaper;		 
	 }		 
	 
	 
	 @Transactional(readOnly=true)
	 public List<ExamExecutionPlain> getAttemptsByExamId(Long examId, Long userId) throws Exception
	 {
		     TypedQuery<ExamExecution> qry = em.createNamedQuery("ExamExecution.findExamIdUserId", ExamExecution.class);
		     qry.setParameter("examId", examId);
		     qry.setParameter("userId", userId);
		     
			 List<ExamExecution> examExecutions = qry.getResultList();
			 
			 //preparing plain examExcution object array
			 List<ExamExecutionPlain> eePlainList=new ArrayList<ExamExecutionPlain>();
			 
			 for(ExamExecution ee:examExecutions)
			 {
				 ExamExecutionPlain eepObj = new ExamExecutionPlain();
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
				 
				 eePlainList.add(eepObj);
			 }
			 
			 return eePlainList;		 
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
