/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.controller.back.common.TreeController.java
 * 日期:2017年2月16日
 */
package com.june.controller.back.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.june.common.BaseController;
import com.june.dto.back.common.TreeDto;

/**
 * 通用的树形结构获取器
 * TreeController <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年2月16日 下午7:49:18
 * @version 1.0.0
 */
@Controller
@RequestMapping("/tree")
public class TreeController extends BaseController<TreeDto> {
	// TODO 应该定义为通用的树结构获取，可以参考字典部分，设置参数：dic_code、type、条件 来获取一颗树
	// XXX 适合于固定结构树，大数据量的树需重新定义方法实现
}
