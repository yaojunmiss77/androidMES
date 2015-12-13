package tool;

import com.yaojun.bean.User;

import java.util.StringTokenizer;

import org.json.JSONException;

public class JsonUser {

		public static  User getJsonUser(String json)throws JSONException {
			User user=new User();
			//先转义成字符串
			  StringTokenizer content = new StringTokenizer(json,",;");

	        	int id=Integer.parseInt(content.nextToken().toString());
	
	        	String name=content.nextToken().toString();
	
	        	String number=content.nextToken().toString();
	
	        	String password=content.nextToken().toString();

		        
				user.setId(id);
				user.setName(name);
				user.setNumber(number);
				user.setPassword(password);

			return user;
		}




}
