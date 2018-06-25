package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;

public class HttpRequest {

	public static final String JSON_FORMAT = "application/json";
	
	private String path;
	private URL url;
	
	private String content = "";
	
	private HttpURLConnection con;
	
	@Getter(value=AccessLevel.PACKAGE)
	private String method;
	
	private String stringParams;
	
	private String contentType;
	
	@Getter(value=AccessLevel.PACKAGE)
	private int responseCode;
	
	@Getter(value=AccessLevel.PACKAGE)
	private String responseMessage;
	
	private Map<String, String> queryParams;
	
	public HttpRequest(String path, String method, Map<String, String> queryParams, String contentType) {
		this.path=path;
		this.method=method;
		this.queryParams = queryParams;
		this.stringParams=Utils.mapToQueryParam(queryParams);
		this.contentType=contentType;
	}


	public String send() throws IOException {
		if(queryParams!=null){
			this.path += "?" + stringParams;
		}
		this.url = new URL(path);
		this.con = (HttpURLConnection) url.openConnection();
		if(this.contentType!=null){
			con.setRequestProperty("Content-Type", this.contentType);
		}
		if(queryParams==null){
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(stringParams);
			wr.flush();
			wr.close();
		}
		responseCode = con.getResponseCode();
		responseMessage = con.getResponseMessage();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			this.content+=inputLine;
		}
		in.close();
		return this.content;
	}
}
