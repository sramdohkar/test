/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ss899.homework09;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,SignUpFragment.OnFragmentInteractionListener,
        ContactsFragment.OnFragmentInteractionListener,ConversationsFragment.OnFragmentInteractionListener,ItemsAdapter.ItemsInterface, EditProfileFragment.OnFragmentInteractionListener, ArchiveFragment.OnFragmentInteractionListener,ViewMessagesFragment.OnFragmentInteractionListener{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout DrawerLinear;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mOptionsTitles;
    Firebase firebase, firebase1;
    String email1;
    User currentUser;


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Log.d("demologin", "Logged in");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        firebase=new Firebase("https://blinding-inferno-3349.firebaseio.com/");
//        firebase.unauth();
        if (firebase.getAuth() != null) {
            Toast.makeText(MainActivity.this, "User Logged In", Toast.LENGTH_SHORT).show();
            moveToConversations();
        }else{
            moveToLogin();
        }


        DrawerLinear= (LinearLayout) findViewById(R.id.drawerLinear);
        mTitle = mDrawerTitle = getTitle();
        mOptionsTitles = getResources().getStringArray(R.array.options_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mOptionsTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_menu_black_24dp);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);



    }






    private void moveToLogin() {
        setTitle("Stay in Touch (Login)");
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new LoginFragment(), "tag_loginfragment")
                .addToBackStack(null)
                .commit();
    }

    private void moveToConversations() {
            setTitle("Conversations Activity");
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ConversationsFragment(), "tag_conversationfragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        final boolean drawerOpen = mDrawerLayout.isDrawerOpen(DrawerLinear);
//        menu.findItem(R.id.action_menu).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
      /*  switch(item.getItemId()) {
            case R.id.action_menu:
                mDrawerToggle.onDrawerOpened(DrawerLinear);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void moveToConversation() {
            setTitle("Conversations Activity");
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ConversationsFragment(), "tag_conversationfragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void moveToSignUp() {
        setTitle("Stay in Touch (Sign Up)");
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SignUpFragment(), "tag_signupfragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void returnToLogin() {
        setTitle("Stay in Touch (Login)");
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new LoginFragment(), "tag_loginfragment")
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void sendUserDetails(User currentUser, User user) {
        ViewMessagesFragment vf = new ViewMessagesFragment();
        vf.getContactDetails(currentUser, user);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, vf, "tag_viewmessagefragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sendContactDetails(User user) {
        setTitle("Contacts Activity");
        ViewContactFragment vcf = new ViewContactFragment();
        vcf.getUserDetails(user);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, vcf, "tag_viewcontactfragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void moveToViewMessage(User user,User currentUser) {
        ViewMessagesFragment vf = new ViewMessagesFragment();
        vf.getContactDetails(currentUser, user);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, vf, "tag_viewmessagefragment1")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void moveToViewMessageFrag(User user, User currentUser) {
        ViewMessagesFragment vf = new ViewMessagesFragment();
        vf.getContactDetails(currentUser, user);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, vf, "tag_viewmessagefragment2")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void displayUpdatedContent(User user, User currentuser) {
        ViewMessagesFragment vf = new ViewMessagesFragment();
        vf.getContactDetails(currentuser, user);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, vf, "tag_viewmessagefragment3")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void displaylatestmessages(User user,User currentuser) {
        ViewMessagesFragment vf = new ViewMessagesFragment();
        vf.getContactDetails(currentuser, user);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, vf, "tag_viewmessagefragment4")
                .addToBackStack(null)
                .commit();
    }


    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        switch(position){
            case 0:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new ContactsFragment()).commit();
                break;
            case 1:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new ConversationsFragment()).commit();
                break;
            case 2:
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new ArchiveFragment()).commit();
                break;
            case 3:
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new EditProfileFragment()).commit();
                break;
            case 4:
                firebase.unauth();
                getFragmentManager().beginTransaction().replace(R.id.content_frame,new LoginFragment()).commit();
                break;
            case 5:
                firebase.unauth();
                finishAffinity();
                break;
            default:
                break;
        }


        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);

        mDrawerLayout.closeDrawer(DrawerLinear);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
//        setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        if(getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
}