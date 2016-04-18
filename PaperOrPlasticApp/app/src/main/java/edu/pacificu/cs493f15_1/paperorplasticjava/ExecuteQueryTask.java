package edu.pacificu.cs493f15_1.paperorplasticjava;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jo9026 on 11/30/2015.
 */


public class ExecuteQueryTask extends AsyncTask<String, Void, Void>
{
	//Setup for returning results
	public interface AsyncResponse {
		void processFinish(JSONArray output);
	}

	public AsyncResponse delegate;

	public ExecuteQueryTask (AsyncResponse delegate){
		this.delegate = delegate;
	}

	//Setup for API connection
	String website = "http://api.nutritionix.com/v1_1/search/";
	StringBuilder sb = new StringBuilder ();
	JSONArray returnJSONArray = new JSONArray();

	JSONObject queryBundle = new JSONObject ();
	JSONObject filters = new JSONObject ();
	JSONObject sort = new JSONObject();
	JSONArray fields = new JSONArray();

	@Override
	protected Void doInBackground (String... query) {
		try
		{
			URL url = new URL (website);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestMethod("POST");
			urlConnection.connect();



			//Login
			queryBundle.put("appId", "0f0b5b93");
			queryBundle.put("appKey", "f468c4aec88a5de24bf91e30a9f491bf");

			//Fields
			fields.put ("item_name");
			fields.put ("brand_name");
			fields.put ("item_description");
			fields.put ("nf_calories");
			fields.put ("nf_total_fat");
			fields.put ("nf_saturated_fat");
			fields.put ("nf_total_carbohydrate");
			fields.put ("nf_protein");
			fields.put ("nf_sugars");
			fields.put ("nf_dietary_fiber");
			fields.put ("nf_sodium");

			fields.put ("nf_monounsaturated_fat");
			fields.put ("nf_polyunsaturated_fat");
			fields.put ("nf_trans_fatty_acid");
			fields.put ("nf_cholesterol");
			fields.put ("nf_potassium");
			fields.put ("nf_vitamin_a_dv");
			fields.put ("nf_vitamin_c_dv");
			fields.put ("nf_calcium_mg");
			fields.put ("nf_iron_mg");
			queryBundle.put("fields", fields);

			//Sorting
			sort.put("field", "_score");
			sort.put("order", "desc");
			queryBundle.put("sort", sort);

			//Filters
			filters.put("item_type", 2);
			queryBundle.put("filters", filters);

			queryBundle.put("query", query[0]);
			queryBundle.put("min_score", 0.2);
			queryBundle.put("offset", 0);
			queryBundle.put("limit", 10);


			OutputStreamWriter out = new OutputStreamWriter (urlConnection.getOutputStream());
			out.write(queryBundle.toString());
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

				JSONObject parser = new JSONObject(sb.toString());
				JSONArray hits = parser.getJSONArray("hits");

				int hitsSize = hits.length();

				for (int i = 0; i < hitsSize; i++) {
					JSONObject resultItem = hits.getJSONObject(i);

					returnJSONArray.put(resultItem);
				}

				buffer.close();
				System.out.println("" + sb.toString());

			}

			else
			{
				System.out.println (urlConnection.getResponseMessage());
			}

			delegate.processFinish(returnJSONArray);
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
