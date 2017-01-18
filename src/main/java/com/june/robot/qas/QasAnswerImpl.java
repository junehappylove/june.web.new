/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.robot.qas.QasAnswerImpl.java
 * 日期:2017年1月18日
 */
package com.june.robot.qas;

import java.util.ArrayList;
import java.util.List;

import com.june.common.Constants;
import com.june.robot.Answer;
import com.june.robot.Result;
import com.june.utility.MessageUtil;

/**
 * QasAnswerImpl <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月18日 下午11:13:31
 * @version 1.0.0
 */
public class QasAnswerImpl implements Answer {

	private static QasAnswerImpl instance = new QasAnswerImpl();

	private QasAnswerImpl() {
	}

	public static QasAnswerImpl instance() {
		return instance == null ? new QasAnswerImpl() : instance;
	}

	@Override
	public Result getAnswer(String ask) {
		return getAnswers(ask, 1).get(0);
	}

	@Override
	public List<Result> getAnswers(String ask, int num) {
		List<Result> list = null;
		// TODO 外接june.qas.qa来回去答案信息
		String url = MessageUtil.$VALUE(Constants.QAS_API, ask, num + "");
		System.out.println(url);
		Result r = new Result(url,1.0d);
		list = new ArrayList<>();
		list.add(r);
		return list;
	}

	public List<Result> getAnswers(String ask) {
		return getAnswers(ask, Answer.DEFALUT_ANSWER_NUM);
	}

}
