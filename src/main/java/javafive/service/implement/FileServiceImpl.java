package javafive.service.implement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javafive.service.FileService;

@Service
public class FileServiceImpl implements FileService {
    private static final String BASE_DIRECTORY = "src/main/resources/static/photo/";

    @Override
    public File save(MultipartFile multipartFile, String folder) {
        if (multipartFile.isEmpty()) {
            return null;
        }
        try {
            String directory = BASE_DIRECTORY + folder;
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs(); 
            }
            
            String filePath = directory + "/" + multipartFile.getOriginalFilename();
            Path path = Paths.get(filePath);
            Files.write(path, multipartFile.getBytes());
            return path.toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<File> save(MultipartFile[] multipartFiles, String folder) {
        List<File> savedFiles = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            File savedFile = save(file, folder);
            if (savedFile != null) {
                savedFiles.add(savedFile);
            }
        }
        return savedFiles;
    }

    @Override
    public byte[] read(String path) {
        try {
            Path filePath = Paths.get(BASE_DIRECTORY + path);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
