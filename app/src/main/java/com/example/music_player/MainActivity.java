package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();
    SearchFragment searchFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout_container, homeFragment)
                                .commit();
                        return true;

                    case R.id.favorite:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout_container, favoriteFragment)
                            .commit();
                    return true;

                    case R.id.search:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout_container, searchFragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });

    }
}