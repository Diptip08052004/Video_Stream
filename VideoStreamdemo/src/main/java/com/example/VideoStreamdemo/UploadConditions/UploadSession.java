package com.example.VideoStreamdemo.UploadConditions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UploadSession {

    private MultipartFile file;
    private List<String> chunkIds;
    private int lastChunkIndex;
    private boolean paused;

    public UploadSession(MultipartFile file){
        this.file=file;
        this.chunkIds=new ArrayList<>();
        this.paused=false;
    }

    public void addChunk(String chunkId,int chunkIndex){
        chunkIds.add(chunkId);
        lastChunkIndex=chunkIndex;
    }
    public void pause(){
        this.paused=true;
    }

    public boolean isPaused(){
        return paused;
    }

    public int getLastChunkIndex(){
        return lastChunkIndex;
    }
}
