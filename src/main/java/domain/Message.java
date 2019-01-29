package domain;

public class Message {
	private boolean isSuccess; // 成功与否
	private String mess; // 相关信息

	public Message() {
		super();
	}

	public Message(boolean isSuccess, String mess) {
		super();
		this.isSuccess = isSuccess;
		this.mess = mess;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}

}
