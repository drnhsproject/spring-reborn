package id.co.xinix.media.modules;

import id.co.xinix.media.services.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/file")
@Tag(name = "Media API", description = "Operation media")
@SecurityRequirement(name = "bearerAuth")
@RepositoryRestResource(exported = false)
@RequiredArgsConstructor
public class MediaResource {

    private final FileStorage fileStorage;

    private final UploadMediaHandler uploadMediaHandler;

    @Operation(summary = "Upload file(s)", description = "Upload single (file) or multiple files (file[])")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Uploaded successfully")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SingleResponse<?>> createMedia(
            @Parameter(description = "Single file", required = false)
            @RequestParam(value = "file", required = false) MultipartFile file,

            @Parameter(description = "Multiple files", required = false)
            @RequestParam(value = "file[]", required = false) List<MultipartFile> files,

            @RequestParam("bucket") String bucket,
            @RequestParam("path") String path
    ) throws IOException {
        List<MultipartFile> allFiles = new ArrayList<>();
        if (file != null && !file.isEmpty()) {
            allFiles.add(file);
        }

        if (files != null && !files.isEmpty()) {
            allFiles.addAll(files);
        }

        MediaUploadResult result = uploadMediaHandler.handle(bucket, path, file, allFiles);
        if (result.multiple()) {
            return ResponseEntity.ok(new SingleResponse<>("file uploaded", result.results()));
        } else {
            return ResponseEntity.ok(new SingleResponse<>("file uploaded", result.results().getFirst()));
        }
    }

    @Operation(summary = "Download File", description = "use for download file")
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
