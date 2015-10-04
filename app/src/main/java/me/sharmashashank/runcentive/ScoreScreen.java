package me.sharmashashank.runcentive;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScoreScreen extends Activity {

    double money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        Intent intent = getIntent();
        money = intent.getDoubleExtra("Money", 45);

        TextView displayMoney = (TextView) findViewById(R.id.congratsTextView);
        displayMoney.setText("Congratulation! You just earned yourself $" + money +
                " of guilt free money");


    }

    @Override
    public void onBackPressed(){
        doneMethod((Button) findViewById(R.id.doneBtn));
    }

    public void goToTwitter (View view) {
        Log.d("ScoreScreen", "Going to Twitter(hopefully)");
        String val=String.format("%.2f", money);
        String url = "https://twitter.com/home?status=I%20just%20earned%20$"
        +val+"%20of%20guilt%20free%20spending%20money%20with%20Runcentive!%20";
        Log.d("ScoreScreen", url);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void goToFacebook (View view) {
        Log.d("ScoreScreen", "Going to Facebook(hopefully)");
        String val=String.format("%.2f", money);
        String url="https://www.facebook.com/sharer/sharer.php?u=I%20just%20earned%20"+val+
                "%20guilt%20free%20spending%20money%20with%20Runcentive!";
        Log.d("ScoreScreen", url);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }


    public void  doneMethod (View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("Money", money);
        startActivity(intent);
    }

}
