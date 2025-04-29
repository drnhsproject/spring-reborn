package id.co.xinix.spring.modules.media;

import com.reborn.media_upload_library.modules.MediaFile;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.reborn.media_upload_library.modules.MediaStorageRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/media")
@ComponentScan(basePackages = {"com.reborn.media_upload_library.modules"})
public class MediaResource {

    private final MediaStorageRepository mediaStorageRepository;

    public MediaResource(MediaStorageRepository mediaStorageRepository) {
        this.mediaStorageRepository = mediaStorageRepository;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            MediaFile mediaFile = new MediaFile(file.getOriginalFilename(), file.getContentType(), file.getBytes());
            return mediaStorageRepository.upload(mediaFile);
        } catch (IOException e) {
            return "Upload failed: " + e.getMessage();
        }
    }
}
