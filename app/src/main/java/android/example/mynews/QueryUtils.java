package android.example.mynews;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    /**
     * Query the Guardian's dataset and return a list  {@link News} article objects.
     */
    public static List<News> fetchNewsArticleData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}s
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link News} articles
        return news;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news article JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link News} article objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<News> extractFeatureFromJson(String newsArticleJSON) {

        if (TextUtils.isEmpty(newsArticleJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding news to
        List<News> news = new ArrayList<>();


        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsArticleJSON);

            // Extract the JSONObject associated with the key called "response",
            JSONObject newsArticleResponse = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of results from the Guardian API request.
            JSONArray newsArticleResultsArray = newsArticleResponse.getJSONArray("results");

            // For each news article in the newsArticleResultsArray, create an {@link News} article object
            for (int i = 0; i < newsArticleResultsArray.length(); i++) {

                // Get a single news article at position i within the list of news articles
                JSONObject currentNewsArticle = newsArticleResultsArray.getJSONObject(i);

                // Extract the value for the key called "sectionName"
                String NewsArticleSectionName = currentNewsArticle.getString("sectionName");

                // Extract the value for the key called "webTitle"
                String NewsArticleTitle = currentNewsArticle.getString("webTitle");

                // Extract the value for the key called "webTitle" in the array "tags".
                // This allows us to get the author's name.
                String NewsArticleAuthor = "";

                // Check if array object "tags" has any value
                if (currentNewsArticle.getJSONArray("tags").length() > 0) {
                    // If so, extract its (webTitle) value for the author's name
                    NewsArticleAuthor = currentNewsArticle.getJSONArray("tags").getJSONObject(0).getString("webTitle");
                } else {
                    NewsArticleAuthor = "No Author Listed";
                }


                // Extract the value for the key called "webPublicationDate"

                String NewsArticlePublicationDate = formatDate(currentNewsArticle.getString("webPublicationDate"));

                // Extract the value for the key called "webUrl"
                String NewsArticleUrl = currentNewsArticle.getString("webUrl");

                // Create a new {@link News} object with the news_article_section_name, NewsArticleTitle, NewsArticlePublicationDate,
                // and NewsArticleUrl from the JSON response.
                News newsfeed = new News(NewsArticleSectionName, NewsArticleTitle, NewsArticleAuthor, NewsArticlePublicationDate, NewsArticleUrl);

                // Add the new {@link News} to the list of news articles.
                news.add(newsfeed);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news article JSON results", e);
        }

        // Return the list of news
        return news;
    }


    private static String formatDate(String NewsArticlePublicationDate) {

        return NewsArticlePublicationDate;
    }

}