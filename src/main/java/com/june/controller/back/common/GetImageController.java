/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */

package com.june.controller.back.common;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.june.util.FastDfsUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@Api(value="取图片")
public class GetImageController {

	@RequestMapping("/test/{date}/{url}/")
	@ApiOperation(value="取图片",httpMethod="POST")
	public void getImage(@ApiParam(value="图片地址",name="url",required=true)
			@PathVariable(value = "url") String url, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		byte[] buffer = FastDfsUtils.getFile("group1-M00-00-00-wKhIgFaM0QSAeLiPAAS4VPEmAjY095.jpg");
		returnImageByBuffer(response, buffer);
	}
	
	private void returnImageByBuffer(HttpServletResponse response, byte[] b) {
		response.setContentType("image/gif");
		try {
			OutputStream out = response.getOutputStream();
			out.write(b);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
