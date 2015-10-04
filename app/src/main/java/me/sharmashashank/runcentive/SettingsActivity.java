package me.sharmashashank.runcentive;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * This Activity sets up the Fragment that references the
 * preferences.xml to create a settings view.
 */
public class SettingsActivity
        extends Activity {
    /**
     * Key to store play type that is selected in settings fragment.
     */
    public static final String WEIGHT =
            "weight";

    /**
     * Key to store download type that is selected in the settinsg
     * fragment.
     */
    public static final String CAL_RATIO =
            "calRatio";

    /**
     * Factory method that makes an explicit Intent to launch the
     * SettingsActivity.
     */
    public static Intent makeIntent(Context context){
        return new Intent(context,
                SettingsActivity.class);
    }

    /**
     * Overrides onCreate() to create the SettingsFragment.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Calls up to the superclass.
        super.onCreate(savedInstanceState);

        Log.d("vikas","Moved to SettingsActivity");

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content,
                        new SettingsFragment())
                .commit();
    }
}