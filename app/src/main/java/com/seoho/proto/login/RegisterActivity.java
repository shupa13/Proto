package com.seoho.proto.login;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.seoho.proto.R;
import com.seoho.proto.UserPrefer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.user_email) EditText userEmail;
    @BindView(R.id.user_password) EditText userPassword;
    @BindView(R.id.user_passwrd_re) EditText userPasswordRe;
    @BindView(R.id.user_name) EditText userName;
    @BindView(R.id.user_age) EditText userAge;
    @BindView(R.id.btn_register) Button btnRegister;
    private FirebaseAuth mAuth;
    ProgressDialog pd;
    UserPrefer up;

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        up = new UserPrefer(RegisterActivity.this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });

    }

    private void userRegister() {
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();
        pd = new ProgressDialog(RegisterActivity.this);
        pd.setMessage("회원가입 진행중입니다.");
        pd.show();

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setResult(RESULT_OK);
                            if (!email.isEmpty()) {
                                up.setUser_email(email);
                                up.setUser_nick(email.split("@")[0]);
                            }
                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
