package com.example.VideoStreamdemo.Service;

import com.example.VideoStreamdemo.Entity.Video;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Repository
public interface VideoService {
    String saveVideo(MultipartFile file) throws IOException;
    Video getVideoById(String id);
    void deleteVideoById(String id);
    List<Video> getAllVideos();
}
