package com.yhdc.mreview.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.yhdc.mreview.dto.UploadResultDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@Log4j2
public class UploadController {

	@Value("${com.yhdc.upload.path}")
	private String uploadPath;

	// Save Image
	@PostMapping("/uploadAjax")
	public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles) {

		List<UploadResultDTO> resultDTOList = new ArrayList<>();
		for (MultipartFile uploadFile : uploadFiles) {

			// Uploading only the image files
			if (uploadFile.getContentType().startsWith("image") == false) {
				log.warn("this is not the image type");
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			// In IE or EDGE takes the whole path
			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

			log.info("fileName: " + fileName);

			// CREATE FOLDER for DATEs
			String folderPath = makeFolder();

			// UUID
			String uuid = UUID.randomUUID().toString();

			// When saving files, use "_" in between the names
			String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

			Path savePath = Paths.get(saveName);

			try {
				// Save Original
				uploadFile.transferTo(savePath);

				// Filename starts with "s"
				String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;

				File thumbnailFile = new File(thumbnailSaveName);

				// Create Thumbnail
				Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile,100,100);

				resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));

			} catch (IOException e) {
				e.printStackTrace();
			}
		} // ForLoop Ends
		return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
	}

	// Display Image
	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(String fileName) {

		ResponseEntity<byte[]> result = null;

		try {
			String srcFileName = URLDecoder.decode(fileName, "UTF-8");
			log.info("fileName: " + srcFileName);

			File file = new File(uploadPath + File.separator + srcFileName);
			log.info("file: " + file);

			HttpHeaders header = new HttpHeaders();
			// MIME Type
			header.add("Content-Type", Files.probeContentType(file.toPath()));

			// File Data
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}

	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		String folderPath = str.replace("/", File.separator);

		File uploadPathFolder = new File(uploadPath, folderPath);

		if (uploadPathFolder.exists() == false) {
			uploadPathFolder.mkdirs();
		}
		return folderPath;
	}

	// Delete Image
	@PostMapping("/removeFile")
	public ResponseEntity<Boolean> removeFile(String fileName) {
		String srcFileName = null;

		try {
			srcFileName = URLDecoder.decode(fileName, "UTF-8");
			File file = new File(uploadPath + File.separator + srcFileName);
			boolean result = file.delete();
			File thumbnail = new File(file.getParent(), "s_" + file.getName());
			result = thumbnail.delete();

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
