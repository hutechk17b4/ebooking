package com.example.ungdungdatvexemphim.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ungdungdatvexemphim.Models.SessionManagement;
import com.example.ungdungdatvexemphim.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    //AppCompatActivity
    // Khai báo để ánh xạ
    // TabLayout là cái giúp màn hình di chuyển qua trái, phải
    // Tạo cái xô chứa TabLayout
    TabLayout tabLayout;

    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.nav_find);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        // thêm tiêu đề vào TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Now Playing"));
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        // đặt trọng lực fill đầy màn hình
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // Ánh xạ

        pagerAdapter = new com.example.ungdungdatvexemphim.Adapters.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        // sự kiện chuyển trang
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    //==============================================================================

    @Override
    public void onBackPressed() {


        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("LOGOUT!");
        alertdialog.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        alertdialog.setMessage("Are you sure for logout this app ?");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SessionManagement sessionManagement=new SessionManagement(MainActivity.this);
                sessionManagement.removeSessionMail();
                sessionManagement.removeSessionUsername();
                sessionManagement.removeSession();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);

                //  int ID=sessionManagement.getSession();
                int ID=sessionManagement.getSession();
                String name=sessionManagement.getSessionUsername();
                String mail=sessionManagement.getSessionMail();
                Toast.makeText(MainActivity.this,mail+" "+name+""+ID,Toast.LENGTH_LONG).show();

            }
        });
        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertdialog.show();
        super.onBackPressed();
    }

    //==============================================================================

    // Xử lý sự kiện lắng nghe khi click vào icon trên Bottom Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch(menuItem.getItemId()){
                        case R.id.nav_home_page:
                            Intent intent1 = new Intent(MainActivity.this, HomePageActivity.class);
                            startActivity(intent1);
                            return  true;
                        case R.id.nav_store:
                            Intent intent2 = new Intent(MainActivity.this, StoreActivity.class);
                            startActivity(intent2);

                            return  true;
                        case R.id.nav_find:
                            Intent intent3 = new Intent(MainActivity.this, FindActivity.class);
                            startActivity(intent3);
                            return  true;
                        case R.id.nav_account:
                            Intent intent5 = new Intent(MainActivity.this, AccountActivity.class);
                            startActivity(intent5);
                            return  true;
                    }
                    return false;
                }
            };




    /*public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    }

