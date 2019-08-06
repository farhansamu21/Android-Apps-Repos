package com.prodalang1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prodalang1.R;
import com.prodalang1.fragments.cart;
import com.prodalang1.fragments.diy;
import com.prodalang1.fragments.home;
import com.prodalang1.fragments.you;
import com.prodalang1.helper.BottomNavigationBehavior;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_main);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //ataching bottom sheet behaviour - hide / show on scroll
        /*CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());*/

        // load the home fragment by default
        loadFragment(new home());
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.bottom_navigation_item_home:
                    /*toolbar.setTitle("Home");*/
                    fragment = new home();
                    loadFragment(fragment);
                    return true;
                case R.id.bottom_navigation_item_diy:
                    /*toolbar.setTitle("DIY");*/
                    fragment = new diy();
                    loadFragment(fragment);
                    return true;
                case R.id.bottom_navigation_item_cart:
                    /*toolbar.setTitle("Cart");*/
                    fragment = new cart();
                    loadFragment(fragment);
                    return true;
                case R.id.bottom_navigation_item_you:
                    /*toolbar.setTitle("Profile");*/
                    fragment = new you();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };


    /**
     * loading fragment into FrameLayout
     *
     * @param fragment
     */
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_bottom_navigation, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
