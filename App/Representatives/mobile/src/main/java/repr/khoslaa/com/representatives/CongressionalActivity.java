package repr.khoslaa.com.representatives;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CongressionalActivity extends Activity {

    public ArrayList<String> repNames;
    public ArrayList<String> websites;
    public ArrayList<String> emails;
    public ArrayList<String> tweets;
    RecyclerView recList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_congressional);



        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        repNames = new ArrayList<>();
        websites = new ArrayList<>();
        emails = new ArrayList<>();
        tweets = new ArrayList<>();


        Intent currentIntent = getIntent();
        Boolean isZip = currentIntent.getBooleanExtra("IS_ZIPCODE", false);


        String urlString = "";
        if (isZip) {
            String value = currentIntent.getStringExtra("VALUE");
            urlString = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=";
            urlString += value;
            urlString += "&apikey=1346c165073d46dc8badcb108486150b";
        } else {
            urlString = "https://congress.api.sunlightfoundation.com/legislators/locate?latitude=";
            urlString += currentIntent.getStringExtra("LAT");
            urlString += "&longitude=";
            urlString += currentIntent.getStringExtra("LONG");
            urlString += "&apikey=1346c165073d46dc8badcb108486150b";
        }

        final List<RepInfo> repInfoList = new ArrayList<RepInfo>();

        Ion.with(getApplicationContext())
                .load(urlString)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("T", "got in this oncomplete");
                        if (result == null) {
                            Log.d("T", "result is null");
                            if (e != null) {
                                Log.d("T", e.toString());
                            } else {
                                Log.d("T", "exception is null too");

                            }
                        } else {
                            Log.d("T", result.toString());
                            JsonArray results = result.get("results").getAsJsonArray();
                            Log.d("T", "as json array is" + results);

                            Iterator<JsonElement> resultIter = results.iterator();

                            while (resultIter.hasNext()) {
                                JsonObject currResult = (JsonObject) resultIter.next();
                                RepInfo currRepInfo = new RepInfo();
                                String currFirstName = currResult.get("first_name").getAsString();
                                String currLastName = currResult.get("last_name").getAsString();
                                currRepInfo.setFullName(currFirstName + " " + currLastName);
                                currRepInfo.setEmail(currResult.get("oc_email").getAsString());
                                currRepInfo.setWebsite(currResult.get("website").getAsString());
                                currRepInfo.setParty(currResult.get("party").getAsString());
                                currRepInfo.setTweet("Blank tweet for right now");
                                currRepInfo.setTermEnds(currResult.get("term_end").getAsString());
                                currRepInfo.setRepId(currResult.get("bioguide_id").getAsString());
                                currRepInfo.setTwitterId(currResult.get("twitter_id").getAsString());
                                repInfoList.add(currRepInfo);
                            }

                            populateRecyclerView(repInfoList);

                            JsonObject firstResult = (JsonObject) results.get(0);
                            Log.d("T", "as firstresult is" + firstResult);
                            Log.d("T", "so the list here is" + firstResult.get("first_name").getAsString());
                        }


                    }
                });




    }

    public void populateRecyclerView(List<RepInfo> repInfoList) {
        Log.d("T", "the repinfo list is currently" + repInfoList.toString());
        CongressionalViewAdapter ca = new CongressionalViewAdapter(repInfoList, getApplicationContext());
        recList.setAdapter(ca);
    }

}
