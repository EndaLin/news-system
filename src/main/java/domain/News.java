package domain;

public class News {
	private int id;
	private String title;
	private String author;
	private String time;
	private String ischeck;

	public News(int id, String title, String author, String time, String ischeck) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.time = time;
		this.ischeck = ischeck;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

}
