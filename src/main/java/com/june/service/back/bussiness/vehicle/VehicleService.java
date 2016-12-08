package com.june.service.back.bussiness.vehicle;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.bussiness.vehicle.VehicleDao;
import com.june.dto.back.bussiness.vehicle.VehicleDto;
import com.june.dto.back.bussiness.vehicle.VehicleUser;
import com.june.dto.back.system.basicset.UserInfoDto;
import com.june.utility.StringUtil;

/**
 * 车型信息Service
 * 
 * @author 王俊偉
 *
 */
@Service
public class VehicleService extends BaseService<VehicleDao, VehicleDto> {

	/**
	 * 车型信息dao
	 */
	@Autowired
	protected VehicleDao vehicleDao;

	/**
	 * 删除车型以及车型相关的用户信息
	 * 
	 * @param vehicleDto
	 * @return: void
	 */
	@Override
	public void deleteDtoById(VehicleDto vehicleDto) {
		super.deleteDtoById(vehicleDto);
		vehicleDao.deleteVehicleUsers(vehicleDto);// 删除车型-用户
	}
	
	/**
	 * 车型用户更新
	 * 
	 * @param vehicleDto
	 * @return: void
	 */
	@CacheEvict(value = { "getVehicleById", "getPageList", "getTotal" }, allEntries = true)
	public void updateVehicleUser(VehicleDto vehicleDto) {
		VehicleUser vehicleUser = null;
		String userId = null;
		String vehicleId = vehicleDto.getVehicleId();
		List<UserInfoDto> list = vehicleDto.getUsers();
		if (list != null) {
			for (UserInfoDto userInfoDto : list) {
				userId = userInfoDto.getUserId();
				vehicleUser = new VehicleUser(vehicleId, userId);
				updateVehicleUser(vehicleUser);
			}
		}
	}

	/**
	 * 车型用户一条记录更新
	 * 
	 * @param vehicleDto
	 * @return: void
	 */
	void updateVehicleUser(VehicleUser vehicleUser) {
		// 删除车型用户信息
		vehicleDao.deleteVehicleUser(vehicleUser);
		// 添加新的车型用户信息
		vehicleDao.addVehicleUser(vehicleUser);
	}

	/**
	 * 根据车型ID和用户ID获取一条车型用户信息
	 * 
	 * @param params
	 * @return
	 */
	public VehicleUser getVehicleUserById(VehicleUser vu) {
		return vehicleDao.getVehicleUserById(vu);
	}

	/**
	 * 删除车型-用户信息
	 * 
	 * @return void
	 */
	@CacheEvict(value = { "getVehicleById", "getPageList", "getTotal" }, allEntries = true)
	public void deleteVehicleUser(VehicleDto vehicleDto) {
		List<VehicleUser> list = vehicleDao.getVehicleUsers(vehicleDto);
		if (list != null && list.size() > 0) {// 如果存在车型用户信息
			vehicleDao.deleteVehicleUsers(vehicleDto);
		}
	}

	/**
	 * 添加车型-用户信息
	 * 
	 * @param vehicleDto
	 * @throws Exception
	 */
	@CacheEvict(value = { "getVehicleById", "getPageList", "getTotal" }, allEntries = true)
	public void addVehicleUsers(VehicleDto vehicleDto) {
		VehicleUser vu = null;
		String vid = vehicleDto.getVehicleId();
		List<UserInfoDto> users = vehicleDto.getUsers();
		if (users != null) {
			for (UserInfoDto user : users) {
				if (!StringUtil.isBlank(user.getUserId())) {
					String userId = user.getUserId();
					vu = new VehicleUser(vid, userId);
					VehicleUser vu2 = getVehicleUserById(vu);
					if (vu2 == null) {// 不存在添加车型用户信息
						vu.setAddTime(new Timestamp(System.currentTimeMillis()));
						vehicleDao.addVehicleUser(vu);
					}
				}
			}
		}
	}
}
