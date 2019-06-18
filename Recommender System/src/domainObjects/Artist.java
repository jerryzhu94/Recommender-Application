package domainObjects;

public class Artist {

	public Artist(Integer id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}
	
	
	public String getName() {
		return name;
	}
	
	private Integer id;
	private String name;
	private String url;
}
