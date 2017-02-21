/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.robot.qas.QasAnswerImpl.java
 * 日期:2017年1月18日
 */
package com.june.robot.qas;

import java.text.MessageFormat;
import java.util.List;

import com.june.common.Constants;
import com.june.robot.Answer;
import com.june.robot.BaseAnswer;
import com.june.robot.Result;
import com.june.util.NetUtil;

/**
 * QasAnswerImpl <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月18日 下午11:13:31
 * @version 1.0.0
 */
public class QasAnswerImpl extends BaseAnswer implements Answer {

	private static QasAnswerImpl instance = new QasAnswerImpl();

	private QasAnswerImpl() {
	}

	public static QasAnswerImpl instance() {
		return instance == null ? new QasAnswerImpl() : instance;
	}

	@Override
	public List<Result> getAnswers(String q, int n) {
		List<Result> list = null;
		if (!NetUtil.canConnect(Constants.QAS_API_)) {
			return null;
		}
		String api = MessageFormat.format(Constants.QAS_API, NetUtil.$(q), n);
		list = NetUtil.callApi(api, Result.class);
		return list;
	}

}
