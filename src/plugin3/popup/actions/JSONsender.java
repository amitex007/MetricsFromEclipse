package plugin3.popup.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONsender {
	String link;
	JSONsender()
	{
		
	}
	public void init(String path,String url)throws IOException
	{
		File of= new File(path);
		LinkedHashMap<String, String> idMap= new LinkedHashMap<String, String>();
		idMap.put("PAR", "Number of Parameters");
		idMap.put("NSF", "Number of Static Attributes");
		idMap.put("CE", "Efferent Coupling");
		idMap.put("SIX", "Specialization Index");
		idMap.put("NOC", "Number of Classes");
		idMap.put("NOF", "Number of Attributes");
		idMap.put("RMA", "Abstractness");
		idMap.put("RMD", "Normalized Distance");
		idMap.put("NSM", "Number of Static Methods");
		idMap.put("NOI", "Number of Interfaces");
		idMap.put("TLOC", "Total Lines of Code");
		idMap.put("WMC", "Weighted methods per Class");
		idMap.put("NOM", "Number of Methods");
		idMap.put("DIT", "Depth of Inheritance Tree");
		idMap.put("NOP", "Number of Packages");
		idMap.put("RMI", "Instability");
		idMap.put("VG", "McCabe Cyclomatic Complexity");
		idMap.put("NBD", "Nested Block Depth");
		idMap.put("LCOM", "Lack of Cohesion of Methods");
		idMap.put("MLOC", "Method Lines of Code");
		idMap.put("NORM", "Number of Overridden Methods");
		idMap.put("CA", "Afferent Coupling");
		idMap.put("NSC", "Number of Children");

		Scanner in = new Scanner(of);
		ArrayList<String> al = new ArrayList<String>(idMap.keySet());
		//JSONArray version= new JSONArray();
		JSONObject jsonToSend= new JSONObject();
		
		while(in.hasNextLine())
		{
			
			String x[] = (in.nextLine()).split(" ");
			
			JSONObject metrics= new JSONObject();
			for(int i=1;i<x.length;i++)
			{
				
				metrics.put(al.get(i-1),x[i] );
				
			}
			jsonToSend.put(x[0], metrics);
			
			
			
		}
		
		/*HttpClient httpClient = new DefaultHttpClient(); //Deprecated
	   // HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

	    try {
	        HttpPost request = new HttpPost(url);
	        StringEntity params =new StringEntity(jsonToSend.toJSONString());
	        request.addHeader("content-type", "application/x-www-form-urlencoded");
	        request.setEntity(params);
	        HttpResponse response = httpClient.execute(request);

	        // handle response here...
	    }catch (Exception ex) {
	        // handle exception here
	    } finally {
	        httpClient.getConnectionManager().shutdown(); //Deprecated
	    }*/
		
		try{  HttpPost post = new HttpPost(url);
	      post.addHeader("Content-Type", "application/json");
	     // StringEntity entity = new StringEntity("{\"key\":\"value\",\"key2\":\"value2\"}");
	      StringEntity entity = new StringEntity(jsonToSend.toJSONString());
	      
	      post.setEntity(entity);
	      HttpClient client = new DefaultHttpClient();
	      HttpResponse response = client.execute(post);

	      // assert
	      
	      HttpEntity entity1 = response.getEntity();
	      String responseString = EntityUtils.toString(entity1, "UTF-8");
	      System.out.println(responseString);
	      System.err.println("RESPONSE: " + response.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
