package domain;

public class Static {
	private String status;
	private String author;
	private int num;

	public Static(String status, String author, int num) {
		super();
		this.status = status;
		this.author = author;
		this.num = num;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
