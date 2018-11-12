package com.seoho.proto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.seoho.proto.login.LoginActivity;
import com.seoho.proto.login.ProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LetMenuActivity extends AppCompatActivity {

    @BindView(R.id.menu_drawerLayout) DrawerLayout menu_drawLayout;
    @BindView(R.id.menu_navigation) NavigationView menu_navigation;
    FirebaseAuth firebaseAuth;

    ImageView header_image;
    TextView header_profile;
    UserPrefer up;

    LogEventListener lListener;

    public interface LogEventListener{
        void loginStateCheck();
    }

    public void setStateLoginChecker(LogEventListener lListener){
        this.lListener = lListener;
    }

    @Override
    public void setContentView(int layoutID) {
        super.setContentView(R.layout.activity_let_menu);

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.menu_framlayout);
        LayoutInflater.from(this).inflate(layoutID, viewGroup, true);
        getNavigation();
        up = new UserPrefer(LetMenuActivity.this);
        
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            LoginText(true);
        }
        
    }

    private void LoginText(boolean isLogin) {

        MenuItem itemLogin = menu_navigation.getMenu().findItem(R.id.menu_login);
        MenuItem itemMyinfo = menu_navigation.getMenu().findItem(R.id.menu_myinfo);

        if (isLogin){
            itemLogin.setTitle("로그아웃");
            itemMyinfo.setVisible(true);
            header_profile.setText(up.getUser_nick().toString());
            Glide.with(LetMenuActivity.this)
                    .load(up.getUser_image())
                    .into(header_image);
        }else {
            itemLogin.setTitle("로그인");
            itemMyinfo.setVisible(false);
            header_profile.setText("로그인이 필요합니다.");
            header_profile.setText(up.getUser_nick().toString());
            Glide.with(LetMenuActivity.this)
                    .load(R.drawable.noperson)
                    .into(header_image);

        }
        if (lListener != null){
            lListener.loginStateCheck();
        }
    }

    private void getNavigation() {
        ButterKnife.bind(this);
        menu_navigation.setNavigationItemSelectedListener(naviListener);

        View headerView = menu_navigation.getHeaderView(0);
        header_image = (ImageView) headerView.findViewById(R.id.header_image);
        header_profile = (TextView) headerView.findViewById(R.id.header_profile);

    }

    public void drawoOpen() {
        if (menu_drawLayout!=null) {
            menu_drawLayout.openDrawer(Gravity.LEFT);
        }
    }

    NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){

                case R.id.menu_login :
                    if (item.getTitle().equals("로그인")) {
                        Intent loginIntent = new Intent(LetMenuActivity.this, LoginActivity.class);
                        startActivityForResult(loginIntent, 101);
                    }else
                    {
                        firebaseAuth.signOut();
                        Toast.makeText(LetMenuActivity.this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                        LoginText(false);
                    }
                    menu_drawLayout.closeDrawer(Gravity.LEFT);
                    break;

                case R.id.menu_myinfo :
                    Intent profileIntent = new Intent(LetMenuActivity.this, ProfileActivity.class);
                    startActivityForResult(profileIntent, 102);
                    menu_drawLayout.closeDrawer(Gravity.LEFT);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 101) {
                LoginText(true);
            }

            if (requestCode == 102){
                LoginText(true);
            }
        }
    }
}
