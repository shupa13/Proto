package com.seoho.proto;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.seoho.proto.fragment.FeedFragment;
import com.seoho.proto.fragment.HomeFragment;
import com.seoho.proto.fragment.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.s64.android.navigationbarview.bottom.BottomNavigationBarView;
import jp.s64.android.navigationbarview.view.INavigationBarView;

public class MainActivity extends LetMenuActivity {


    @BindView(R.id.bottom_bar) BottomNavigationBarView bottomNavigationBarView;
    @BindView(R.id.toolbar) AppBarLayout toolbar;

    private HomeFragment homeFragment;
    private FeedFragment feedFragment;
    private ProfileFragment profileFragment;

   // FloatingActionButton writeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        initFragment();

        MenuItem homeItem = new MenuItem(R.mipmap.home, 1231, "Home", true);
        MenuItem feedItem  = new MenuItem(R.mipmap.feed, 1233, "Feed", false);
        MenuItem profileItem = new MenuItem(R.mipmap.profile, 1235, "Profile", false);

        bottomNavigationBarView.add(homeItem,feedItem,profileItem);
        bottomNavigationBarView.setItemWidthFixed(true);
        bottomNavigationBarView.setOnCheckChangedListener(new INavigationBarView.OnCheckChangeListener() {
            @Override
            public void onCheckChanged(int oldIdRes, int newIdRes) {

                FragmentTransaction ft = hideAll();

                switch (newIdRes){

                    case 1231:
                        ft.show(homeFragment);
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                    case 1233:
                        ft.show(feedFragment);
                        toolbar.setVisibility(View.GONE);
                        break;
                    case 1235:
                        ft.show(profileFragment);
                        toolbar.setVisibility(View.GONE);
                        break;
                }
                ft.commitAllowingStateLoss();
            }
        });

        bottomNavigationBarView.check(homeItem.getIdRes());

        /*
        writeButton = (FloatingActionButton)findViewById(R.id.btn_write);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeIntent = new Intent(MainActivity.this, WriteActivity.class);
                startActivityForResult(writeIntent, 300);
            }
        });
        */

        this.setStateLoginChecker(new LogEventListener() {
            @Override
            public void loginStateCheck() {
                if (firebaseAuth.getCurrentUser() != null){
                 //   writeButton.setVisibility(View.VISIBLE);
                }else {
                  //  writeButton.setVisibility(View.INVISIBLE);
                }
            }
        });
        CheckWriteButton();
    }

    private void CheckWriteButton() {
        if (firebaseAuth.getCurrentUser() != null){
         //   writeButton.setVisibility(View.VISIBLE);
        }else {
         //   writeButton.setVisibility(View.INVISIBLE);
        }
    }

    private void initFragment(){

        homeFragment = new HomeFragment();
        feedFragment = new FeedFragment();
        profileFragment = new ProfileFragment();

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .replace(R.id.fl_main, homeFragment)
                .replace(R.id.fl_feed, feedFragment)
                .replace(R.id.fl_profile, profileFragment)
                .hide(feedFragment)
                .hide(profileFragment)
                .commitAllowingStateLoss();

    }
    private FragmentTransaction hideAll(){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(homeFragment);
        ft.hide(feedFragment);
        ft.hide(profileFragment);

        return  ft;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 300){
            finish();
            startActivity(getIntent());
        }
    }
}
