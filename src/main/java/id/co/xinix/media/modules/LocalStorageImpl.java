package id.co.xinix.media.modules;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@Qualifier("localStorage")
public class LocalStorageImpl implements FileStorage{

    private final Path root = Paths.get("uploads");

    @Override
    public List<MediaDataResult> upload(String bucket, String path, List<MultipartFile> files) {
        return files.stream()
            .map(file -> handleSingle(bucket, path, file))
            .toList();
    }

    private MediaDataResult handleSingle(String bucket, String path, MultipartFile file) {
        try {
            String randomId = NanoIdUtils.randomNanoId();
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;

            String safeOriginalFilename = originalFilename
                .trim()
                .replaceAll("\\s+", "_")
                .replaceAll("[^a-zA-Z0-9._-]", "");

            String filename = randomId + "_" + safeOriginalFilename;
            Path targetPath = root.resolve(Paths.get(bucket, path, filename)).normalize();

            Files.createDirectories(targetPath.getParent());
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return new MediaDataResult(
                bucket,
                path,
                file.getContentType(),
                filename,
                originalFilename
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public Resource download(String bucket, String path, String filename, String originalFilename) {
        try {
            Path filePath = Paths.get("uploads", bucket, path, filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found or not readable");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while reading file", e);
        }
    }

}
