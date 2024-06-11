package com.example.VideoStreamdemo.UploadConditions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChunkService {

    @Autowired
    private ChunkRepositoty chunkRepository;

    public Long saveChunk(Chunk chunk) {
        Chunk savedChunk = chunkRepository.save(chunk);
        return savedChunk.getId();
    }

    public List<Chunk> getChunksByVideoId(String videoId) {
        return chunkRepository.findByVideoId(videoId);
    }

    public Chunk getChunkByVideoIdAndChunkNumber(String videoId, Integer chunkNumber) {
        return chunkRepository.findByVideoIdAndChunkNumber(videoId, chunkNumber);
    }
}
