package com.mobileappdevelopersclub.shellp.transactions;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public abstract class AbsHttpTask extends AsyncTask<Void, Void, String> {

    private HttpClient mClient;
    private HttpUriRequest mRequest;

	public AbsHttpTask() {
        mClient =  new DefaultHttpClient(); 
    }
	
	public AbsHttpTask(String verb, String url) {
		
		mClient = new DefaultHttpClient();
		mRequest = this.createRequest(verb, url);
		
	}
   
    @Override
    protected String doInBackground(Void... params) {
    	
    	String response = null;
    	
    	try{
        	
            HttpResponse serverResponse = mClient.execute(getRequest());
            
            String status = serverResponse.getStatusLine().toString();
            int code = serverResponse.getStatusLine().getStatusCode();
            
            // TODO Handle other HTTP status codes
            switch(code) {
	            case 401:
	            	onError(status);
	            	cancel(true);
	            	break;
            }

            response = EntityUtils.toString(serverResponse.getEntity());
            
        } 
        catch (Exception e) {
        	onError("Http error: " + e.getMessage());
        	cancel(true);
        }
    	
    	return response;
    	
    }
    
    abstract protected void onError(String error);
    abstract protected void onSuccess(String response);
    
    protected void onPostExecute(String httpResponse) {
    	onSuccess(httpResponse);
    }
    
	private HttpUriRequest createRequest(String verb, String url) {

		URI requestUri = null;
		try {
			requestUri = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}

		// Initialize request object
		HttpUriRequest httpRequest = null;

		// Create request object based on verb
		if(verb == "POST") {
			httpRequest = new HttpPost(requestUri);
		}
		else if(verb == "DELETE") {
			httpRequest = new HttpDelete(requestUri);
		}
		else if(verb == "PUT") {
			httpRequest = new HttpPut(requestUri);
		}
		else {
			httpRequest = new HttpGet(requestUri);
		}
			
		httpRequest.addHeader("Accept", "application/json");

		return httpRequest;

	}
	
	public HttpUriRequest getRequest() {
		return mRequest;
	}

	public void setRequest(HttpUriRequest request) {
		this.mRequest = request;
	}

}