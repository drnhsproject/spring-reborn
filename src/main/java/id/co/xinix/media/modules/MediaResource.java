package id.co.xinix.media.modules;

import id.co.xinix.media.services.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class MediaResource {

    private final FileStorage fileStorage;

    @PostMapping("/upload")
    public ResponseEntity<SingleResponse<MediaDataResult>> createMedia(
            @RequestHeader("Authorization") @RequestParam(value = "files") MultipartFile files,
            @RequestParam(value = "bucket") String bucket,
            @RequestParam(value = "path") String path
    ) throws URISyntaxException, IOException {
        MediaDataResult mediaDataResult = fileStorage.upload(bucket, path, files);
        SingleResponse<MediaDataResult> responseData = new SingleResponse<>("file uploaded", mediaDataResult);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadMediaFromOS(
            @RequestParam("bucket") String bucket,
            @RequestParam("path") String path,
            @RequestParam("filename") String filename,
            @RequestParam("originalFilename") String originalFilename
    ) throws Exception {
        try {
            Resource resource = fileStorage.download(bucket, path, filename, originalFilename);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFilename + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file", e);
        }
    }

}
