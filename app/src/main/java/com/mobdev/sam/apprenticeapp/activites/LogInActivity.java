package com.mobdev.sam.apprenticeapp.activites;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.profile.LogInFragment;

/**
 * The Log In Activity. This is the activity which first loads when the app is started, and it displays
 * the log in fragment, allowing the user to log in or create an account.
 */
public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Replace the container with the log in fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new LogInFragment())
                .commit();
    }
}
