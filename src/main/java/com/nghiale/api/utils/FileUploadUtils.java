package com.nghiale.api.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUploadUtils {
	public static boolean saveFile(InputStream fis, String path) {
		OutputStream fos = null;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			byte[] b = new byte[1024 * 10];
			int data = 0;
			while ((data = fis.read(b)) != -1) {
				fos.write(b, 0, data);
			}
			fos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				return false;
			}
		}
	}
}
