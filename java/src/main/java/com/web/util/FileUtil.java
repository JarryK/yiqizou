package com.web.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 文件工具类
 */
public class FileUtil extends FileUtils {
	
	/**
	 * web端上传文件
	 * @param filePath 服务器本地保存目录
	 * @return fileName 生成文件文件名
	 * */
	public static String uploadFile(MultipartFile file, String filePath, String fileName) throws Exception {
		byte[] fileByte = file.getBytes();
		int index = fileName.lastIndexOf('.');
		if(index < 0) {
			index = fileName.length();
		}
		String prefix = fileName.substring(0,index);
		String suffix = fileName.substring(index,fileName.length());
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        fileName = prefix+UUID.randomUUID()+suffix;
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(fileByte);
        out.flush();
        out.close();
        return fileName;
    }
	
	
	
	/**
	 * 文件转换成byte数组
	 * 
	 * @param filePath
	 * @return byte[]
	 */
	public static byte[] file2byte(File filePath) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 文件转换成byte数组
	 * 
	 * @param file
	 * @return byte[]
	 */
	public static byte[] readFile(File file) {
		InputStream input = null;
		ByteArrayOutputStream output = null;
		byte[] data = null;
		try {
			input = new FileInputStream(file);
			output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * byte数组转换成文件
	 * 
	 * @param bytes
	 * @param path 转换文件保存路径
	 */
	public static void byte2File(byte[] bytes, String path) {
		try {
			// 根据绝对路径初始化文件
			File localFile = new File(path);
			if (!localFile.exists()) {
				localFile.createNewFile();
			}
			// 输出流
			OutputStream os = new FileOutputStream(localFile);
			os.write(bytes);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断文件是否为图片类型（根据文件后缀）
	 * 
	 * @param file 检查文件
	 * */
	public static boolean isImg(String fileName) {
		int suffixIndex = fileName.lastIndexOf(".");
		if(suffixIndex < 0) {
			return false;
		}
		String suffix = fileName.substring(suffixIndex,fileName.length());
		String[] imgSuffix = {".jpg",".jpeg",".png",".bmp",".gif"};
		for(String suf : imgSuffix) {
			if(suffix.equalsIgnoreCase(suf))
				return true;
		}
		return false;
	}
}
