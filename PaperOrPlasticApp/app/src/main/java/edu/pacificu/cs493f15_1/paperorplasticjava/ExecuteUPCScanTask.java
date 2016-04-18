package edu.pacificu.cs493f15_1.paperorplasticjava;

import android.os.AsyncTask;
import android.provider.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jo9026 on 4/4/2016.
 */
public class ExecuteUPCScanTask extends AsyncTask<String, Void, Void> {


	StringBuilder sb = new StringBuilder ();

	JSONObject queryBundle = new JSONObject();
	JSONObject returnJSONObject = new JSONObject();


	@Override
	protected Void doInBackground(String... UPC) {
		try {
			//String website = "https://api.nutritionix.com/v1_1/item?upc=" + UPC[0] + "&appId=0f0b5b93&appKey=f468c4aec88a5de24bf91e30a9f491bf";
			String website = "https://api.nutritionix.com/v1_1/item/";

			URL url = new URL(website);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

			int HttpResult1 = urlConnection.getResponseCode ();


			//Login
			queryBundle.put("upc", URLEncoder.encode(UPC[0], "UTF-8"));
			queryBundle.put("appId", "0f0b5b93");
			queryBundle.put("appKey", "f468c4aec88a5de24bf91e30a9f491bf");

			//Adding UPC
			//Long intUPC = Long.parseLong(UPC[0].trim());


			//int HttpResult1 = urlConnection.getResponseCode ();
/*			StringBuilder result = new StringBuilder();


			result.append("/v1_1/item?");

			result.append(URLEncoder.encode("upc", "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(UPC[0], "UTF-8"));

			result.append(URLEncoder.encode("appId", "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode("0f0b5b93", "UTF-8"));

			result.append(URLEncoder.encode("appKey", "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode("f468c4aec88a5de24bf91e30a9f491bf", "UTF-8"));*/

			//OutputStreamWriter out = new OutputStreamWriter (urlConnection.getOutputStream());
			//out.write(queryBundle.toString());
			//out.close ();


			//urlConnection.connect();

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

				JSONObject returnJSONObject = new JSONObject(sb.toString());

				buffer.close();
				System.out.println("" + sb.toString());

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
			i.printStackTrace();
		}
		catch (JSONException j)
		{
			j.printStackTrace ();
		}

		return null;
	}
}
