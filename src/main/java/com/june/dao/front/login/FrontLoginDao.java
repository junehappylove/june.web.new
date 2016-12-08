package com.june.dao.front.login;

import com.june.common.BaseDao;
import com.june.dto.front.login.UserInfoDto;

public interface FrontLoginDao extends BaseDao<UserInfoDto> {

	public UserInfoDto frontloginCheck(UserInfoDto userInfoDto);
}
