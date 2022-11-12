package com.zionflame.imperiumserver.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	@Value("${imperium.storage.upload_path}")
	private String uploadPath;

	public File salvar(MultipartFile multipart) {

		Path filePath = this.montaCaminho();

		Path imagePath = filePath
				.resolve(String.format("%S__%S", Instant.now().toEpochMilli(), multipart.getOriginalFilename()));

		try {

			Files.createDirectories(filePath);

			Files.createFile(imagePath);

			multipart.transferTo(imagePath);

			return imagePath.toFile();

		} catch (IOException | IllegalStateException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public UrlResource buscar(String path) {
		try {
			return new UrlResource(Paths.get(path).toUri());	
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void deletar(String path) {
		try {
			Files.delete(Paths.get(path));	
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	private Path montaCaminho() {
		LocalDate now = LocalDate.now();
		return Paths.get(uploadPath, String.valueOf(now.getYear()), String.format("%02d", now.getMonthValue()),
				String.format("%02d", now.getDayOfMonth()));
	}

}
