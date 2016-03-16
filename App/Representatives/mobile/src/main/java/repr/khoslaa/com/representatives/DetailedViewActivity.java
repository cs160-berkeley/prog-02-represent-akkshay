package repr.khoslaa.com.representatives;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailedViewActivity extends Activity {

    TextView repName;
    TextView partyLabel;
    TextView email;
    TextView website;
    TextView termEnds;
    TextView currentComm;
    TextView bills;
    private String repId;
    CircleImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detailed_view);

        repName = (TextView) findViewById(R.id.repName);
        partyLabel = (TextView) findViewById(R.id.partyLabel);
        email = (TextView) findViewById(R.id.email);
        website = (TextView) findViewById(R.id.website);
        termEnds = (TextView) findViewById(R.id.termEnds);
        currentComm = (TextView) findViewById(R.id.currentComm);
        bills = (TextView) findViewById(R.id.bills);
        profileImageView = (CircleImageView) findViewById(R.id.profileImageView);

        Intent currIntent = getIntent();
        repName.setText(currIntent.getStringExtra("FULL_NAME"));
        if (currIntent.getStringExtra("PARTY").equals("D")) {
            partyLabel.setText("PARTY AFFILIATION:   Democrat");
        } else {
            partyLabel.setText("PARTY AFFILIATION:   Republican");
        }

        email.setText("EMAIL:   " + currIntent.getStringExtra("EMAIL"));
        website.setText("WEBSITE:   " + currIntent.getStringExtra("WEBSITE"));
        termEnds.setText("TERM ENDS:   " + currIntent.getStringExtra("TERM_ENDS"));
        repId = currIntent.getStringExtra("REP_ID");

        getBills();
        getComms();

        String urlString = "https://theunitedstates.io/images/congress/original/";
        urlString += repId;
        urlString += ".jpg";
        Picasso.with(getApplicationContext())
                .load(urlString)
                .into(profileImageView);


    }

    public void getBills() {
        String urlString = "https://congress.api.sunlightfoundation.com/bills?sponsor_id=";
        urlString += repId;
        urlString += "&apikey=1346c165073d46dc8badcb108486150b";


        Ion.with(getApplicationContext())
                .load(urlString)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonArray results = result.get("results").getAsJsonArray();
                        if (result == null) {
                            Log.d("T", " aresult is null");
                            Log.d("T", "aerror is " + e);
                        } else {
                            Log.d("T", "aresult is not null in this case");
                            Log.d("T", result.toString());
                            Log.d("T", "1 down aresult is not null in this case");
                            Log.d("T", result.get("results").toString());
                        }

                        if (result.get("results") == null) {
                            Log.d("T", "aresults get is null");
                        }

                        Iterator<JsonElement> resultIter = results.iterator();

                        String recentBills = "";
                        Integer numBills = 0;
                        while (resultIter.hasNext() && numBills < 4) {
                            JsonObject currResult = (JsonObject) resultIter.next();
                            Log.d("T", "2 down aresult is not null in this case");
                            Log.d("T", currResult.toString());
                            if (currResult.get("short_title") != null || currResult.get("official_title") != null) {
                                Log.d("T", "3 down aresult is not null in this case");




                                if (currResult.get("short_title") != null) {
                                    if (currResult.get("short_title").toString() != null && !currResult.get("short_title").toString().equals("null")) {
                                        recentBills += currResult.get("introduced_on").getAsString();
                                        recentBills += ": ";
                                        recentBills += currResult.get("short_title").toString();
//                                        if (resultIter.hasNext()) {
//                                            recentBills += ", ";
//                                        }
                                        recentBills += "\r\n";
                                        recentBills += "\r\n";
                                        numBills++;

                                    }

//                                    Log.d("T", currResult.get("short_title").getAsString());
                                    Log.d("T", "short title is not null 1");
                                    Log.d("T", "short title as string is " + currResult.get("short_title").toString());
                                } else if (currResult.get("official_title") != null) {
                                    if (currResult.get("official_title").toString() != null && !currResult.get("official_title").toString().equals("null")) {
                                        recentBills += currResult.get("introduced_on").getAsString();
                                        recentBills += ": ";
                                        recentBills += currResult.get("official_title").toString();
//                                        if (resultIter.hasNext()) {
//                                            recentBills += ", ";
//                                        }
                                        recentBills += "\r\n";
                                        recentBills += "\r\n";
                                        numBills++;
                                    }

//                                    Log.d("T", currResult.get("official_title").getAsString());
                                    Log.d("T", "official title is not null 2");
                                    Log.d("T", "official title as string is" + currResult.get("official_title").toString());
                                }



                            }

                        }

                        bills.setText(recentBills);


                    }
                });
    }

    public void getComms() {
        String urlString = "https://congress.api.sunlightfoundation.com/committees?subcommittee=false&member_ids=";
        urlString += repId;
        urlString += "&apikey=1346c165073d46dc8badcb108486150b";

        Ion.with(getApplicationContext())
                .load(urlString)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (result == null) {
                            Log.d("T", "result is null");
                            Log.d("T", "error is " + e);
                        } else {
                            Log.d("T", "result is not null in this case");
                            Log.d("T", result.toString());
                        }

                        if (result.get("results") == null) {
                            Log.d("T", "results get is null");
                        }

                        JsonArray results = result.get("results").getAsJsonArray();

                        Log.d("T", "the results for comms are" + results.toString());

                        Iterator<JsonElement> resultIter = results.iterator();

                        String comms = "CURRENT COMMITTEES:\r\n";

                        while (resultIter.hasNext()) {
                            JsonObject currResult = (JsonObject) resultIter.next();
                            comms += currResult.get("name").getAsString();
                            if (resultIter.hasNext()) {
                                comms += ", ";
                            }
                        }

                        currentComm.setText(comms);


                    }
                });

    }

}
