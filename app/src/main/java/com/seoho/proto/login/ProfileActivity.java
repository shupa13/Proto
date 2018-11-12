package com.seoho.proto.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.seoho.proto.R;
import com.seoho.proto.UserPrefer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.btn_save) Button btnSave;
    @BindView(R.id.btn_cancel) Button btnCancel;
    @BindView(R.id.iv_image) ImageView ivImage;
    @BindView(R.id.et_nickname) EditText etNickname;
    ArrayList<ImageView> profile = new ArrayList<>();

    FirebaseStorage storage = FirebaseStorage.getInstance();

    UserPrefer up;
    ProgressDialog pd;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        pd = new ProgressDialog(ProfileActivity.this);
        up = new UserPrefer(ProfileActivity.this);

        Glide.with(ProfileActivity.this)
                .load(up.getUser_image())
                .into(ivImage);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(Intent.ACTION_PICK);
                profileIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                profileIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(profileIntent,200);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Save();
            }
        });
    }

    private void Save() {

        String nick = etNickname.getText().toString();
        pd.setMessage("프로필 업데이트 진행중");
        pd.show();
        up.setUser_nick(nick);
        user = FirebaseAuth.getInstance().getCurrentUser();

        StorageReference storageRef = storage.getReference();
        StorageReference mountainsRef = storageRef.child("profile/"+up.getUser_nick()+".jpg");

        // Get the data from an ImageView as bytes
        ivImage.setDrawingCacheEnabled(true);
        ivImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUri = taskSnapshot.getDownloadUrl();
                up.setUser_image(downloadUri.toString());
                up.setUser_nick(etNickname.getText().toString());

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(etNickname.getText().toString())
                        .setPhotoUri(downloadUri)
                        .build();


                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    setResult(RESULT_OK);
                                    Toast.makeText(getBaseContext(), "프로필 업데이트 성공", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getBaseContext(), "프로필 업데이트 실패", Toast.LENGTH_SHORT).show();
                                }
                                pd.dismiss();
                            }
                        });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Uri imageUri = intent.getData();
        Bitmap bitmap = null;

        if (requestCode == 200){
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri );
                ivImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
