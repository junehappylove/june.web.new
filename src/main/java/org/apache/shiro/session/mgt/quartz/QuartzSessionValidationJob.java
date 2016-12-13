/**
 * 普导慧智软件有限公司<br>
 * june_web_new:org.apache.shiro.session.mgt.quartz.QuartzSessionValidationJob.java
 * 日期:2016年12月11日
 */
package org.apache.shiro.session.mgt.quartz;

import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于Quartz 2.* 版本的实现 <br>
 * 未解决项目中同时使用quartz2.x和shrio的quartz1.6.x所引用jar包冲突问题
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年12月11日 上午1:07:30
 */
public class QuartzSessionValidationJob implements Job {

	/**
	 * Key used to store the session manager in the job data map for this job.
	 */
	public static final String SESSION_MANAGER_KEY = "sessionManager";

	private static final Logger log = LoggerFactory.getLogger(QuartzSessionValidationJob.class);

	/**
	 * Called when the job is executed by quartz. This method delegates to the
	 * <tt>validateSessions()</tt> method on the associated session manager.
	 * 
	 * @param context
	 *            the Quartz job execution context for this execution.
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		ValidatingSessionManager sessionManager = (ValidatingSessionManager) jobDataMap.get(SESSION_MANAGER_KEY);

		if (log.isDebugEnabled()) {
			log.debug("Executing session validation Quartz job...");
		}

		sessionManager.validateSessions();

		if (log.isDebugEnabled()) {
			log.debug("Session validation Quartz job complete.");
		}

	}

}
