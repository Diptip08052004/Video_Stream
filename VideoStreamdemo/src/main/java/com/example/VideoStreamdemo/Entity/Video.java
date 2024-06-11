package com.example.VideoStreamdemo.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String videoName;

    private String videoType;

    @Lob
    @Column(length = 65555)
    private byte[] videoData;

    private long videoSize;

    private String videoResolution;

    @ElementCollection
    private List<String> chunks;

    private int videoDuration; // in seconds

    public Video(String videoName, String videoType, long videoSize, String videoResolution) {
        this.videoName = videoName;
        this.videoType = videoType;
        this.videoSize = videoSize;
        this.videoResolution = videoResolution;
    }

    public void setChunks(List<String> chunks) {
        this.chunks = chunks;
    }

    public List<String> getChunks() {
        return chunks;
    }

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    @Builder
    public Video(String videoName, String videoType, byte[] videoData, long videoSize, String videoResolution, int videoDuration) {
        this.videoName = videoName;
        this.videoType = videoType;
        this.videoData = videoData;
        this.videoSize = videoSize;
        this.videoResolution = videoResolution;
        this.videoDuration = videoDuration;
    }
}
