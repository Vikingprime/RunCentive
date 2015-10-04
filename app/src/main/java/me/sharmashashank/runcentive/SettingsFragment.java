package me.sharmashashank.runcentive;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Represents a view of preferences.xml.
 */
public class SettingsFragment
        extends PreferenceFragment {
    /**
     * Overrides onCreate() to create preferences from the
     * preferences.xml file.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource.
        addPreferencesFromResource(R.xml.preferences);
    }
}