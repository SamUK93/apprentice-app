package com.mobdev.sam.apprenticeapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.sam.apprenticeapp.R;
import com.mobdev.sam.apprenticeapp.fragments.admin.AdminFragment;
import com.mobdev.sam.apprenticeapp.fragments.CalendarFragment;
import com.mobdev.sam.apprenticeapp.fragments.HomeFragment;
import com.mobdev.sam.apprenticeapp.fragments.profile.ProfileFragment;
import com.mobdev.sam.apprenticeapp.fragments.SettingsFragment;
import com.mobdev.sam.apprenticeapp.fragments.social.SocialFragment;
import com.mobdev.sam.apprenticeapp.fragments.study.StudyFragment;
import com.mobdev.sam.apprenticeapp.models.Profile;

/**
 * The Main Activity. Used as the base activity for every part of the app apart from the log in
 * screens. New fragments are placed/replaced into the content frame to change between pages.
 * <p>
 * Also contains the navigation drawer logic, that allows the user to navigate between the different
 * areas of the app from wherever they currently are.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //**** USER PROFILE! ****//
    public Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // INTENT BUNDLE PARAMS ETC
        Bundle params = getIntent().getExtras();
        profile = (Profile) params.get("profile");


        // NAVIGATION DRAWER
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu navMenu = navigationView.getMenu();
        if (!profile.getIsAdmin()) {
            navMenu.findItem(R.id.nav_admin).setVisible(false);
        }
        navigationView.setNavigationItemSelectedListener(this);

        // Welcome the user to the app
        Toast.makeText(this, "Welcome to the Apprentice app! Drag the navigation menu from the left to begin!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Bundle bundle = new Bundle();
        bundle.putLong("userId", profile.getId());

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Instantiate Fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_study) {
            // Switch the current fragment to the 'Study' Fragment
            StudyFragment studyFragment = new StudyFragment();
            studyFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, studyFragment)
                    .commit();
        } else if (id == R.id.nav_social) {
            // Switch the current fragment to the 'Social' Fragment
            SocialFragment socialFragment = new SocialFragment();
            socialFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, socialFragment)
                    .commit();
        } else if (id == R.id.nav_profile) {
            bundle.putBoolean("owner", true);
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.setArguments(bundle);
            // Switch the current fragment to the 'Profile' Fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, profileFragment)
                    .commit();
        } else if (id == R.id.nav_admin) {
            // Switch the current fragment to the 'Admin' Fragment
            AdminFragment adminFragment = new AdminFragment();
            adminFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, adminFragment)
                    .commit();
        } else if (id == R.id.nav_logout) {
            // Launch the Log In Activity
            // Start a new main activity and pass the new profile
            final Intent intent = new Intent(this, LogInActivity.class);

            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Set Title
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
