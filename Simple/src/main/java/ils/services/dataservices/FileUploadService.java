package ils.services.dataservices;


import ils.json.PhotoUrl;
import ils.persistence.repositories.UserDataRepository;
import ils.testspring.context.SpringApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
@Path("/qb")
public class FileUploadService {
	
	final static Logger logger = Logger.getLogger(FileUploadService.class);

	//private final String UPLOADED_PROFILE_FILE_PATH = "D:\\workspace\\DevelopmentWS\\Simple\\src\\main\\webapp\\files\\";
	//private final String UPLOADED_QUESTION_FILE_PATH = "D:\\workspace\\DevelopmentWS\\Simple\\src\\main\\webapp\\files\\questions\\";
	//private final String UPLOADED_PROFILE_FILE_PATH ="C:\\Users\\sanjay.verma\\git\\IQPoint\\Simple\\src\\main\\webapp\\files";
	//private final String UPLOADED_QUESTION_FILE_PATH = "C:\\Users\\sanjay.verma\\git\\IQPoint\\Simple\\src\\main\\webapp\\files\\questions\\";
	private final String UPLOADED_PROFILE_FILE_PATH ="/Users/sanjayverma/git/simple/Simple/src/main/webapp/files/";
	private final String UPLOADED_QUESTION_FILE_PATH = "/Users/sanjayverma/git/simple/Simple/src/main/webapp/files/questions/";

	
	@POST
	@Path("/upload/user/{userid}")
	@Produces(MediaType.TEXT_HTML)	
	@Consumes("multipart/form-data")
	public Response uploadFileForUserProfile(MultipartFormDataInput input,@Context HttpHeaders reqHeader,@PathParam("userid") String userId) {
	//public Response uploadFile(@FormParam("name") String name ) {

		return uploadFileGeneric(input, reqHeader, userId,"UserProfile");

	}
	
	@POST
	@Path("/upload/question/{questionid}")
	@Produces(MediaType.TEXT_HTML)	
	@Consumes("multipart/form-data")
	public Response uploadFileForQuestion(MultipartFormDataInput input,@Context HttpHeaders reqHeader,@PathParam("questionid") String userId) {
	//public Response uploadFile(@FormParam("name") String name ) {

		return uploadFileGeneric(input, reqHeader, userId,"Question");

	}

	private Response uploadFileGeneric(MultipartFormDataInput input,
			HttpHeaders reqHeader, String userId, String uploadFor) {
		String fileName = "";

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadFile");
        
		String jsonStr="";
		
		for (InputPart inputPart : inputParts) {

		 try {

			MultivaluedMap<String, String> header = inputPart.getHeaders();
			fileName = getFileName(header);
			
			String contentType=header.getFirst("Content-Type");
			float fileSize=new Float(reqHeader.getRequestHeader("Content-Length").get(0)).floatValue();
			
			if ((contentType.equalsIgnoreCase("image/jpeg") || contentType.equalsIgnoreCase("image/gif") || contentType.equalsIgnoreCase("image/bmp")) && (fileSize/1024) < 1000 )
			{
			
					//convert the uploaded file to inputstream
					InputStream inputStream = inputPart.getBody(InputStream.class,null);
		
					byte [] bytes = IOUtils.toByteArray(inputStream);
					
					//getting the timestamp
					String ts = new Long(System.currentTimeMillis()).toString();
					  
		            String onlyFileName=fileName;
					//constructs upload file path
		            if (uploadFor.equalsIgnoreCase("UserProfile")) 
		            	fileName = UPLOADED_PROFILE_FILE_PATH + ts +"_"+fileName;
		            else
		            	fileName = UPLOADED_QUESTION_FILE_PATH + ts +"_"+fileName;	
					
					//System.out.println("Filename -->"+fileName);
		
					writeFile(bytes,fileName);
		
					//System.out.println("Done");
					//setting photo url for user
					String pathprefix="";
					String photourl="";
					if (uploadFor.equalsIgnoreCase("UserProfile")) {
						UserDataRepository srit= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
						photourl=ts +"_"+onlyFileName;
						srit.updateUserPhotoUrl(new Long(userId), photourl);
						pathprefix="files/";
					} else if (uploadFor.equalsIgnoreCase("Question")) {
						photourl=ts +"_"+onlyFileName;
						pathprefix="files/questions/";
					}
					
					PhotoUrl pu = new PhotoUrl();
					pu.setUrl(pathprefix+photourl);
					Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
					
					jsonStr=gson.toJson(pu);
			}
			else
			{
				PhotoUrl pu = new PhotoUrl();
				pu.setUrl("FileSizeOrTypeError");
				Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
				
				jsonStr=gson.toJson(pu);				
			}

		  } catch (Exception e) {
	      	  logger.error("FileUploadService-Error in uploading file for user, cause ->"+ e.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 
		  }

		}

		return Response.status(200).entity(jsonStr).build();
	}

	/**
	 * header sample
	 * {
	 * 	Content-Type=[image/png],
	 * 	Content-Disposition=[form-data; name="file"; filename="filename.extension"]
	 * }
	 **/
	//get uploaded filename, is there a easy way in RESTEasy?
	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	//save to somewhere
	private void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}
}