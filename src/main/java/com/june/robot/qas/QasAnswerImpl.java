/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.robot.qas.QasAnswerImpl.java
 * 日期:2017年1月18日
 */
package com.june.robot.qas;

import java.text.MessageFormat;
import java.util.List;

import com.june.common.Constants;
import com.june.robot.BaseAnswer;
import com.june.robot.Result;
import com.june.utility.NetUtil;

/**
 * QasAnswerImpl <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月18日 下午11:13:31
 * @version 1.0.0
 */
public class QasAnswerImpl extends BaseAnswer {

	private static QasAnswerImpl instance = new QasAnswerImpl();

	private QasAnswerImpl() {
	}

	public static QasAnswerImpl instance() {
		return instance == null ? new QasAnswerImpl() : instance;
	}

	@Override
	public List<Result> getAnswers(String ask, int num) {
		List<Result> list = null;
		// 外接june.qas.qa来回去答案信息
		String api = MessageFormat.format(Constants.QAS_API, NetUtil.$(ask), num);
		list = NetUtil.callApi(api, Result.class);
		return list;
	}

}
