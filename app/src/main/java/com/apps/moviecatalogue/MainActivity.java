package com.apps.moviecatalogue;

import android.os.Bundle;

import com.apps.moviecatalogue.ui.bookmark.BookmarkFragment;
import com.apps.moviecatalogue.ui.movie.MovieFragment;
import com.apps.moviecatalogue.ui.tvShow.TvshowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private final String SELECTED_MENU = "selected_menu";
    private BottomNavigationView navView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        Fragment fragment = null;
        if (item.getItemId() == R.id.navigation_home) {
            fragment = MovieFragment.newInstance();
        } else if (item.getItemId() == R.id.navigation_dashboard) {
            fragment = TvshowFragment.newInstance();
        }else if (item.getItemId() == R.id.navigation_notifications) {
            fragment = BookmarkFragment.newInstance();
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
        }
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState != null) {
            savedInstanceState.getInt(SELECTED_MENU);
        } else {
            navView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_MENU, navView.getSelectedItemId());
    }

}
