package ils.testspring.testing;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ils.testspring.context.SpringApplicationContext;
import ils.testspring.testing.SpringRestIntegrationTest;

@Path("/springtest")
public class SprintTestService {
	
	@GET
    @Path("/showmessage")
    @Produces(MediaType.TEXT_HTML)	
	public Response getTestMessage()
	{
		SpringRestIntegrationTest srit= (SpringRestIntegrationTest)SpringApplicationContext.getBean("springTestBean");
		
		String result=srit.springIsWorking();
		
		return Response.status(200).entity(result).build();
	}

}
