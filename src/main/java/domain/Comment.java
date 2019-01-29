package domain;

public class Comment {
	private int cid;
	private String content;
	private String author;
	private String time;
	private int ischange;

	public Comment(int cid, String content, String author, String time, int ischange) {
		super();
		this.cid = cid;
		this.content = content;
		this.author = author;
		this.time = time;
		this.ischange = ischange;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getIschange() {
		return ischange;
	}

	public void setIschange(int ischange) {
		this.ischange = ischange;
	}

}
