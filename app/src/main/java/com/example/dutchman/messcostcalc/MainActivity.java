package com.example.dutchman.messcostcalc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private HomeFragment homeFragment;
    private FloatingActionButton fab;

    private boolean isMembersFrag = true,isHomeFrag = false;

    private DBHandler handler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        context = this;


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_add_white));
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Click action


                    if(isMembersFrag){
                        isMembersFrag = false;
                        isHomeFrag = true;

                        //android.R.drawable.ic_menu_close_clear_cancel


                        fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_close_white));

                        BazarFragment bazarFragment = new BazarFragment();
                        bazarFragment.setContext(context);

                        manager = getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.rlContent,bazarFragment,bazarFragment.getTag())
                                .commit();
                        //fab.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);



                    } else if(isHomeFrag){

                        isMembersFrag = true;
                        isHomeFrag = false;

                        fab.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_add_white));

                        homeFragment = new HomeFragment();
                        homeFragment.setContext(context);

                        manager = getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.rlContent,homeFragment,homeFragment.getTag()).commit();
                        //fab.setImageResource(android.R.drawable.ic_input_add);
                    }

                }
            });
        }


        homeFragment = new HomeFragment();

        homeFragment.setContext(context);

        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.rlContent,homeFragment,homeFragment.getTag()).commit();


    }



    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }

        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
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

            SettingsFragment settingsFragment = new SettingsFragment();

            settingsFragment.setContext(context);

            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent,settingsFragment,settingsFragment.getTag()).commit();

            return true;
        } else if(id == R.id.action_about){

            AboutFragment aboutFragment = new AboutFragment();

            aboutFragment.setContext(context);

            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent,aboutFragment,aboutFragment.getTag()).commit();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Toast.makeText(MainActivity.this,"Under construction",Toast.LENGTH_SHORT).show();

        if (id == R.id.nav_home) {

            homeFragment = new HomeFragment();
            homeFragment.setContext(context);

            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent,homeFragment,homeFragment.getTag()).commit();

        } else if(id == R.id.nav_addMember){

            AddMembersFragment addMembersFragment = new AddMembersFragment();
            addMembersFragment.setContext(context);

            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent,addMembersFragment,addMembersFragment.getTag())
                    .commit();

        } else if (id == R.id.nav_add_meal) {


            AddMealFragment addMealFragment = new AddMealFragment();

            addMealFragment.setContext(context);

            manager = getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.rlContent, addMealFragment, addMealFragment.getTag()).commit();


        } else if (id == R.id.nav_meal_credit) {


            CreditFragment creditFragment = new CreditFragment();

            creditFragment.setDest(context, "meal");

            manager = getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.rlContent, creditFragment, creditFragment.getTag()).commit();



        } else if (id == R.id.nav_meal_debit) {

            DebitFragment debitFragment = new DebitFragment();
            debitFragment.setDest(context, "meal");

            manager = getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.rlContent, debitFragment, debitFragment.getTag()).commit();

        } else if(id == R.id.nav_meal_history){


            MealHistoryFragment mealHistoryFragment = new MealHistoryFragment();

            mealHistoryFragment.setContext(context);

            manager = getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.rlContent, mealHistoryFragment, mealHistoryFragment.getTag()).commit();



        } else if (id == R.id.nav_rent_per_credit) {

            CreditFragment creditFragment = new CreditFragment();

            creditFragment.setDest(context, "rent");

            manager = getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.rlContent, creditFragment, creditFragment.getTag()).commit();


        } else if (id == R.id.nav_rent_per_debit) {

            DebitFragment debitFragment = new DebitFragment();
            debitFragment.setDest(context, "rent");

            manager = getSupportFragmentManager();

            manager.beginTransaction().replace(R.id.rlContent, debitFragment, debitFragment.getTag()).commit();

        } else if (id == R.id.nav_rent_cost) {

            CostFragment costFragment = new CostFragment();
            costFragment.setContext(context);
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent, costFragment, costFragment.getTag()).commit();

        } else if(id == R.id.nav_rent_history){


            RentHistoryFragment rentHistoryFragment = new RentHistoryFragment();
            rentHistoryFragment.setContext(context);
            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rlContent, rentHistoryFragment, rentHistoryFragment.getTag()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
