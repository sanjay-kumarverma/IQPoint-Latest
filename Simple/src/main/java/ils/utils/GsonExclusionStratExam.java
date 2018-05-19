package ils.utils;

import ils.persistence.domainclasses.ExamAssignment;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GsonExclusionStratExam implements ExclusionStrategy {
	
    public boolean shouldSkipClass(Class<?> clazz) {
    	
    	 return (clazz==ExamAssignment.class);
    }

   public boolean shouldSkipField(FieldAttributes f) {

/*        return (f.getDeclaringClass() == Student.class && f.getName().equals("firstName"))||
        (f.getDeclaringClass() == Country.class && f.getName().equals("name"));*/
	   return false;
    }

}
