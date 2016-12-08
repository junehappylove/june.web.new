package com.june.service.front.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.front.login.FrontLoginDao;
import com.june.dto.front.login.UserInfoDto;

@Service
public class FrontLoginService extends BaseService<FrontLoginDao, UserInfoDto> {

	@Autowired
	FrontLoginDao frontLoginDao;

	// 门户首页登录用户check
	public UserInfoDto frontloginCheck(UserInfoDto userInfoDto) {
		userInfoDto = frontLoginDao.frontloginCheck(userInfoDto);
		return userInfoDto;
	}
}
