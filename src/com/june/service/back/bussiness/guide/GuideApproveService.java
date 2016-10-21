package com.june.service.back.bussiness.guide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.bussiness.guide.GuideApproveDao;
import com.june.dto.back.bussiness.guide.GuideApproveDto;

/**
 * 
 * 操作指南审批信息Service <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月12日 下午5:42:59
 */
@Service
public class GuideApproveService extends BaseService<GuideApproveDao,GuideApproveDto> {
	@Autowired
	GuideApproveDao guideApproveDao;
	public void updateApproveNum(){
		guideApproveDao.updateApproveNum();
	}
	
	public int getApproveInfoNumber(){
		return guideApproveDao.getApproveInfoNumber();
	}
}
