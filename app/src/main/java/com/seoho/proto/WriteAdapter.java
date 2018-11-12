package com.seoho.proto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.otilla.agmeditlist.ContentData;
import kr.co.otilla.agmeditlist.EditAdapter;
import kr.co.otilla.agmeditlist.EditLayout;
import kr.co.otilla.agmeditlist.EditViewHolder;

public class WriteAdapter extends EditAdapter{

    public WriteAdapter(Context context, ArrayList<ContentData> list) {
        super(context, list);
    }

    @Override
    public EditViewHolder onCreateEditViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_write, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindEditViewHolder(EditViewHolder holder, int position) {
        TextView tvName = (TextView) holder.vContent;
        ImageView ivImage = (ImageView) holder.vImage;

        ContentData cdata = (ContentData)mList.get(position);

        ivImage.setImageBitmap(cdata.Bmp);
        tvName.setText(cdata.FileName);
    }

    private static class ViewHolder extends EditViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public EditLayout setEditLayout(View itemView) {
            return (EditLayout)itemView.findViewById(R.id.edit_layout);
        }

        @Override
        public View setContent(View itemView) {
            return itemView.findViewById(R.id.tv_name);
        }

        @Override
        public View setImage(View itemView) {
            return itemView.findViewById(R.id.ivImage);
        }

        @Override
        public View setPreDelete(View itemView) {
            return itemView.findViewById(R.id.fl_pre_delete);
        }

        @Override
        public View setDelete(View itemView) {
            return itemView.findViewById(R.id.fl_delete);
        }

        @Override
        public View setSort(View itemView) {
            return itemView.findViewById(R.id.fl_sort);
        }
    }
}
