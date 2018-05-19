package ils.services.dataservices;

import java.security.Principal;
//import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import ils.json.AnswerSheetPlain;
import ils.json.ExamExecutionPlain;
import ils.json.ExamPlain;
import ils.json.LevelPlain;
import ils.json.QuestionPaperPlain;
import ils.json.QuestionPlain;
import ils.json.QuestionPlainForScore;
import ils.json.SubjectPlain;
import ils.json.TopicPlain;
import ils.persistence.repositories.ExamDataRepository;
import ils.persistence.repositories.QuestionBankDataRepository;
import ils.persistence.repositories.UserDataRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ils.persistence.Exception.ErrorMessage;
import ils.persistence.domainclasses.AnswerSheet;
import ils.persistence.domainclasses.Exam;
import ils.persistence.domainclasses.ExamExecution;
import ils.persistence.domainclasses.Question;
import ils.persistence.domainclasses.QuestionTypeMaster;
import ils.persistence.domainclasses.Role;
import ils.persistence.domainclasses.Subject;
import ils.persistence.domainclasses.User;
import ils.persistence.domainclasses.embeddables.Address;
import ils.persistence.domainclasses.embeddables.RecordStatus;

import ils.testspring.context.SpringApplicationContext;
import ils.utils.GsonExclusionStrat;
import ils.utils.GsonExclusionStratExam;

@Service
@Path("/qb")
//@RolesAllowed({"admin","manager"})
public class QuestionBankDataService {
	
	final static Logger logger = Logger.getLogger(QuestionBankDataService.class);
	
	//@RolesAllowed("admin")


	@GET
    @Path("/questionbank/levels")
    @Produces(MediaType.TEXT_HTML)	
	public Response getLevels()
	{
		List<LevelPlain> levelslist=null;
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		try {
		    levelslist=exRep.getLevels();
		    
		} catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error getting levels, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(levelslist);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	//getting classes for a level (like Primary, Secondary etc)
	@GET
    @Path("/questionbank/level/classes/{levelname}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getLevelClasses(@PathParam("levelname") String levelname)
	{
		List<LevelPlain> levelslist=null;
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		try {
		    levelslist=exRep.getLevelClasses(levelname);
		    
		} catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error getting classes for level levels, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(levelslist);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	@GET
    @Path("/questionbank/subjectsForLevel/{id}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getSubjects(@PathParam("id") String id)
	{
		List<SubjectPlain> subjectslist=null;
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		try {
			subjectslist=exRep.getSubjectsForLevel(new Long(id));
		    
		} catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error getting subjects, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(subjectslist);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	
	
	@GET
    @Path("/questionbank/topicsForSubject/{id}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getTopics(@PathParam("id") String id)
	{
		List<TopicPlain> topicslist=null;
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		try {
			topicslist=exRep.getTopicsForSubject(new Long(id));
		    
		} catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error getting topics, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(topicslist);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	
	@GET
    @Path("/questionbank/questionTypes")
    @Produces(MediaType.TEXT_HTML)	
	public Response getQuestionTypes()
	{
		List<QuestionTypeMaster> qtypelist=null;
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		try {
			qtypelist=exRep.getQuestionTypes();
		    
		} catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error getting question types, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(qtypelist);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	
	 
	@POST
    @Path("/questionbank/searchQuestionBank")
    @Produces(MediaType.TEXT_HTML)	
	public Response getQuestionsBySeachCriteria(@FormParam("level") String levelId,
												@FormParam("subject") String subjectId,
												@FormParam("topic") String topicId,
												@FormParam("questiontype") String questionTypeId,
												@FormParam("datefrom") String dateFrom,
												@FormParam("dateto") String dateTo,
												@FormParam("freetext") String freeText)
	{

		List<QuestionPlain> qList=null;
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		//System.out.println("class name "+exRep.getClass().getCanonicalName());
		try {
			qList=exRep.getSearchedQuestions(levelId,subjectId,topicId,questionTypeId,dateFrom,dateTo,freeText);
		    
		} catch(javax.persistence.NoResultException nr) {
			qList=null;
		}
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error getting question types, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(qList);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	@POST
    @Path("/questionbank/saveQuestion")
    @Produces(MediaType.TEXT_HTML)	
	public Response saveQuestion(				@FormParam("id") String qId,
												@FormParam("question") String question,
												@FormParam("optionFirst") String optionFirst,
												@FormParam("optionSecond") String optionSecond,
												@FormParam("optionThird") String optionThird,
												@FormParam("optionFourth") String optionFourth,
												@FormParam("answer") String answer,
												@FormParam("maxMarks") String maxMarks,
												@FormParam("imageUrl") String imageUrl)
	{

		QuestionPlain ques=null;
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		try {
			ques=exRep.saveQuestion(qId,question,optionFirst,optionSecond,optionThird,optionFourth,answer,maxMarks,imageUrl);
		    
		} catch(javax.persistence.NoResultException nr) {
			ques=null;
		}
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error getting question types, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(ques);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	
	@POST
    @Path("/questionbank/addNewCourse")
    @Produces(MediaType.TEXT_HTML)	
	public Response addNewLevel(@FormParam("qualification") String qualification,
								@FormParam("course") String course)

	{

		LevelPlain level=null;
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		try {
			level=exRep.createLevel(qualification,course);
		}
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error creating new level, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(level);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	@POST
    @Path("/questionbank/addNewSubject")
    @Produces(MediaType.TEXT_HTML)	
	public Response addNewSubject(@FormParam("levelid") String levelid,
								@FormParam("subject") String subject)

	{

		SubjectPlain subjectPlain=null;
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		try {
			subjectPlain=exRep.createSubject(levelid,subject);
		}
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error creating new subject, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(subjectPlain);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	@POST
    @Path("/questionbank/addNewTopic")
    @Produces(MediaType.TEXT_HTML)	
	public Response addNewTopic(@FormParam("subjectid") String subjectid,
								@FormParam("topic") String topic)

	{

		TopicPlain topicPlain=null;
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		try {
			topicPlain=exRep.createTopic(subjectid,topic);
		}
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error creating new topic, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(topicPlain);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	


	@POST
    @Path("/questionbank/addNewQuestion")
    @Produces(MediaType.TEXT_HTML)	
	public Response addNewQuestion(
			                    @FormParam("userId") String userid,
								@FormParam("subjectid") String subjectid,
								@FormParam("topicid") String topicid,
								@FormParam("questionType") String questiontypeid,
								@FormParam("question") String question,
								@FormParam("optionFirst") String optionFirst,
								@FormParam("optionSecond") String optionSecond,
								@FormParam("optionThird") String optionThird,
								@FormParam("optionFourth") String optionFourth,
								@FormParam("answer") String answer,
								@FormParam("maxMarks") String maxMarks,
								@FormParam("imageUrl") String imageUrl
			
			)

	{

		QuestionPlain questionPlain=null;
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		try {
			Question quesObj =new Question();
			quesObj.setSubjectId(new Long(subjectid));
			quesObj.setTopicId(new Long(topicid));
			quesObj.setQuestionType(new Long(questiontypeid));
			quesObj.setQuestion(question);
			quesObj.setOptionFirst(optionFirst);
			quesObj.setOptionSecond(optionSecond);
			quesObj.setOptionThird(optionThird);
			quesObj.setOptionFourth(optionFourth);
			quesObj.setAnswer(answer);
			quesObj.setMaxMarks(new Float(maxMarks));
			quesObj.setImageUrl(imageUrl);
			
			
			questionPlain=exRep.createQuestion(quesObj,userid);
		}
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error creating new question, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(questionPlain);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	
	@POST
    @Path("/questionbank/saveQuestionPaper")
    @Produces(MediaType.TEXT_HTML)	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveQuestionPaper(String questionPaper )
	{

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();	
		QuestionBankDataRepository exRep= (QuestionBankDataRepository)SpringApplicationContext.getBean("QuestionBankDataAccessBean");
		try {
			String jsonstr= gson.toJson(questionPaper);
			
			exRep.saveQuestionPaper(jsonstr);
			
			
		    
		} catch(javax.persistence.NoResultException nr) {
			
		}
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionBankDataService-Error getting question types, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		} 


		String jsonstr="{'Test':'Success'}";

		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	
	/*		JsonParser parser = new JsonParser();


	JsonElement jsonTree = parser.parse(jsonstr);

	if(jsonTree.isJsonPrimitive()){
	    JsonPrimitive jsonObject = jsonTree.getAsJsonPrimitive();

	    String name = jsonObject.getAsString();
	    
	    System.out.println(name);
	    
	    jsonTree =parser.parse(name);
	    
	    JsonObject jsonobj=jsonTree.getAsJsonObject();
	    
	    
	    System.out.println(jsonobj.get("name"));
	    System.out.println(jsonobj.get("passpercent"));
	    
	    JsonArray sections=jsonobj.getAsJsonArray("sections");
	    
	    JsonObject firstSection = sections.get(0).getAsJsonObject();
	    
	    System.out.println(firstSection.get("sectionid"));
	    System.out.println(firstSection.get("sectionname"));
	    System.out.println(firstSection.get("sectiontype"));
	    System.out.println(firstSection.get("maxmarks"));
	    
	    JsonArray questions = firstSection.getAsJsonArray("questions");
	    for (JsonElement element:questions)
	    {
	       System.out.println(element.getAsString()); }
	    
	    

	    JsonElement passpercent = jsonObject.getAsJsonObject().get("passpercent");
	    
	    System.out.println(passpercent.getAsString());

	}	*/	
		
	
	
}
