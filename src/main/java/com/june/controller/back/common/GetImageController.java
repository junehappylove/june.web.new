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

import com.june.utility.FastDfsUtils;

@Controller
public class GetImageController {

	@RequestMapping("/test/{date}/{url}/")
	public void getImage(@PathVariable(value = "url") String url, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		byte[] buffer = FastDfsUtils.getFile("group1-M00-00-00-wKhIgFaM0QSAeLiPAAS4VPEmAjY095.jpg");
		returnImageByBuffer(httpServletResponse, buffer);
	}
	
	private void returnImageByBuffer(HttpServletResponse httpServletResponse, byte[] b) {
		httpServletResponse.setContentType("image/gif");
		try {
			OutputStream out = httpServletResponse.getOutputStream();
			out.write(b);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
