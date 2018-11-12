package com.seoho.proto.DataClass;

import java.util.Objects;

public class ContentsModel {

    String index;
    String profile;
    String nick;
    String imageUrl;
    String date_upload;
    String summary;
    int count_hits;
    int count_like;
    int count_reply;

    @Override
    public String toString() {
        return "ContentsModel{" +
                "index='" + index + '\'' +
                ", profile='" + profile + '\'' +
                ", nick='" + nick + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", date_upload='" + date_upload + '\'' +
                ", summary='" + summary + '\'' +
                ", count_hits=" + count_hits +
                ", count_like=" + count_like +
                ", count_reply=" + count_reply +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentsModel that = (ContentsModel) o;
        return count_hits == that.count_hits &&
                count_like == that.count_like &&
                count_reply == that.count_reply &&
                Objects.equals(index, that.index) &&
                Objects.equals(profile, that.profile) &&
                Objects.equals(nick, that.nick) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(date_upload, that.date_upload) &&
                Objects.equals(summary, that.summary);
    }

    @Override
    public int hashCode() {

        return Objects.hash(index, profile, nick, imageUrl, date_upload, summary, count_hits, count_like, count_reply);
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate_upload() {
        return date_upload;
    }

    public void setDate_upload(String date_upload) {
        this.date_upload = date_upload;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getCount_hits() {
        return count_hits;
    }

    public void setCount_hits(int count_hits) {
        this.count_hits = count_hits;
    }

    public int getCount_like() {
        return count_like;
    }

    public void setCount_like(int count_like) {
        this.count_like = count_like;
    }

    public int getCount_reply() {
        return count_reply;
    }

    public void setCount_reply(int count_reply) {
        this.count_reply = count_reply;
    }

    public ContentsModel(String index, String profile, String nick, String imageUrl, String date_upload, String summary, int count_hits, int count_like, int count_reply) {
        this.index = index;
        this.profile = profile;
        this.nick = nick;
        this.imageUrl = imageUrl;
        this.date_upload = date_upload;
        this.summary = summary;
        this.count_hits = count_hits;
        this.count_like = count_like;
        this.count_reply = count_reply;
    }

    public ContentsModel(){

    }
}
