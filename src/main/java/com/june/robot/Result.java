/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.robot.Result.java
 * 日期:2017年1月18日
 */
package com.june.robot;

import java.io.Serializable;

/**
 * Result <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月18日 下午11:04:30
 * @version 1.0.0
 */
public class Result implements Serializable{
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 20170118L;
	private String answer;
	private double score;
	
	static Result noAnswer(){
		String answer = "I don`t know";
		double score = 0.5d;
		return new Result(answer, score);
	}
	
	public Result() {
	}

	public Result(String answer, double score) {
		this.answer = answer;
		this.score = score;
	}
	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
}
