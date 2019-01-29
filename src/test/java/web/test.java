package web;

import service.SendEmail;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//          String mess = "123";
//    		byte[] resultBytes;
//			try {
//				resultBytes = MD5.eccrypt(mess);
//				String msg = new String(resultBytes);
//	    		System.out.println("√‹Œƒ «£∫" + Base64.getBase64(msg) );
//			} catch (NoSuchAlgorithmException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
    		SendEmail.send("admin","1139914806@qq.com");

	}

}
