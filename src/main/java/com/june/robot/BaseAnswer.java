/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.robot.BaseAnswer.java
 * 日期:2017年1月19日
 */
package com.june.robot;

import java.util.List;

/**
 * BaseAnswer <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月19日 下午7:21:52
 * @version 1.0.0
 */
public abstract class BaseAnswer implements Answer {

	@Override
	public Result getAnswer(String ask) {
		List<Result> list = getAnswers(ask, 1);
		return list == null || list.size() == 0 ? Result.noAnswer() : list.get(0);
	}

	public List<Result> getAnswers(String ask) {
		return getAnswers(ask, Answer.DEFALUT_ANSWER_NUM);
	}
}
