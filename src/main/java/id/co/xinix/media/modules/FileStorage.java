package id.co.xinix.media.modules;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    MediaDataResult upload(String bucket, String path, MultipartFile file);
    Resource download(String bucket, String path, String filename, String originalFilename);
}
