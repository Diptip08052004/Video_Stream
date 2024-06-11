package com.example.VideoStreamdemo.Controller;

import com.example.VideoStreamdemo.Entity.UploadFileResponse;
import com.example.VideoStreamdemo.Entity.Video;
import com.example.VideoStreamdemo.Service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private static final Logger log = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private VideoService videoService;

//    @PostMapping("/upload")
//    public ResponseEntity<String>uploadVideo(@RequestParam("file")MultipartFile file) throws IOException{
//        String videoId=videoService.saveVideo(file);
//        String videoUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/videos/").path(videoId).toUriString();
//        return new ResponseEntity<>("Video uploaded Successfully with ID: " + videoId + ". You can retrieve the video at: " + videoUrl, HttpStatus.OK);
//    }

    @PostMapping("/upload")
    public ResponseEntity<String>uploadVideo(@RequestParam("file")MultipartFile file){
        try {
            String videoId=videoService.saveVideo(file);
            String videoUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/videos/").path(videoId).toUriString();
            return new ResponseEntity<>("Video uploaded Successfully with ID: " + videoId + ". You can retrieve the video at: " + videoUrl, HttpStatus.OK);
        }catch (IOException e){
            log.error("Error uploading video",e);
            return new ResponseEntity<>("Error uploading video",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getVideo(@PathVariable String id, @RequestHeader(value = "Range", required = false) String range) throws IOException {
        Video video = videoService.getVideoById(id);
        if (video == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(video.getVideoType()));
        headers.setContentDispositionFormData("attachment", video.getVideoName());

        if (range!= null) {
            // Handle range request
            String[] rangeValues = range.split("=");
            String[] rangeParams = rangeValues[1].split("-");
            long start = Long.parseLong(rangeParams[0]);
            long end = rangeParams.length > 1? Long.parseLong(rangeParams[1]) : video.getVideoData().length - 1;

            if (start >= video.getVideoData().length) {
                return new ResponseEntity<>(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
            }

            long contentLength = end - start + 1;
            headers.set("Content-Range", "bytes " + start + "-" + end + "/" + video.getVideoData().length);
            headers.set("Content-Length", String.valueOf(contentLength));

            byte[] videoData = new byte[(int) contentLength];
            System.arraycopy(video.getVideoData(), (int) start, videoData, 0, (int) contentLength);

            return new ResponseEntity<>(videoData, headers, HttpStatus.PARTIAL_CONTENT);
        } else {
            // Handle full video request
            headers.set("Content-Length", String.valueOf(video.getVideoData().length));
            return new ResponseEntity<>(video.getVideoData(), headers, HttpStatus.OK);
        }
    }
}
