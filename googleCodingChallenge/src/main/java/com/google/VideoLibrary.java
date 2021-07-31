package com.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary{

  private final HashMap<String, Video> videos;

  VideoLibrary() {
    this.videos = new HashMap<>();
    try {
      File file = new File(this.getClass().getResource("/videos.txt").getFile());

      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] split = line.split("\\|");
        String title = split[0].strip();
        String id = split[1].strip();
        List<String> tags;
        if (split.length > 2) {
          tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(
              Collectors.toList());
        } else {
          tags = new ArrayList<>();
        }
        this.videos.put(id, new Video(title, id, tags));
      }
    } catch (FileNotFoundException e) {
      System.out.println("Couldn't find videos.txt");
      e.printStackTrace();
    }
  }

  List<Video> getVideos() {

    /**
     * Sorting videos from the hashmap by their title value.
     * Create a custom comparator, convert entry set to list, sort the list then copy it back to a HashMap which is returned as sorted
     */
    Comparator<Entry<String, Video>> valueComparator = new Comparator<>() {
      @Override
      public int compare(Entry<String, Video> v1, Entry<String, Video> v2) {
        String s1 = v1.getValue().getTitle();
        String s2 = v2.getValue().getTitle();
        return s1.compareTo(s2);
      }
    };
    Set<Entry<String, Video>> entries = videos.entrySet();
    List<Entry<String, Video>> listOfEntries = new ArrayList<>(entries);
    listOfEntries.sort(valueComparator);
    LinkedHashMap<String, Video> sortedByValue = new LinkedHashMap<>(listOfEntries.size());

    for(Entry<String, Video> entry: listOfEntries){
      sortedByValue.put(entry.getKey(), entry.getValue());
    }
    return new ArrayList<>(sortedByValue.values());
  }

  /**
   * Get a video by id. Returns null if the video is not found.
   */
  Video getVideo(String videoId) {
    return this.videos.get(videoId);
  }

}
