
package com.example.admincollageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.admincollageapp.Login.EntryActivity;
import com.example.admincollageapp.Login.UserLoginActivity;
import com.example.admincollageapp.databinding.ActivityMain2Binding;
import com.example.admincollageapp.ebook.EbookActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity  implements  NavigationView.OnNavigationItemSelectedListener{

    ActivityMain2Binding binding;

    private ActionBarDrawerToggle drawerToggle;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
         if(auth.getCurrentUser() == null){
             startActivity(new Intent(MainActivity2.this,UserLoginActivity.class));
         }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,R.id.navigation_notice , R.id.navigation_faculty,
                R.id.navigation_gallery,R.id.navigation_about )
                .build();
        NavController navController = Navigation.findNavController(this, R.id.frame_layout);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        // if we directly write hardcoded string in start and close than it gave error
        drawerToggle = new ActionBarDrawerToggle(this,binding.drawerlayout,R.string.start,R.string.close);

        binding.drawerlayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //showing navigation in toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        binding.navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main_activity2_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        drawerToggle.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.logoutMenuOption){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity2.this, UserLoginActivity.class));
            finish();
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull  MenuItem item) {

        switch(item.getItemId()){
            case R.id.navigation_videolecture:
                Toast.makeText(this, "Video lecture Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_ebook:
                Intent intent = new Intent(MainActivity2.this, EbookActivity.class);
                startActivity(intent);
                break;
            case R.id. navigation_theme:
                Toast.makeText(this, "theme Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_website:
                Toast.makeText(this, "website Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_share:
                Toast.makeText(this, "share Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_rateus:
                Toast.makeText(this, "rateus Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_developer:
                Toast.makeText(this, "developer Clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        /**
         *  here we check is drawer is open id yes than on pressing back button
         *  first close drawer than get back from activity
         */

        if(binding.drawerlayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerlayout.closeDrawer(GravityCompat.START);
        }else{
            startActivity(new Intent(MainActivity2.this, EntryActivity.class));

        }

    }

    @Override
    protected void onStart() {
       if(binding.drawerlayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerlayout.closeDrawer(GravityCompat.START);
        }
        super.onStart();
    }

}