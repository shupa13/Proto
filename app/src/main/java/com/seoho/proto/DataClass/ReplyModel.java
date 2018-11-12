package com.seoho.proto.DataClass;

import java.util.Objects;

public class ReplyModel {

    private String idx = "";
    private String reg_user_nick = "";
    private String reg_user_profile = "";
    private String reply_content = "";
    private String reply_image = "";
    private int count_like = 0;
    private int count_dislike = 0;
    private String date = "";
    private boolean isMe;

    @Override
    public String toString() {
        return "ReplyModel{" +
                "idx='" + idx + '\'' +
                ", reg_user_nick='" + reg_user_nick + '\'' +
                ", reg_user_profile='" + reg_user_profile + '\'' +
                ", reply_content='" + reply_content + '\'' +
                ", reply_image='" + reply_image + '\'' +
                ", count_like=" + count_like +
                ", count_dislike=" + count_dislike +
                ", date='" + date + '\'' +
                ", isMe=" + isMe +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReplyModel that = (ReplyModel) o;
        return count_like == that.count_like &&
                count_dislike == that.count_dislike &&
                isMe == that.isMe &&
                Objects.equals(idx, that.idx) &&
                Objects.equals(reg_user_nick, that.reg_user_nick) &&
                Objects.equals(reg_user_profile, that.reg_user_profile) &&
                Objects.equals(reply_content, that.reply_content) &&
                Objects.equals(reply_image, that.reply_image) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idx, reg_user_nick, reg_user_profile, reply_content, reply_image, count_like, count_dislike, date, isMe);
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getReg_user_nick() {
        return reg_user_nick;
    }

    public void setReg_user_nick(String reg_user_nick) {
        this.reg_user_nick = reg_user_nick;
    }

    public String getReg_user_profile() {
        return reg_user_profile;
    }

    public void setReg_user_profile(String reg_user_profile) {
        this.reg_user_profile = reg_user_profile;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getReply_image() {
        return reply_image;
    }

    public void setReply_image(String reply_image) {
        this.reply_image = reply_image;
    }

    public int getCount_like() {
        return count_like;
    }

    public void setCount_like(int count_like) {
        this.count_like = count_like;
    }

    public int getCount_dislike() {
        return count_dislike;
    }

    public void setCount_dislike(int count_dislike) {
        this.count_dislike = count_dislike;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public ReplyModel(){
    }
}
