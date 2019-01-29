package domain;

public class YearOfNum {
	private int year;
	private int month;
	private int num;

	public YearOfNum(int year, int month, int num) {
		super();
		this.year = year;
		this.month = month;
		this.num = num;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
