package com.seoho.proto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.seoho.proto.DataClass.ContentsModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.otilla.agmeditlist.ContentData;
import kr.co.otilla.agmeditlist.EditRecyclerView;

public class WriteActivity extends AppCompatActivity {

    @BindView(R.id.et_content) EditText etContent;
    @BindView(R.id.btn_camera) ImageButton btnCamera;
    @BindView(R.id.btn_gallery) ImageButton btnGallery;
    @BindView(R.id.btn_enter) Button btnEnter;
    ArrayList<ContentData> mList =new ArrayList<>(); // 이미지 툴
    WriteAdapter writeAdapter;
    private File photoFile;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_SELECT_PHOTO = 2;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseDatabase database;
    DatabaseReference myRef;

    UserPrefer up;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);

        up = new UserPrefer(WriteActivity.this);
        pd  = new ProgressDialog(WriteActivity.this);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCamera();
            }
        });
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Write();
            }
        });

        EditRecyclerView editRecyclerView = (EditRecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(WriteActivity.this, LinearLayoutManager.VERTICAL, false);
        editRecyclerView.setLayoutManager(layoutManager);
        editRecyclerView.addItemDecoration(new DividerItemDecoration(WriteActivity.this, DividerItemDecoration.VERTICAL));
        writeAdapter = new WriteAdapter(WriteActivity.this, mList);
        writeAdapter.setEdit(true);
        editRecyclerView.setAdapter(writeAdapter);



    }

    private void getPhoto(){
        Intent gallerIntent = new Intent(Intent.ACTION_PICK);
        gallerIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        gallerIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallerIntent, REQUEST_SELECT_PHOTO);
    }

    private void getCamera() {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (photoIntent.resolveActivity(getPackageManager())!=null){

            photoFile = createImageFile();
        }

        if (photoFile != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(WriteActivity.this,"kr.co.otilla.agmhealing.fileprovider",photoFile));
                photoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }else {
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            }

            startActivityForResult(photoIntent, REQUEST_IMAGE_CAPTURE);
        }
        else {
            // 파일생성을 실패한 경우
            Toast.makeText(WriteActivity.this, "이미지 생성 실패 ! 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void Write(){

        pd.setMessage("글을 중입니다. 잠시만 기다려주세요.");
        pd.show();

        StorageReference storageRef = storage.getReference();
        StorageReference mountainsRef;

        if (etContent.getText().toString().isEmpty()){
            Toast.makeText(WriteActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else {

            final ContentsModel model = new ContentsModel();

            String image_time = new SimpleDateFormat("yyyyMMdd HHmmss", Locale.US).format(new Date());

            model.setIndex(up.getUser_nick()+image_time);
            model.setNick(up.getUser_nick());
            model.setProfile(up.getUser_image());
            model.setSummary(etContent.getText().toString());

            model.setCount_hits(0);
            model.setCount_like(0);
            model.setCount_reply(0);
            model.setDate_upload(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date()));

            if (mList.size()>0){
                // 이미지를 업로드 할 경우

                int imageCount = 0;
                for (ContentData cdata : mList){

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    cdata.Bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    mountainsRef = storageRef.child("board/"+image_time+ "_"+up.getUser_nick()+".jpg");


                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            model.setImageUrl("none.");

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            model.setImageUrl(downloadUri.toString());
                            
                            doInsert(model, true);
                        }
                    });

                    imageCount++;
                }
            } else {
                //글만 쓰는 경우

                doInsert(model, false);
            }

        }
    }

    // 실제로 파이어 베이스 db 에 인서트 하는 method
    private void doInsert(ContentsModel model, boolean isImageUpload) {

        if (isImageUpload) {
            if (model.getImageUrl().split(",").length == mList.size()) {

                Toast.makeText(getApplicationContext(),"doInsert 호출",Toast.LENGTH_SHORT).show();
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("contents");
                myRef.child(model.getIndex()).setValue(model);
                pd.dismiss();
                setResult(RESULT_OK);
                finish();
            }
        }else {
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("contents");
            myRef.push().setValue(model);
            pd.dismiss();
            setResult(RESULT_OK);
            finish();
        }

    }

    private File createImageFile() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG" + timeStamp + "_";

        File storageDir = getExternalFilesDir(null);

        try {
            return  File.createTempFile(imageFileName,".jpg",storageDir);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        ContentData cdata;

        switch (requestCode){

            case REQUEST_SELECT_PHOTO :

                Uri imageUri = intent.getData();
                cdata = new ContentData();

                try {
                    cdata.Bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Cursor c = getContentResolver().query(imageUri,null,null,null,null);
                    c.moveToNext();
                    String path = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    cdata.FileName = path;
                    mList.add(cdata);
                    writeAdapter.notifyDataSetChanged();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case  REQUEST_IMAGE_CAPTURE :
                cdata = new ContentData();

                Bitmap bmp = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                cdata.Bmp = bmp;
                cdata.FileName = photoFile.getName();
                mList.add(cdata);
                writeAdapter.notifyDataSetChanged();

                break;
        }
    }
}
