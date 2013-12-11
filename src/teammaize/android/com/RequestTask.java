package teammaize.android.com;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

class RequestTask extends AsyncTask<String, Void, String>{

	//private QuestionsDataSource data;
	//private Context _context;
	private List<dbEntry> dataList;
	private MazeGUI m_gui;
	
    public RequestTask(QuestionsDataSource dataSource, Context context, List<dbEntry> list, MazeGUI maze_gui) {
    	super();
    	
    	//data = dataSource;
    	//_context = context;
    	dataList = list;
    	m_gui = maze_gui;
    	
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
            System.out.println("ClientProtocolException " + e.toString());
        } catch (IOException e) {
            System.out.println("IOException in AsyncTask " + e.toString());
        }
        
        return responseString;
    }
    
    protected void onPostExecute(String result) {
    	//data = new QuestionsDataSource(_context);
    	//data.open();
    	//data.addData(result);
    	//data.close();
    	System.out.println("Got Data from Website " + result);
    	try {
    		if(!result.equals("null")) {
    			dataList = parseData(result);
    		}
    		else {
    			Log.v("RequestTask", "Server could not be reached");
    		}
    	}
        catch(NoSuchElementException e) {
        	System.out.println("NoSuchElementException " + e.toString());
        }
    	catch(Exception e) {
    		Log.v("RequestTask", "Exception thrown in onPostExecute: " + e.toString());
    	}
    	       
    	System.out.println("DATA LIST HAS: " + dataList.size());
    	
    	for (int i=0;i<dataList.size();i++) {
    		dataList.get(i).printEntry();
    	}
        
    	m_gui.setDataList(dataList);
        
    	
    	if (dataList.isEmpty()) {
    		return;
    	}
    	else {
    		
    		System.out.println("About to add to database");
    		
    		m_gui.clearDatabase();
    		
    		try {
    			m_gui.setDatabase(dataList);
    		}
    		catch (Exception e) {
    			Log.v("RequestTask", "Error when in setDatabase " + e.toString());
    		}
    	}
    }
    
    public List<dbEntry> parseData(String data) {
    	
    	List<dbEntry> list = new ArrayList<dbEntry>();
    	

    		 StringTokenizer strtok = new StringTokenizer(data, ",\n\t\r");
    		 dbEntry insertEntry;
    		 String garb;
    		 
    		 System.out.println("parseData has: " + strtok.countTokens() + " tokens");
    		
    		 garb = new String("");
    		 
    		 while(!garb.equals("<body>")) {
    			 System.out.println(garb);
    			 garb = strtok.nextToken();
    		 }
    		 
    	
    		 
    		 long count = 0;
    		 
    		 while (strtok.hasMoreTokens()) {
    			// StringTokenizer entry = new StringTokenizer(strtok.nextToken(), ",");
    			 //System.out.println("THERE ARE THIS MANY TOKENS IN THIS LINE: " + entry.countTokens());
    			 insertEntry = new dbEntry();
    			 
    	
    				 String tempStr = strtok.nextToken(",");
    				 
    				 
    				 if (tempStr.equals("</body>")) {
    					 System.out.println("BODY END TAG");
    					 return list;
    				 }
    			

    				 
    				 System.out.println("TEMPSTR = " + tempStr);

    			 if (strtok.hasMoreElements()) {
    			   tempStr = strtok.nextToken(",");
    			   insertEntry.qestion = tempStr;
    			 }
    			 if (strtok.hasMoreElements()) {
    			   tempStr = strtok.nextToken(",");
    			   insertEntry.ansCorrect = tempStr;
    			 }
    			 if (strtok.hasMoreElements()) {
    			   tempStr = strtok.nextToken(",");
    			   insertEntry.ans2 = tempStr;
    			 }
    			 if (strtok.hasMoreElements()) {
    			   tempStr = strtok.nextToken(",");
    			   insertEntry.ans3 = tempStr;
    			 }
    			 if (strtok.hasMoreElements()) {
    			   tempStr = strtok.nextToken(",");
    			   insertEntry.ans4 = tempStr;
    			 }
    			 if (strtok.hasMoreElements()) {
    			   tempStr = strtok.nextToken(",");
    			   insertEntry.subject = tempStr;
    			 }
    			 if (strtok.hasMoreElements()) {
    			   tempStr = strtok.nextToken(",");
    			   insertEntry.level = tempStr;
    			 }
    			 if (strtok.hasMoreElements()) {
    			   tempStr = strtok.nextToken(",");
    			   insertEntry.corAttempts = tempStr;
    			 }
    			 if (strtok.hasMoreElements()) {
    			   tempStr = strtok.nextToken(",");
    			   insertEntry.attempts = tempStr;
    			 }
    			 else {
    				 return list;
    			 }
    				
    			   System.out.println("//////////////");
    			   insertEntry.printEntry();
    			   System.out.println("//////////////");
    			   
    			   insertEntry.id = count;
    			   
    			   list.add(insertEntry);
    			   count++;
    		 } 
    		 
    		 
    	return list;
    	
    }
}
