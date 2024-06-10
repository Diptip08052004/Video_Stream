package com.example.VideoStreamdemo.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    private String videoName;
    private String videoType;
    @Lob
    @Column(length = 65555)
    private byte[] videoData;
    private long videoSize;
    private String videoResolution;

    public Video(String videoName, String videoType, byte[] videoData, long videoSize, String videoResolution) {
        this.videoName = videoName;
        this.videoType = videoType;
        this.videoData = videoData;
        this.videoSize = videoSize;
        this.videoResolution = videoResolution;
    }

    public Video(String videoName, String videoType, long videoSize, String videoResolution) {
        this.videoName=videoName;
        this.videoType=videoType;
        this.videoSize=videoSize;
        this.videoResolution=videoResolution;

    }

    public void setVideoSize(long videoSize){
        this.videoSize=videoSize;
    }

    public long getVideoSize(){
        return videoData.length;
    }

    @Column(nullable = false)
    private int videoDuration;  ///in seconds

    public void SetVideoDuration(int videoDuration){
        this.videoDuration=videoDuration;
    }
    public void setVideoResolution(String videoResolution){
        this.videoResolution=videoResolution;
    }
    public String getVideoResolution(){
        return videoResolution;
    }

}
