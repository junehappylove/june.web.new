package com.june.dao.back.bussiness.vehicle;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.back.bussiness.vehicle.VehicleDto;
import com.june.dto.back.bussiness.vehicle.VehicleUser;

/**
 * @Description: 车型管理用Dao
 * @author 王俊伟
 * @date 2015年11月6日 下午2:30:30
 * @version V1.0
 */
public interface VehicleDao extends BaseDao<VehicleDto> {

	/**
	 * 根据车型ID和用户ID，获取一条车型-用户信息
	 * 
	 * @param vehicleUser
	 * @return
	 * @date 2016年5月10日 下午12:52:21
	 * @writer wjw.happy.love@163.com
	 */
	VehicleUser getVehicleUserById(VehicleUser vehicleUser);

	/**
	 * 根据车型ID获取所有的车型用户信息
	 * 
	 * @param vehicleDto
	 * @return
	 * @date 2016年5月10日 下午12:51:21
	 * @writer wjw.happy.love@163.com
	 */
	List<VehicleUser> getVehicleUsers(VehicleDto vehicleDto);

	/**
	 * 添加一条车型-用户信息
	 * 
	 * @param vehicleUser
	 * @date 2016年5月10日 下午12:52:50
	 * @writer wjw.happy.love@163.com
	 */
	void addVehicleUser(VehicleUser vehicleUser);

	/**
	 * 根据车型ID删除所有的车型-用户信息记录
	 * @param vehicleDto
	 * @date 2016年5月10日 下午12:57:44
	 * @writer wjw.happy.love@163.com
	 */
	void deleteVehicleUsers(VehicleDto vehicleDto);
	/**
	 * 根据车型ID和用户ID删除一条车型-用户信息记录
	 * @param vehicleUser
	 * @date 2016年5月10日 下午12:57:44
	 * @writer wjw.happy.love@163.com
	 */
	void deleteVehicleUser(VehicleUser vehicleUser);
}
