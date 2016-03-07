package repr.khoslaa.com.representatives;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button findRepsButton;
    EditText editText;
    EditText editText2;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        editText = (EditText)findViewById(R.id.editText2);

        findRepsButton = (Button) findViewById(R.id.findRepsButton);
        findRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
//                sendIntent.putExtra("RepName", "Peter Aguilar");
//                startService(sendIntent);
                Intent intent = new Intent(getApplicationContext(), CongressionalActivity.class);
                MainActivity.this.startActivity(intent);

            }
        });

    }

}
