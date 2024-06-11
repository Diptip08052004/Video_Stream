package com.example.VideoStreamdemo.UploadConditions;

import com.example.VideoStreamdemo.Entity.Video;
import com.example.VideoStreamdemo.VideoRepository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VideoUploadService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ChunkRepositoty chunkRepositoty;

    private UploadSession uploadSession;

    public void startUpload(MultipartFile file) throws IOException {
        uploadSession =new UploadSession(file);
        uploadChunk(0);
    }

    private void uploadChunk(int chunkIndex) throws IOException {
        byte[] chunk=getChunkFromFile(uploadSession.getFile(),chunkIndex);

        String chunkId=uploadChunkToServer(chunk);
        uploadSession.addChunk(chunkId,chunkIndex);
        if (chunkIndex * 1024 * 1024 < uploadSession.getFile().getSize()){
            uploadChunk(chunkIndex+1);
        }else {
            Video video =new Video();
            video.setChunks(uploadSession.getChunkIds());
            videoRepository.save(video);
        }
    }

    private String uploadChunkToServer(byte[] chunk){
        Chunk chunkEntity=new Chunk();
        chunkEntity.setChunkData(chunk);

        Chunk savedChunk=chunkRepositoty.save(chunkEntity);

        return savedChunk.getId().toString();
    }

    private void pauseUpload(){
        uploadSession.pause();
    }

    private byte[] getChunkFromFile(MultipartFile file,int chunkIndex){
        int chunkSize=1024*1024;
        long fileSize=file.getSize();
        long startIndex=chunkIndex*chunkSize;
        long endIndex=Math.min(startIndex+chunkSize,fileSize);

        try(InputStream inputStream = file.getInputStream()) {
            byte[] chunk=new byte[(int)(endIndex-startIndex)];
            inputStream.skip(startIndex);
            inputStream.read(chunk);
            return chunk;
        }catch (IOException e){
            throw  new RuntimeException("Error reading chunk from file",e);
        }
    }
}
