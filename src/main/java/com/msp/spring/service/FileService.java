package com.msp.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@RequiredArgsConstructor
public class FileService {

    //путь берется либо и по свойству либо дефолтный
    @Value("${app.files.bucket:\\Users\\Serg\\IdeaProjects\\spring_starter\\springCore\\files}")
    private final String bucket;

    @SneakyThrows
    public void upload(String fileName, InputStream content) {
        Path fullPath = Path.of(bucket, fileName);
        try (content) {
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String fileName) {
        Path fullPath = Path.of(bucket, fileName);

        return Files.exists(fullPath)
                ? Optional.of(Files.readAllBytes(fullPath))
                : Optional.empty();
    }
}
