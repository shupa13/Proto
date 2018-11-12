package com.seoho.proto;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seoho.proto.DataClass.ContentsModel;
import com.seoho.proto.DataClass.ReplyModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_reply) TextView tvReply;
    String board_idx;
    FirebaseDatabase database;
    DatabaseReference myRef;

    // 댓글부분
    @BindView(R.id.et_insertmessage) EditText etInserMessage;
    @BindView(R.id.btn_send) ImageButton btnSend;
    @BindView(R.id.lv_reply) ListView lvReply;

    ReplyAdapter replyAdapter;
    UserPrefer up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(DetailActivity.this);

        up = new UserPrefer(DetailActivity.this);

        board_idx = getIntent().getStringExtra("board_idx");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        setDetailListen();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 대화 보내는 자리
                ReplyModel replyMessage = new ReplyModel();

                String replyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());

                replyMessage.setIdx(replyTime);
                replyMessage.setReg_user_profile(up.getUser_image());
                replyMessage.setReg_user_nick(up.getUser_nick());
                replyMessage.setReply_content(etInserMessage.getText().toString());
                replyMessage.setDate(replyTime);

                myRef.child("reply").child(board_idx).push().setValue(replyMessage);
                etInserMessage.setText("");

                myRef.child("contents").child(board_idx).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){
                            int count_reply = dataSnapshot.getValue(ContentsModel.class).getCount_reply();
                            myRef.child("contents").child(board_idx).child("count_reply").setValue(count_reply + 1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        
        replyAdapter = new ReplyAdapter(DetailActivity.this,new ArrayList<ReplyModel>());
        lvReply.setAdapter(replyAdapter);
        setReplyListen();

    }

    private void setReplyListen() {
        myRef.child("reply").child(board_idx).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.hasChildren()){
                    ReplyModel ownData = new ReplyModel();
                    ReplyModel rvData = dataSnapshot.getValue(ReplyModel.class);

                    ownData.setReg_user_nick(rvData.getReg_user_nick());
                    ownData.setReg_user_profile(rvData.getReg_user_profile());
                    ownData.setReply_content(rvData.getReply_content());
                    ownData.setDate(rvData.getDate());
                    ownData.setMe(rvData.getReg_user_nick().equals(up.getUser_nick()));

                    replyAdapter.add(ownData);
                    replyAdapter.notifyDataSetChanged();
                    lvReply.setSelection(lvReply.getCount()-1); // 스크롤을 항상 아래로
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setDetailListen() {

        myRef.child("contents").orderByChild("index").equalTo(board_idx).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot onSnap : dataSnapshot.getChildren()){

                    ContentsModel model = onSnap.getValue(ContentsModel.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
