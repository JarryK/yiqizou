package com.web.file.service.impl;

import cn.hutool.core.codec.Base64;
import com.web.file.config.FileProperties;
import com.web.file.mapper.UserFileMapper;
import com.web.file.model.UserFile;
import com.web.file.service.UserFileService;
import com.web.security.jwt.JwtTokenUtils;
import com.web.util.FileUtil;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class UserFileServiceImpl implements UserFileService{
	
	private final FileProperties properties;
	private final JwtTokenUtils tokenUtils;
	private final UserFileMapper userFileMapper;

	@Override
	public UserFile uploadFile(MultipartFile file, HttpServletRequest request) throws Exception {
		Long sizeKB = file.getSize() >> 10;	// 除1024转KB单位
		if(file.getSize() > 0 && file.getSize() <1024) {
			sizeKB = 1L;
		}
		if(sizeKB > properties.getMaxSize()) {
			throw new Exception("文件大小不可超过"+properties.getMaxSize()+"KB");
		}
		
		// 保存文件
		String realName = FileUtil.uploadFile(file, properties.getPath(), file.getResource().getFilename());
		
		String username = tokenUtils.getTokenUserName(request);
		UserFile userFile = new UserFile();
		userFile.setFileName(file.getResource().getFilename());
		userFile.setRealName(realName);
		userFile.setFilePath(properties.getPath()+realName);
		userFile.setSize(sizeKB);
		userFile.setIsImg(FileUtil.isImg(file.getResource().getFilename()));
		userFileMapper.insert(userFile);
		return userFile;
	}

	@Override
	public void downloadFile(Long id, HttpServletResponse response) throws Exception {
		UserFile uf = userFileMapper.selectByPrimaryKey(id);
		if(uf == null) {
			throw new Exception("fileId不存在");
		}
		String realName = uf.getFileName();
		String filePath = uf.getFilePath();
		File file = new File(filePath);
		if(!file.exists()) {
			throw new Exception("文件丢失");
		}
		if (file.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("Download  successfully!");
                return ;

            } catch (Exception e) {
                System.out.println("Download  failed!");
                throw new Exception("文件下载失败");

            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
	}

	@Override
	public void deleteFile(Long id) {
		UserFile uf = userFileMapper.selectByPrimaryKey(id);
		String filePath = uf.getFilePath();
		FileUtil.deleteQuietly(new File(filePath));
		userFileMapper.deleteByPrimaryKey(id);
	}

	@Override
	public String getImgBase64(Long id) throws Exception {
		UserFile uf = userFileMapper.selectByPrimaryKey(id);
		if(!uf.getIsImg()) {
			throw new Exception("非图片文件");
		}
		String base64 = "data:image/png;base64,"
			+ Base64.encode(new File(uf.getFilePath()));
		return base64;
	}

	@Override
	public UserFile save(UserFile userFile) {
		userFileMapper.insert(userFile);
		return userFile;
	}

	@Override
	public UserFile getById(Long id) {
		UserFile qo = new UserFile();
		qo.setId(id);
		return userFileMapper.selectByPrimaryKey(qo);
	}

}
