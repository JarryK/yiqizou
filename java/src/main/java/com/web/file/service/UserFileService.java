package com.web.file.service;

import com.web.file.model.UserFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserFileService {

	public UserFile uploadFile(MultipartFile file, HttpServletRequest request) throws Exception;

	public void downloadFile(Long id, HttpServletResponse response) throws Exception ;
	
	public void deleteFile(Long id);
	
	public String getImgBase64(Long id)  throws Exception;
	
	public UserFile save(UserFile userFile);
	
	public UserFile getById(Long id);
}
