package tool;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONException;

import com.yaojun.bean.Device;

public class JsonDevice {

	public static List<Device> getJsonDevice(String json)throws JSONException {
		List<Device> devices=new ArrayList<Device>();
		
		//先转义成字符串
		  StringTokenizer content = new StringTokenizer(json,",;");
		  
		  while(content.hasMoreTokens()){
			  
			  	Device device=new Device();

				device.setId(Integer.parseInt(content.nextToken().toString()));
				device.setName(content.nextToken().toString());
				device.setNumber(content.nextToken().toString());
				device.setType(content.nextToken().toString());
				device.setDate(content.nextToken().toString());
				
				devices.add(device);
			  
		  }

		return devices;
	}

}
