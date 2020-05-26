package com.example.chatappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager main_ViewPager;
    private TabLayout main_TabLayout;
    private Main_Tab_Adapter main_tab_adapter;

    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("MeChat");

        main_ViewPager = findViewById(R.id.main_tabs_pager);
        main_tab_adapter = new Main_Tab_Adapter(getSupportFragmentManager());
        main_ViewPager.setAdapter(main_tab_adapter);

        main_TabLayout = findViewById(R.id.main_tabs);
        main_TabLayout.setupWithViewPager(main_ViewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser == null) {
            SendUserToLoginActivity();
        }
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, Dangnhap_Dangky_Activity.class);
        startActivity(loginIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout_option) {
            firebaseAuth.signOut();
            SendUserToLoginActivity();
        }
        if (item.getItemId() == R.id.setting_option) {

        }
        if (item.getItemId() == R.id.find_friends_option) {

        }
        return true;
    }
}
