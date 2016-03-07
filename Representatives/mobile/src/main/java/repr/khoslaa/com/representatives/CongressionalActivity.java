package repr.khoslaa.com.representatives;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.pkmmte.view.CircularImageView;
import android.widget.Button;
import android.content.Intent;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CongressionalActivity extends AppCompatActivity {

    public ArrayList<String> repNames;
    public ArrayList<String> websites;
    public ArrayList<String> emails;
    public ArrayList<String> tweets;
    Button moreButtonOne;
    Button moreButtonTwo;
    Button moreButtonThree;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional);

        repNames = new ArrayList<>();
        websites = new ArrayList<>();
        emails = new ArrayList<>();
        tweets = new ArrayList<>();

        moreButtonOne = (Button)findViewById(R.id.moreButtonOne);
        moreButtonTwo = (Button)findViewById(R.id.moreButtonTwo);
        moreButtonThree = (Button)findViewById(R.id.moreButtonThree);

        moreButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailedViewActivity.class);
                CongressionalActivity.this.startActivity(intent);
            }
        });

        moreButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailedViewActivity.class);
                CongressionalActivity.this.startActivity(intent);
            }
        });

        moreButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailedViewActivity.class);
                CongressionalActivity.this.startActivity(intent);
            }
        });

    }

}
