package id.co.xinix.media.modules;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorage {
    List<MediaDataResult> upload(String bucket, String path, List<MultipartFile> files);
    Resource download(String bucket, String path, String filename, String originalFilename);
}
