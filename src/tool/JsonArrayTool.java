package tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonArrayTool {
	private JSONArray jsonArray;
	private JSONObject jsonObj;
	private String[] string;

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public JSONObject getJsonObj() {
		return jsonObj;
	}

	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}

	public String[] getString() {
		return string;
	}

	public void setString(String[] string) {
		this.string = string;
	}

	public String strToStr(String str) {
		return "["+str+"]";
	}
	public String[] jsonArrayToString(String str) {
		
		try {
			this.setJsonArray(new JSONArray(str));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		string = new String[jsonArray.length()];
		for (int i = 0; i < jsonArray.length(); i++) {
			
			try {
				this.setJsonObj(this.getJsonArray().getJSONObject(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			string[i] = strToStr(this.getJsonObj().toString());
		}
		return string;
	}
}
