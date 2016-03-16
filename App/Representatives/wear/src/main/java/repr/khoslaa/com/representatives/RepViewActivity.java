package repr.khoslaa.com.representatives;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
public class RepViewActivity extends Activity {
    private TextView repNameWatch;
    ArrayList<String> repNames;
    ArrayList<String> repParties;
    ArrayList<Integer> profPicNames;
    Button nextButton;
    Button backButton;
    ImageView profileImageView;
    TextView partyWatch;
    Integer currIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_view);

        repNames = new ArrayList<>();
        repParties = new ArrayList<>();
        profPicNames = new ArrayList<>();
        currIndex = 0;
        repNames.add("Pete Aguilar");
        repNames.add("Diane Feinstein");
        repNames.add("Barbara Boxer");
        repParties.add("Democrat");
        repParties.add("Republican");
        repParties.add("Democrat");
        profPicNames.add(R.drawable.pete);
        profPicNames.add(R.drawable.diane);
        profPicNames.add(R.drawable.boxer);

        partyWatch = (TextView)findViewById(R.id.partyWatch);
        repNameWatch = (TextView)findViewById(R.id.repNameWatch);
        nextButton = (Button)findViewById(R.id.nextButton);
        backButton = (Button)findViewById(R.id.backButton);
        profileImageView = (ImageView)findViewById(R.id.profileImageView);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currIndex == 3) {
                    currIndex = 0;
                }

//                if (currIndex == 2) {
//                    Intent intent = new Intent(getApplicationContext(), VoteViewActivity.class);
//                    RepViewActivity.this.startActivity(intent);
//                }
                currIndex += 1;
                String currName = repNames.get(currIndex);
                repNameWatch.setText(currName);
                String currParty = repParties.get(currIndex);
                partyWatch.setText(currParty);
                profileImageView.setImageResource(profPicNames.get(currIndex));

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currIndex != 0) {
                    currIndex -= 1;
                }

                String currName = repNames.get(currIndex);
                repNameWatch.setText(currName);
                String currParty = repParties.get(currIndex);
                partyWatch.setText(currParty);
                profileImageView.setImageResource(profPicNames.get(currIndex));

            }
        });

//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mAccelerometer = mSensorManager
//                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        mShakeDetector = new ShakeDetector();
//        mShakeDetector.setOnShakeListener(new OnShakeListener() {
//
//            @Override
//            public void onShake(int count) {
//				/*
//				 * The following method, "handleShakeEvent(count):" is a stub //
//				 * method you would use to setup whatever you want done once the
//				 * device has been shook.
//				 */
//                handleShakeEvent(count);
//            }
//        });

//        Intent intent = getIntent();
//        if (intent != null) {
//            Bundle extras = intent.getExtras();
//            if (extras != null) {
//                String repName = extras.getString("RepName");
//                repNameWatch.setText(repName);
//                System.out.println("oh yes getting here");
//            } else {
//                System.out.println("oh no getting here");
//            }
//        }


    }
}
