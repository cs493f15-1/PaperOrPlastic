package edu.pacificu.cs493f15_1.paperorplasticjava;

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

/**
 * Created by jo9026 on 11/30/2015.
 */
public class ExecuteQueryTask extends AsyncTask<String, Void, Void>
{
	String website = "http://api.nutritionix.com/v1_1/search/";
	StringBuilder sb = new StringBuilder ();

	@Override
	protected Void doInBackground (String... query) {
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
			JSONObject filters = new JSONObject ();
			JSONArray fields = new JSONArray();

			login.put ("appId", "0f0b5b93");
			login.put ("appKey", "f468c4aec88a5de24bf91e30a9f491bf");

			fields.put ("item_name");
			fields.put ("brand_name");
			fields.put ("item_description");
			fields.put ("nf_calories");
			fields.put ("nf_calories_from_fat");
			fields.put ("nf_total_fat");
			fields.put ("nf_saturated_fat");
			fields.put ("nf_total_carbohydrate");
			fields.put ("nf_protein");
			fields.put ("nf_sugars");
			fields.put ("nf_dietary_fiber");
			fields.put ("nf_sodium");
			fields.put ("nf_servings_per_container");
			fields.put ("nf_serving_size_qty");
			fields.put ("nf_serving_size_unit");
			fields.put ("nf_serving_weight_grams");

			filters.put ("item_type", 1);

			login.put ("fields", fields);
			login.put ("filters", filters);
			login.put ("min_score", 0);
			login.put ("limit", 10.0);
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

				//JSONObject jsonRootObject = new JSONObject(sb.toString());

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
