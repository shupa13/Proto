package com.seoho.proto.video;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.seoho.proto.DataClass.ContentsModel;
import com.seoho.proto.R;
import com.seoho.proto.UserPrefer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    ArrayList<ContentsModel> models = new ArrayList<>();

    ThreeButtonListener likeListener;
    ThreeButtonListener replyListener;

    OnDeleteListener deleteListener;

    UserPrefer up;

    public interface OnDeleteListener{
        void ContentDelete(int menuId, String idx);

    }
    public void setOnDeleteListener(OnDeleteListener deleteListener){
        this.deleteListener = deleteListener;
    }


    public VideoAdapter(ArrayList<ContentsModel> models, ThreeButtonListener likeListener, ThreeButtonListener replyListener) {
        this.models = models;
        this.likeListener = likeListener;
        this.replyListener = replyListener;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_contents, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
      //  holder.bindItem(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {


        ContentsModel model;


        public VideoViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            up = new UserPrefer(itemView.getContext());


            // 게시물 삭제 이벤트
            /*
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(itemView.getContext(), btnAdd);
                    popup.inflate(R.menu.list_content_meu);

                    if (model.getNick().equals(up.getUser_nick())) {
                        popup.getMenu().findItem(R.id.menu_content_delete).setVisible(true);
                        popup.getMenu().findItem(R.id.menu_content_revise).setVisible(true);
                        popup.getMenu().findItem(R.id.menu_content_report).setVisible(false);
                    } else {
                        popup.getMenu().findItem(R.id.menu_content_delete).setVisible(false);
                        popup.getMenu().findItem(R.id.menu_content_revise).setVisible(false);
                        popup.getMenu().findItem(R.id.menu_content_report).setVisible(true);
                    }
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.menu_content_delete :
                                    //삭제처리
                                    new AlertDialog.Builder(itemView.getContext())
                                            .setMessage("게시물을 삭제하시겠습니까 ?")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // 삭제 확정
                                                    if (deleteListener != null) {
                                                        deleteListener.ContentDelete(R.id.menu_content_delete, model.getIndex());
                                                    }
                                                }
                                            })
                                    .setNegativeButton("No", null)
                                    .show();
                                    break;

                                case R.id.menu_content_revise :
                                    //수정처리
                                    break;

                                case R.id.menu_content_report :
                                    //신고처리
                                    break;

                                default :
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            });
        }
        */
         /*
        public void bindItem(ContentsModel model){
            this.model = model;

            Glide.with(itemView.getContext()).load(model.getProfile()).apply(new RequestOptions().placeholder(R.drawable.noperson).centerCrop().dontTransform()).into(contents_profile);
            Glide.with(itemView.getContext()).load(model.getImageUrl()).apply(new RequestOptions().placeholder(R.drawable.blackpink).centerCrop().dontTransform()).into(contents_video);


            contents_nick.setText(model.getNick());
            count_hits.setText("조회수 : "+model.getCount_hits());
            count_like.setText("·  좋아요 : "+model.getCount_like());
            count_reply.setText("댓글 : "+model.getCount_reply());
            tv_summary.setText(model.getSummary());
            contents_date.setText(model.getDate_upload());
            tv_summary.setText(model.getSummary());
            */
        }
    }
}
