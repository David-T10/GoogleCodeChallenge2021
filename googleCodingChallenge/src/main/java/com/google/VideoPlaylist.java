package com.google;

import java.util.ArrayList;

/** A class used to represent a Playlist */
class VideoPlaylist {
    private String name;
    private ArrayList<Video> videos;

    public VideoPlaylist(String name, ArrayList<Video> videos){
        this.name = name;
        this.videos = videos;
    }

    public void addVideoToPlaylist(Video v){
        if(this.videos == null){
            this.videos = new ArrayList<>();
        }
        this.videos.add(v);
    }

    public void removeVideoFromPlaylist(Video v){
        if(this.videos == null){
            this.videos = new ArrayList<>();
        }
        this.videos.remove(v);
    }

    public String getName() {
        return name;
    }
    public ArrayList<Video> getVideos() {
        return videos;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
}
