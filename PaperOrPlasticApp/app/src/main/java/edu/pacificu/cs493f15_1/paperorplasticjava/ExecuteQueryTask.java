package edu.pacificu.cs493f15_1.paperorplasticjava;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jo9026 on 11/30/2015.
 */
public class ExecuteQueryTask extends AsyncTask<String, Void, String>
{
	String website = "http://api.nutritionix.com/v1_1/search/";
	StringBuilder sb = new StringBuilder ();

	@Override
	protected String doInBackground (String... query) {
		try
		{
			URL url = new URL (website);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();
			urlConnection.setDoOutput (true);
			urlConnection.setDoInput (true);
			urlConnection.setRequestProperty ("Content-Type", "application/json");
			urlConnection.setRequestMethod ("POST");
			urlConnection.connect ();

			JSONObject login = new JSONObject ();
			ArrayList<String> attributes = new ArrayList<String> ();

			login.put ("appId", "0f0b5b93");
			login.put ("appKey", "f468c4aec88a5de24bf91e30a9f491bf");
			login.put ("query", query[0]);
			OutputStreamWriter out = new OutputStreamWriter (urlConnection.getOutputStream());
			out.write (login.toString ());
			out.close ();

			// Status Code Returns here.
			int HttpResult = urlConnection.getResponseCode ();

			if (HttpResult == HttpURLConnection.HTTP_OK)
			{
				BufferedReader buffer = new BufferedReader (new InputStreamReader(urlConnection.getInputStream (), "UTF-8"));


				String line = null;

				while ((line = buffer.readLine ()) != null)
				{
					sb.append (line + "\n");
				}

				buffer.close ();
				System.out.println ("" + sb.toString());
			}

			else
			{
				System.out.println (urlConnection.getResponseMessage());
			}
		}

		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		catch (IOException i)
		{
			i.printStackTrace ();
		}

		catch (JSONException j)
		{
			j.printStackTrace ();
		}

		return null;
	}
}
