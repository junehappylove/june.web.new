/**
 * 中科方德软件有限公司<br>
 * june_web_new:com.june.robot.qas.QasAnswerImpl.java
 * 日期:2017年1月18日
 */
package com.june.robot.qas;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.june.common.Constants;
import com.june.robot.Answer;
import com.june.robot.BaseAnswer;
import com.june.robot.Result;
import com.june.utility.NetUtil;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

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
	@ApiOperation(value="问答接口",httpMethod="GET",response=Result.class,notes="根据问题返回答案")
	public List<Result> getAnswers(@ApiParam(required=true,name="q",value="问题")@PathVariable(value="q")String q, 
				@ApiParam(required=true,name="n",value="结果数目")@PathVariable(value="n")int n) {
		List<Result> list = null;
		if (!NetUtil.canConnect(Constants.QAS_API_)) {
			return null;
		}
		String api = MessageFormat.format(Constants.QAS_API, NetUtil.$(q), n);
		list = NetUtil.callApi(api, Result.class);
		return list;
	}

}
