package teammaize.android.com;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;

class RequestTask extends AsyncTask<String, Void, String>{

	private QuestionsDataSource data;
	private Context _context;
	
    public RequestTask(QuestionsDataSource dataSource, Context context) {
    	super();
    	
    	data = dataSource;
    	_context = context;
    	
    }
	
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException");
        } catch (IOException e) {
            System.out.println("IOException in AsyncTask");
        }
        
        return responseString;
    }
    


    
    protected void onPostExecute(String result) {
        
    	data = new QuestionsDataSource(_context);
    	data.open();
    	data.addData(result);
    	//data.close();
    	System.out.println("Got Data from Website " + result);
    	       
        
        
    }
}
