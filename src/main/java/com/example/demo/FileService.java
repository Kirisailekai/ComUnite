package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity saveFile(String fileName, String mimeType, byte[] data) {
        FileEntity file = new FileEntity();
        file.setFileName(fileName);
        file.setMimeType(mimeType);
        file.setData(data);
        file.setUploadTime(java.time.LocalDateTime.now());
        return fileRepository.save(file);
    }

    public Optional<FileEntity> getFileById(Long id) {
        return fileRepository.findById(id);
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }
}
