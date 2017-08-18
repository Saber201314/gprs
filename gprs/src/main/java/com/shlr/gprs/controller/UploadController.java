package com.shlr.gprs.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.crypto.digest.DigestUtil;
import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.util.HexUtil;

@Controller
public class UploadController {

	@RequestMapping("/upload.action")
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		JSONObject result = new JSONObject();

		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();
			String path = request.getSession().getServletContext().getRealPath("/uploads/");
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					
					String newFilePath = path +"/"+ file.getOriginalFilename();
					// 上传
					File newFile = new File(newFilePath);
					file.transferTo(newFile);
					String md5FileName = HexUtil.encodeHexStr(DigestUtil.md5(newFile));
					File dest = new File(path+"/"+md5FileName+"."+FileUtil.extName(newFile));
					FileUtil.copy(newFile, dest, true);
					String fileUrl = request.getSession().getServletContext().getContextPath()+"/uploads/"+FileUtil.mainName(dest)+"."+FileUtil.extName(dest);
					result.put("message", fileUrl);
				}

			}

		}
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		result.put("success", true);
		
		return result.toJSONString();
	}
}
