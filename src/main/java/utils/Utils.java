package utils;

import java.io.IOException;
import java.util.Map;

public class Utils {

	
	public static String sendRequest(String url, Map<String,String> params){
		HttpRequest httpRequest = new HttpRequest(
				url,
				"GET", 
				params,
				null);
		String result = "";
		try {
			result = httpRequest.send();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return result;
	}
	
	
	//http://localhost:8230/bitcoin/api_key=DKczFdjuL_16KZVxeZKk&transformation=rdiff&collapse=quarterly&order=asc&end_date=2018-05-01&start_date=2018-05-01
	public static String mapToQueryParam(Map<String, String> params){
		String query = "";
		for(String key : params.keySet()){
			query += key + "=" + params.get(key) + "&";
		}
		query = query.substring(0, query.length()-1);
		return query;
	}


}
