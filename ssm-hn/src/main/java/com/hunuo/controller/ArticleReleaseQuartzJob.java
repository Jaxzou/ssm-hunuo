package com.hunuo.controller;

import com.hunuo.service.ArticleManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;


/** quartz 定时任务调度 修改文章发布状态
 */
public class ArticleReleaseQuartzJob{

	@Resource(name = "articleService")
	private ArticleManager articleManager;

	public void release(){
		System.out.println("开始定时修改状态");

		try {
			articleManager.updataStatus();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("结束定时修改状态");
	}

}
