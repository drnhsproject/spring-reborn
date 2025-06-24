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
import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class MediaResource {

    private final FileStorage fileStorage;

    private final UploadMediaHandler uploadMediaHandler;

    @PostMapping("/upload")
    public ResponseEntity<SingleResponse<?>> createMedia(
        @RequestParam(value = "file", required = false) MultipartFile file,
        @RequestParam(value = "file[]", required = false) List<MultipartFile> files,
        @RequestParam("bucket") String bucket,
        @RequestParam("path") String path
    ) throws IOException {
        MediaUploadResult result = uploadMediaHandler.handle(bucket, path, file, files);
        if (result.multiple()) {
            return ResponseEntity.ok(new SingleResponse<>("file uploaded", result.results()));
        } else {
            return ResponseEntity.ok(new SingleResponse<>("file uploaded", result.results().getFirst()));
        }
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
