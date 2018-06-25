package com.algofi.dataviz.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.Getter;
import utils.Utils;

@RestController 
public class DatavizService {
	
	@Getter
	@Value("${api.code}")
	private String apiKey;
	
	@Getter
	@Value("${api.url}")
	private String url;

	@RequestMapping("/")
	public String ping() {
		return "ping succeeded";
	}
	
	@GET
	@RequestMapping(value="/bitcoin", method= RequestMethod.GET, produces ="application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bitcoinBaseEURRESTService(	@QueryParam("dataset_code") String dataset_code,
												@QueryParam("database_code") String database_code,
												@QueryParam("start_date") String start_date,
												@QueryParam("end_date") String end_date,
												@QueryParam("order") String order,
												@QueryParam("collapse") String collapse,
												@QueryParam("transformation") String transformation) throws JSONException, JsonParseException, JsonMappingException, IOException {
	    StringBuffer sbuff=new StringBuffer(url);
	    sbuff.append(dataset_code);
	    sbuff.append("/"+database_code);
	    sbuff.append(".json");
	    Map<String,String> params = new HashMap<String, String>();
		params.put("api_key", this.apiKey);
		params.put("transformation", transformation);
		params.put("collapse", collapse);
		params.put("order", order);
		params.put("end_date", end_date);
		params.put("start_date", start_date);
		JSONObject jsonResponse = new JSONObject(Utils.sendRequest(sbuff.toString(), params));
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse.toString()).build();
	}
	

	
	@GET
	@RequestMapping(value="/request", method= RequestMethod.GET, produces ="application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testQueryParamRESTService(	@QueryParam("dataset_code") String dataset_code,
												@QueryParam("database_code") String database_code,
												@QueryParam("start_date") String start_date,
												@QueryParam("end_date") String end_date,
												@QueryParam("limit") String limit,
												@QueryParam("columnIndex") String columnIndex,
												@QueryParam("order") String order,
												@QueryParam("collapse") String collapse,
												@QueryParam("transformation") String transformation) throws JSONException {
		
	    StringBuffer sbuff=new StringBuffer(url);
	    sbuff.append(dataset_code);
	    sbuff.append("/"+database_code);
	    sbuff.append(".json");
		Map<String,String> params = new HashMap<String, String>();
		params.put("api_key", this.apiKey);
		params.put("transformation", transformation);
		params.put("collapse", collapse);
		params.put("order", order);
		params.put("end_date", end_date);
		params.put("start_date", start_date);
		if(limit!=null){
			if(Integer.valueOf(limit)>0){
				params.put("limit", limit);
			}
		}
		if(columnIndex!=null){
			if(Integer.valueOf(columnIndex)>-1){
				params.put("column_index", columnIndex);
			}
		}
		JSONObject jsonResponse = new JSONObject(Utils.sendRequest(sbuff.toString(), params));
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(jsonResponse).build();
	}
}
