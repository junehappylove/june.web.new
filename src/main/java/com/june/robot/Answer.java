/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.robot.qas.Result.java
 * 日期:2017年1月18日
 */
package com.june.robot;

/**
 * 机器人聊天交互结果信息接口 Answer <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月18日 下午11:02:48
 * @version 1.0.0
 */
public interface Answer {
	/** 默认返回答案的数目 10条 */
	static int DEFALUT_ANSWER_NUM = 10;

	/**
	 * 获取一条答案
	 * 
	 * @param ask 问题
	 * @return 答案
	 * @date 2017年1月18日 下午11:07:04
	 * @writer junehappylove
	 */
	Result getAnswer(String ask);

	/**
	 * 获取一组答案
	 * 
	 * @param ask 问题
	 * @param num 返回答案个数
	 * @return 答案集
	 * @date 2017年1月18日 下午11:07:16
	 * @writer junehappylove
	 */
	java.util.List<Result> getAnswers(String ask, int num);

}
