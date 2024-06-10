package com.example.VideoStreamdemo.VideoRepository;

import com.example.VideoStreamdemo.Entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video,String> {
}
