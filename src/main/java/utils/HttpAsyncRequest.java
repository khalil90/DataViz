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

public abstract class HttpAsyncRequest implements Runnable {

	private URL url;
	
	private Thread selfThread;
	
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



	public HttpAsyncRequest(String path, String method, String params, String contentType) {
		try {
			this.url = new URL(path);
			this.contentType = contentType;
			this.stringParams = params;
			this.method=method;
			this.con = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.selfThread = new Thread(this);
	}

	public HttpAsyncRequest(String path, String method, Map<String, String> params, String contentType) {
		this(path, method, Utils.mapToQueryParam(params), contentType);
	}

	public void send() {
		this.selfThread.start();
	}

	public abstract void onResponse(String content);

	public abstract void onError(String errorMessage);

	public void run() {
		try {
			con.setRequestProperty("Content-Type", this.contentType);
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(stringParams);
			wr.flush();
			wr.close();
			responseCode = con.getResponseCode();
			responseMessage = con.getResponseMessage();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				this.content+=inputLine;
			}
			in.close();
			this.onResponse(this.content);
		} catch (IOException e) {
			this.onError(e.getMessage());
		}
	}
}
