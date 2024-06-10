package com.example.VideoStreamdemo.Service;

import com.example.VideoStreamdemo.Entity.Video;
import com.example.VideoStreamdemo.VideoRepository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public String saveVideo(MultipartFile file) throws IOException {
        Video video=new Video(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes(),
                file.getSize(),
                "1080p"
        );
        videoRepository.save(video);
        return video.getId();
    }

    @Override
    public Video getVideoById(String id) {
        return videoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteVideoById(String id) {
        videoRepository.deleteById(id);
    }

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

}