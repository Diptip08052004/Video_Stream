package com.example.VideoStreamdemo.UploadConditions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Chunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] chunkData;

    private String videoId;
    private Integer chunkNumber;
}
