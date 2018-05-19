package ils.services.dataservices;

import java.util.List;

public class JsonQuestionPaper {
	
	private String name;
	private String passpercent;
	private List<JsonSection> sections;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasspercent() {
		return passpercent;
	}
	public void setPasspercent(String passpercent) {
		this.passpercent = passpercent;
	}
	public List<JsonSection> getSections() {
		return sections;
	}
	public void setSections(List<JsonSection> sections) {
		this.sections = sections;
	}
	
	

}
