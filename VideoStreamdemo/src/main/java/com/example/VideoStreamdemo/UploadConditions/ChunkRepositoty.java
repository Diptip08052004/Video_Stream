package com.example.VideoStreamdemo.UploadConditions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChunkRepositoty extends JpaRepository<Chunk,Long> {

    List<Chunk> findByVideoId(String videoId);

    Chunk findByVideoIdAndChunkNumber(String videoId, Integer chunkNumber);
}
