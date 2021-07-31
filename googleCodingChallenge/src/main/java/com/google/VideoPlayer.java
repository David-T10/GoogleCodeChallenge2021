package com.google;


import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private final ArrayList<String> vidIds = new ArrayList<>();
  private final ArrayList<String> pausedVids = new ArrayList<>();
  private final ArrayList<Video> flaggedVideos = new ArrayList<>();
  private final ArrayList<String> flagReason = new ArrayList<>();
  private final ArrayList<VideoPlaylist> videoPlaylists = new ArrayList<>();
  private final ArrayList<String> playListNames = new ArrayList<>();

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    //already sorted video in the VideoLibrary class
    System.out.println("Here's a list of all available videos: ");
    for(Video v: videoLibrary.getVideos()){
      if(flaggedVideos.contains(v)){
        System.out.println(v+" - FLAGGED (reason: "+flagReason.get(flaggedVideos.indexOf(v))+")");
      } else {
        System.out.println(v);
      }
    }
  }

  public void playVideo(String videoId) {
    ArrayList<String> tempIdList = new ArrayList<>();
    for(Video v: videoLibrary.getVideos()){
      tempIdList.add(v.getVideoId());
    }
    if(flaggedVideos.contains(videoLibrary.getVideo(videoId))){
          System.out.println("Cannot play video: Video is currently flagged " +
                  "(reason: "+flagReason.get(flaggedVideos.indexOf(videoLibrary.getVideo(videoId)))+")");
    }
    if(tempIdList.contains(videoId)&&(!flaggedVideos.contains(videoLibrary.getVideo(videoId)))){
      if (this.vidIds.size() > 0) {
        System.out.println("Stopping video: " + videoLibrary.getVideo(this.vidIds.get(this.vidIds.size() - 1)).getTitle());
      } else {
        this.vidIds.add(videoId);
      }
      pausedVids.remove(videoLibrary.getVideo(videoId).getTitle());
      System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
    } else if (!tempIdList.contains(videoId)){
      System.out.println("Cannot play video: Video does not exist");
    }
  }

  public void stopVideo() {
    if(vidIds.size() > 0) {
      System.out.println("Stopping video: " + videoLibrary.getVideo(vidIds.get(vidIds.size() - 1)).getTitle());
      vidIds.remove(vidIds.size() - 1);
    } else{
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }

  public void playRandomVideo() {
    Random randomIndex = new Random();
    int index = randomIndex.nextInt(videoLibrary.getVideos().size());
    if(vidIds.size() > 0) {
      System.out.println("Stopping video: " + videoLibrary.getVideo(vidIds.get(vidIds.size() - 1)).getTitle());
      vidIds.remove(vidIds.size() - 1);
    }
    //System.out.println("Playing video: "+ videoLibrary.getVideos().get(index).getTitle());
    if(flaggedVideos.containsAll(videoLibrary.getVideos())){
      System.out.println("No videos available");
    } else {
      playVideo(videoLibrary.getVideos().get(index).getVideoId());
    }
    //this.vidIds.add(videoLibrary.getVideos().get(index).getVideoId());
  }

  public void pauseVideo() {
    if(vidIds.size() > 0 && pausedVids.contains(videoLibrary.getVideo(vidIds.get(vidIds.size() - 1)).getTitle())){
      System.out.println("Video already paused: "+ videoLibrary.getVideo(vidIds.get(vidIds.size() - 1)).getTitle());
    } else if (vidIds.size() == 0){
      System.out.println("Cannot pause video: No video is currently playing");
    } else if (vidIds.size()>0 && !pausedVids.contains(videoLibrary.getVideo(vidIds.get(vidIds.size() - 1)).getTitle())) {
      System.out.println("Pausing video: " + videoLibrary.getVideo(vidIds.get(vidIds.size() - 1)).getTitle());
      pausedVids.add(videoLibrary.getVideo(vidIds.get(vidIds.size() - 1)).getTitle());
    }
  }

  public void continueVideo() {
    if(this.vidIds.size() == 0){
      System.out.println("Cannot continue video: No video is currently playing");
    }
    else if(pausedVids.size() == 0){
      System.out.println("Cannot continue video: Video is not paused");
    } else{
      System.out.println("Continuing video: "+pausedVids.get(pausedVids.size()-1));
      pausedVids.remove(pausedVids.get(pausedVids.size()-1));
    }
  }

  public void showPlaying() {
    if(this.vidIds.size() > 0) {
      Video currentVid = videoLibrary.getVideo(this.vidIds.get(this.vidIds.size() - 1));
      if(pausedVids.contains(currentVid.getTitle())) {
        System.out.println("Currently playing: " + currentVid+" - PAUSED");
      }
      else{
        System.out.println("Currently playing: "+ currentVid);
      }
    } else {
      System.out.println("No video is currently playing");
    }
  }

  public void createPlaylist(String playlistName) {
    VideoPlaylist vP = new VideoPlaylist(playlistName, null);
    if(playListNames.isEmpty()){
      System.out.println("Successfully created new playlist: " + playlistName);
      playListNames.add(vP.getName());
      videoPlaylists.add(vP);
    } else {
      for (String p : playListNames) {
        if (p.equalsIgnoreCase(vP.getName())) {
          System.out.println("Cannot create playlist: A playlist with the same name already exists");
        } else {
          System.out.println("Successfully created new playlist: " + playlistName);
          playListNames.add(vP.getName());
          videoPlaylists.add(vP);
        }
        break;
      }
    }
    }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    ArrayList<String> tempIdList = new ArrayList<>();
    for(Video v: videoLibrary.getVideos()){
      tempIdList.add(v.getVideoId());
    }
    if(playListNames.isEmpty()){
      System.out.println("Cannot add video to "+playlistName+": Playlist does not exist");
    }else if(flaggedVideos.contains(videoLibrary.getVideo(videoId))){
      System.out.println("Cannot add video to "+playlistName+": Video is currently flagged " +
              "(reason: "+flagReason.get(flaggedVideos.indexOf(videoLibrary.getVideo(videoId)))+")");
    } else {
      for (String p : playListNames) {
        if (p.equalsIgnoreCase(playlistName)) {
          if (!tempIdList.contains(videoId)) {
            System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
          } else {
            for (VideoPlaylist vP : videoPlaylists) {
              if (playlistName.equalsIgnoreCase(vP.getName())) {
                if (vP.getVideos() == null) {
                  vP.addVideoToPlaylist(videoLibrary.getVideo(videoId));
                  System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
                } else {
                    if (!vP.getVideos().contains(videoLibrary.getVideo(videoId))) {
                      vP.addVideoToPlaylist(videoLibrary.getVideo(videoId));
                      System.out.println("Added video to " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
                    } else {
                      System.out.println("Cannot add video to " + playlistName + ": Video already added");
                    }
                  }
                }
              }
            }
        } else {
          System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
        }
        break;
      }
    }
  }

  public void showAllPlaylists() {
    if(playListNames.isEmpty()){
      System.out.println("No playlists exist yet");
    } else {
      Collections.sort(playListNames);
      System.out.println("Showing all playlists:");
      for (String name : playListNames) {
        System.out.println(name);
      }
    }
  }

  public void showPlaylist(String playlistName) {
    if (playListNames.isEmpty()) {
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    } else {
      for (String p : playListNames) {
        if (p.equalsIgnoreCase(playlistName)) {
          for (VideoPlaylist vPs : videoPlaylists) {
            if (vPs.getName().equalsIgnoreCase(playlistName)) {
              System.out.println("Showing playlist: " + playlistName);
              if (vPs.getVideos() != null && vPs.getVideos().size() != 0) {
                for (Video v : vPs.getVideos()) {
                    if(flaggedVideos.contains(v)){
                        System.out.println(v+" - FLAGGED (reason: "+flagReason.get(flaggedVideos.indexOf(v))+")");
                    } else {
                        System.out.println(v);
                    }
                  //break;
                }
              } else {
                System.out.println("No videos here yet");
              }
            }
            break;
          }
        } else {
          System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
        }
      }
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    ArrayList<String> tempIdList = new ArrayList<>();
    for(Video v: videoLibrary.getVideos()){
      tempIdList.add(v.getVideoId());
    }
    if (playListNames.isEmpty()) {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    } else {
      for (String p : playListNames) {
        if (p.equalsIgnoreCase(playlistName)) {
          if (tempIdList.contains(videoId)) {
            for (VideoPlaylist vPs : videoPlaylists) {
              if (vPs.getName().equalsIgnoreCase(playlistName)) {
                if (vPs.getVideos() == null || vPs.getVideos().isEmpty()) {
                  System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
                } else {
                  vPs.removeVideoFromPlaylist(videoLibrary.getVideo(videoId));
                  System.out.println("Removed video from " + playlistName + ": " + videoLibrary.getVideo(videoId).getTitle());
                }
                break;
              }
            }
          } else {
            System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
            break;
          }
        } else {
          System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
          break;
        }
      }
    }
  }

  public void clearPlaylist(String playlistName) {
    if (playListNames.isEmpty()) {
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
    } else {
      for (String p : playListNames) {
        if (p.equalsIgnoreCase(playlistName)) {
          for (VideoPlaylist vPs : videoPlaylists) {
            if (vPs.getName().equalsIgnoreCase(playlistName)) {
              if (vPs.getVideos() != null) {
                vPs.getVideos().clear();
                System.out.println("Successfully removed all videos from "+playlistName);
              }
            }
          }
        } else {
          System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
        }
      }
    }
  }

  public void deletePlaylist(String playlistName) {
    if(playListNames.isEmpty()){
      System.out.println("Cannot delete playlist " + playlistName+": Playlist does not exist");
    } else {
      for (String p : playListNames) {
        if (p.equalsIgnoreCase(playlistName)) {
          for(VideoPlaylist vPs: videoPlaylists){
            if(vPs.getName().equalsIgnoreCase(playlistName)){
              videoPlaylists.remove(vPs);
              System.out.println("Deleted playlist: "+playlistName);
            }
            break;
          }
        } else {
          System.out.println("Cannot delete playlist " + playlistName+": Playlist does not exist");
        }
      }
    }
  }

  public void searchVideos(String searchTerm) {
    Scanner in = new Scanner(System.in);
    int count = 0;
    boolean videoFound = false;
    ArrayList<Video> videosListedFromSearch = new ArrayList<>();
    ArrayList<String> tempIdList = new ArrayList<>();
    for(Video v: videoLibrary.getVideos()){
      tempIdList.add(v.getVideoId());
    }
    for(String iD: tempIdList){
      String vidLowerC = videoLibrary.getVideo(iD).getTitle().toLowerCase();
      if((vidLowerC.contains(searchTerm.toLowerCase()))&&(!flaggedVideos.contains(videoLibrary.getVideo(iD)))){
        videoFound = true;
        //System.out.println("Here are the results for "+searchTerm+":");
      }
      //break;
    }
    if(videoFound){
        System.out.println("Here are the results for "+searchTerm+":");
    }
    for(String iD: tempIdList){
      String vidLowerC = videoLibrary.getVideo(iD).getTitle().toLowerCase();
      if((vidLowerC.contains(searchTerm.toLowerCase()))&&(!flaggedVideos.contains(videoLibrary.getVideo(iD)))){
          videoFound = true;
        count++;
        System.out.println(count+") "+videoLibrary.getVideo(iD));
        videosListedFromSearch.add(videoLibrary.getVideo(iD));
      }
    }
    if(!videoFound){
      System.out.println("No search results for "+searchTerm);
    } else{
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");
      if(in.hasNextInt()) {
        int videoSelect = in.nextInt();
        if(videoSelect > 0 && videoSelect <=count){
          playVideo((videosListedFromSearch.get(videoSelect-1)).getVideoId());
        }
      }
    }
  }

  public void searchVideosWithTag(String videoTag) {
    Scanner in = new Scanner(System.in);
    int count = 0;
    boolean videoFound = false;
    ArrayList<Video> videosListedFromSearch = new ArrayList<>();
    ArrayList<String> tempIdList = new ArrayList<>();
    for(Video v: videoLibrary.getVideos()){
      tempIdList.add(v.getVideoId());
    }
    for(String iD: tempIdList){
      ArrayList<String> tempTagsList = new ArrayList<>();
      for(String t: videoLibrary.getVideo(iD).getTags()){
        tempTagsList.add(t.toLowerCase());
      }
      if((tempTagsList.contains(videoTag.toLowerCase()))&&(videoTag.contains("#"))&&(!flaggedVideos.contains(videoLibrary.getVideo(iD)))){
        videoFound = true;
        //System.out.println("Here are the results for "+videoTag+":");
      }
      //break;
    }
      if(videoFound){
          System.out.println("Here are the results for "+videoTag+":");
      }
    for(String iD: tempIdList){
      ArrayList<String> tempTagsList = new ArrayList<>();
      for(String t: videoLibrary.getVideo(iD).getTags()){
        tempTagsList.add(t.toLowerCase());
      }
      if((tempTagsList.contains(videoTag.toLowerCase()))&&(videoTag.contains("#"))&&(!flaggedVideos.contains(videoLibrary.getVideo(iD)))){
        count++;
        System.out.println(count+") "+videoLibrary.getVideo(iD));
        videosListedFromSearch.add(videoLibrary.getVideo(iD));
      }
    }
    if(!videoFound){
      System.out.println("No search results for "+videoTag);
    } else{
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");
      if(in.hasNextInt()) {
        int videoSelect = in.nextInt();
        if(videoSelect > 0 && videoSelect <=count){
          playVideo((videosListedFromSearch.get(videoSelect-1)).getVideoId());
        }
      }
    }
  }

  public void flagVideo(String videoId) {
    ArrayList<String> tempIdList = new ArrayList<>();
    for(Video v: videoLibrary.getVideos()){
      tempIdList.add(v.getVideoId());
    }
    if(flaggedVideos.size()>0){
      for(Video v: flaggedVideos){
        if(v.getVideoId().equals(videoId)){
          System.out.println("Cannot flag video: Video is already flagged");
        }
        break;
      }
    }
    if(tempIdList.contains(videoId)&&!flaggedVideos.contains(videoLibrary.getVideo(videoId))){
      if(vidIds.contains(videoId)){
        System.out.println("Stopping video: " + videoLibrary.getVideo(vidIds.get(vidIds.size() - 1)).getTitle());
        vidIds.remove(videoId);
        System.out.println("Successfully flagged video: "+videoLibrary.getVideo(videoId).getTitle()+" (reason: Not supplied)");
      } else {
        System.out.println("Successfully flagged video: " + videoLibrary.getVideo(videoId).getTitle() + " (reason: Not supplied)");
        flaggedVideos.add(videoLibrary.getVideo(videoId));
        flagReason.add("Not supplied");
      }
    } else if (!tempIdList.contains(videoId)){
      System.out.println("Cannot flag video: Video does not exist");
    }
  }

  public void flagVideo(String videoId, String reason) {
    ArrayList<String> tempIdList = new ArrayList<>();
    for(Video v: videoLibrary.getVideos()){
      tempIdList.add(v.getVideoId());
    }
    if(flaggedVideos.size()>0){
      for(Video v: flaggedVideos){
        if(v.getVideoId().equals(videoId)){
          System.out.println("Cannot flag video: Video is already flagged");
        }
        break;
      }
    }
    if(tempIdList.contains(videoId)&&(!flaggedVideos.contains(videoLibrary.getVideo(videoId)))){
      if(vidIds.contains(videoId)) {
        System.out.println("Stopping video: " + videoLibrary.getVideo(vidIds.get(vidIds.size() - 1)).getTitle());
        vidIds.remove(videoId);
        System.out.println("Successfully flagged video: " + videoLibrary.getVideo(videoId).getTitle() + " (reason: " + reason + ")");
      } else {
        System.out.println("Successfully flagged video: " + videoLibrary.getVideo(videoId).getTitle() + " (reason: " + reason + ")");
        flaggedVideos.add(videoLibrary.getVideo(videoId));
        flagReason.add(reason);
      }
    } else if(!tempIdList.contains(videoId)) {
      System.out.println("Cannot flag video: Video does not exist");
    }
  }

  public void allowVideo(String videoId) {
    ArrayList<String> tempIdList = new ArrayList<>();
    for(Video v: videoLibrary.getVideos()){
      tempIdList.add(v.getVideoId());
    }
    if(tempIdList.contains(videoId)&&flaggedVideos.contains(videoLibrary.getVideo(videoId))){
        flagReason.remove(flaggedVideos.indexOf(videoLibrary.getVideo(videoId)));
        flaggedVideos.remove(videoLibrary.getVideo(videoId));
        System.out.println("Successfully removed flag from video: "+videoLibrary.getVideo(videoId).getTitle());
      } else if(tempIdList.contains(videoId)&&!flaggedVideos.contains(videoLibrary.getVideo(videoId))){
        System.out.println("Cannot remove flag from video: Video is not flagged");
      } else if (!tempIdList.contains(videoId)){
      System.out.println("Cannot remove flag from video: Video does not exist");
    }
  }
}