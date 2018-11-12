package com.seoho.proto.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seoho.proto.DetailActivity;
import com.seoho.proto.UserPrefer;
import com.seoho.proto.DataClass.ContentsModel;
import com.seoho.proto.R;
import com.seoho.proto.video.ThreeButtonListener;
import com.seoho.proto.video.VideoAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    ArrayList<ContentsModel> models = new ArrayList<>();

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    UserPrefer up;
    VideoAdapter videoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        firebaseAuth = FirebaseAuth.getInstance();
        up = new UserPrefer(getContext());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("contents");

        ThreeButtonListener likeListener = new ThreeButtonListener() {
            @Override
            public void onButtonClick(final String board_idx) {
                // 좋아요 이벤트
                myRef.child(board_idx).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){
                            int count_like = dataSnapshot.getValue(ContentsModel.class).getCount_like();
                            myRef.child(board_idx).child("count_like").setValue(count_like + 1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };

        ThreeButtonListener replyListener = new ThreeButtonListener() {
            @Override
            public void onButtonClick(String board_index) {
                // 댓글달기 이벤트
                Intent replyIntent = new Intent(getContext(), DetailActivity.class);
                replyIntent.putExtra("board_idx", board_index);
                startActivity(replyIntent);
            }
        };
        videoAdapter = new VideoAdapter(models, likeListener, replyListener);

        videoAdapter.setOnDeleteListener(new VideoAdapter.OnDeleteListener() {
            @Override
            public void ContentDelete(int menuId, String idx) {
                myRef.child(idx).removeValue();
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        myRef.child("1").setValue(new ContentsModel("1",up.getUser_image(),up.getUser_nick(),"https://media.giphy.com/media/4QFL4oYL5ucXQJc7YM/giphy.gif","18. 07. 16","내용1",1000000,1000,3));
        myRef.child("2").setValue(new ContentsModel("2",up.getUser_image(),up.getUser_nick(),"https://media.giphy.com/media/uWzStFbkzk5S9U7fgr/giphy.gif","18. 07. 16","내용2",2000000,2000,4));
        myRef.child("3").setValue(new ContentsModel("3",up.getUser_image(),up.getUser_nick(),"https://media.giphy.com/media/1jFoxDQ5kWPJ7pOJzT/giphy.gif","18. 07. 16","내용3",3000000,3000,5));
        myRef.child("4").setValue(new ContentsModel("4",up.getUser_image(),up.getUser_nick(),"https://media.giphy.com/media/FDBjtB6rbluWMn05fi/giphy.gif","18. 07. 16","내용4",4000000,4000,6));
        myRef.child("5").setValue(new ContentsModel("5",up.getUser_image(),up.getUser_nick(),"https://media.giphy.com/media/4ZeD8Cvi3q2EFluBxM/giphy.gif","18. 07. 16","내용5",5000000,5000,7));


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot onSnap : dataSnapshot.getChildren()){
                    ContentsModel model = onSnap.getValue(ContentsModel.class);
                    models.add(0,model);
                }
                videoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myRef.addChildEventListener(childEventListener);

    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            for (ContentsModel model : models){

                ContentsModel tempModel = dataSnapshot.getValue(ContentsModel.class);

                if (model.getIndex().equals(tempModel.getIndex())){
                    model.setProfile(tempModel.getProfile());
                    model.setNick(tempModel.getNick());
                    model.setImageUrl(tempModel.getImageUrl());
                    model.setCount_hits(tempModel.getCount_hits());
                    model.setCount_like(tempModel.getCount_like());
                    model.setCount_reply(tempModel.getCount_reply());
                    model.setDate_upload(tempModel.getDate_upload());
                    model.setSummary(tempModel.getSummary());

                }
            }
            videoAdapter.notifyDataSetChanged();

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
    };


}
