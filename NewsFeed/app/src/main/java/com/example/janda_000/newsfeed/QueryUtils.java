package com.example.janda_000.newsfeed;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by janda_000 on 2/26/2017.
 */

public class QueryUtils {

    static String createStringUrl(){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("order-by", "newest")
                .appendQueryParameter("q", "Samsung")
                .appendQueryParameter("show-reference", "author")
                .appendQueryParameter("api-key", "3535e17c-d9a9-4314-8b0e-2f902f37f4a1");
        String url = builder.build().toString();
        Log.e("QueryUtils", "The following is the url" + url);
        return url;
    }

    static URL createUrl(){
        String stringUrl = createStringUrl();

        try{
            return new URL(stringUrl);
        }catch(MalformedURLException e){
            Log.e("QueryUtils", "There was an error creating the url e=", e);
            return null;
        }
    }

    static String makeHTTPRequest(URL url) throws IOException{

        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e("MainActivity", "There was an error with the url connection. Response code"
                + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e("QueryUtils", "Error while creating the http request. Error code: ", e);
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{

        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    static List<Article> parseJson(String response) {

        ArrayList<Article> listOfArticle = new ArrayList<>();

        try {

            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonResults = jsonResponse.getJSONObject("response");
            JSONArray resultsArray = jsonResults.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject singleArticle = resultsArray.getJSONObject(i);
                String wTitle = singleArticle.getString("webTitle");
                String wURL = singleArticle.getString("webUrl");
                String wSection = singleArticle.getString("sectionName");
                String wDate = singleArticle.getString("webPublicationDate");
                wDate = formattingDate(wDate);
                listOfArticle.add(new Article(wTitle, wSection, wDate, wURL));

            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "There was an error parting the information", e);

        }

        return listOfArticle;
    }

    private static String formattingDate(String rawDate) {

        String jsonDatePattern = "yyyy-mm-dd'T'HH:MM:SS'Z'";
        SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.US);
        try{
            Date parsedJsonPattern = jsonFormatter.parse(rawDate);
            String finalDatePattern = "mm dd, yyyy";
            SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);
            return finalDateFormatter.format(parsedJsonPattern);
        }catch (ParseException e){
            Log.e("QueryUtils", "There was an erro parsing the date - ", e);
            return "";
        }
    }

}
