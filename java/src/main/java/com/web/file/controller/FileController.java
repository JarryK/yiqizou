package com.web.file.controller;

import com.web.base.RestResult;
import com.web.file.model.UserFile;
import com.web.file.service.UserFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
@Api(tags = "Core-文件：文件管理接口")
public class FileController {
	
	private final UserFileService userFileService;

	@ApiOperation("上传文件")
	@PostMapping("/uploadFile")
	public RestResult<Object> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception{
		return RestResult.success(userFileService.uploadFile(file));
	}
	
	@ApiOperation("下载文件")
	@GetMapping("/downloadFile")
	public RestResult<Object> downloadFile(@RequestParam("id")Long id, HttpServletResponse response) throws Exception{
		userFileService.downloadFile(id, response);
		return RestResult.success();
	}
	
	@ApiOperation("删除文件")
	@GetMapping("/deleteFile")
	public RestResult<Object> deleteFile(@RequestParam("id")Long id){
		userFileService.deleteFile(id);
		return RestResult.success();
	}
	
	@ApiOperation("查询文件")
	@GetMapping("/selectFile")
	public RestResult<Object> selectFile(@RequestParam("id")Long id){
		UserFile userFile = userFileService.getById(id);
		if(userFile == null) {
			return RestResult.error("用户文件不存在");
		}
		return RestResult.success(userFileService.getById(id));
	}
	
	@ApiOperation("获取文件Base64")
	@GetMapping("/getImgBase64")
	public RestResult<Object> getImgBase64(@RequestParam("id")Long id) throws Exception{
		UserFile userFile = userFileService.getById(id);
		if(userFile == null) {
			return RestResult.error("用户文件不存在");
		}
		String base64 = userFileService.getImgBase64(id);
		return RestResult.success("成功",base64);
	}
	
}
