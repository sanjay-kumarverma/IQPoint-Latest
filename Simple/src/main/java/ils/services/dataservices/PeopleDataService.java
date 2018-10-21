package ils.services.dataservices;

import ils.json.UserPlainV2;
import ils.persistence.repositories.PeopleDataRepository;
import ils.testspring.context.SpringApplicationContext;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Path("/qb")
//@RolesAllowed({"admin","manager"})
public class PeopleDataService {
	
	final static Logger logger = Logger.getLogger(PeopleDataService.class);
	
	@POST
    @Path("/people/searchPeople")
    @Produces(MediaType.TEXT_HTML)	
	public Response getPeopleBySeachCriteria(@FormParam("level") String level,
												@FormParam("role") String role,
												@FormParam("userid") String userid,
												@FormParam("userRole") String userRole,
												@FormParam("datefrom") String dateFrom,
												@FormParam("dateto") String dateTo,
												@FormParam("freetext") String freeText)
	{

		List<UserPlainV2> qList=null;
		PeopleDataRepository peopleRep= (PeopleDataRepository)SpringApplicationContext.getBean("PeopleDataAccessBean");
		try {
			qList=peopleRep.getSearchedPeople(level,userid,userRole,role,dateFrom,dateTo,freeText);
		    
		} catch(javax.persistence.NoResultException nr) {
			qList=null;
		}
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("PeopleDataService-Error getting list of people, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(qList);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	@POST
    @Path("/people/promote")
    @Produces(MediaType.TEXT_HTML)	
	public Response promoteUser( 			    @FormParam("userid") String userid,
												@FormParam("role") String userRole)
	{

		UserPlainV2 up=null;
		PeopleDataRepository peopleRep= (PeopleDataRepository)SpringApplicationContext.getBean("PeopleDataAccessBean");
		try {
			up=peopleRep.promoteUser(userid,userRole);
		    
		} 
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("PeopleDataService-Error promoting user, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(up);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	@POST
    @Path("/people/updateLevel")
    @Produces(MediaType.TEXT_HTML)	
	public Response updateUserLevel( @FormParam("userid") String userid,
								     @FormParam("level") String levelid)
	{

		UserPlainV2 up=null;
		PeopleDataRepository peopleRep= (PeopleDataRepository)SpringApplicationContext.getBean("PeopleDataAccessBean");
		try {
			up=peopleRep.updateUserLevel(userid,levelid);
		    
		} 
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("PeopleDataService-Error updating user level, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(up);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
		
	
	
}
