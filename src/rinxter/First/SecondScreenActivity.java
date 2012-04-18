package rinxter.First;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondScreenActivity extends Activity {
	
	ArrayList<Information> info = new ArrayList<Information>();
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(rinxter.First.R.layout.screen2);
		
		TextView tv = (TextView) findViewById(rinxter.First.R.id.textView1);
		ArrayList<String> al = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		try {
			request.setURI(new URI("http://rinxter.net/wftda/ds?type=boutList&leagueId=1&season=2011"));
			HttpResponse response = client.execute(request);
			InputStream ips = response.getEntity().getContent();
			BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
			
			al = new ArrayList<String>();
			String s;
			while(true)
			{
				s = buf.readLine();
				if(s==null || s.length()==0)
					break;
				al.add(s);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		try {
			JSONObject myj = new JSONObject(al.get(0).substring(1));
			info.add(new Information(myj));
			for(int i = 1; i < al.size()-1; i++)
				info.add(new Information(new JSONObject(al.get(i))));
			myj = new JSONObject(al.get(al.size()-1).substring(0, al.get(al.size()-1).length()-1));
			info.add(new Information(myj));
			for(String key : info.get(0).x.keySet())
				tv.append(key + " ");
			tv.append("\n");
			for(int i = 0; i < info.size(); i++)
			{
				for(String key : info.get(0).x.keySet())
					tv.append(info.get(i).getData(key) + " ");
				tv.append("\n");
			}	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}

