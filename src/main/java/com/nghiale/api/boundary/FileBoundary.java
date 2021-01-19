package com.nghiale.api.boundary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileBoundary {
	private static final String DIR = "/images";

	@PostMapping("/uploads")
	public String name(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		try {
			String fileName = file.getOriginalFilename();
			String path = request.getServletContext().getRealPath(DIR) + File.separator + fileName;
			boolean isSaved = saveFile(file.getInputStream(), path);
			return isSaved ? "Saved" : "not saved";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private boolean saveFile(InputStream fis, String path) throws Exception {
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(path));
			byte[] b = new byte[1024 * 10];
			int data = 0;
			while ((data = fis.read(b)) != -1) {
				fos.write(b, 0, data);
			}
			fos.close();
			fos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
