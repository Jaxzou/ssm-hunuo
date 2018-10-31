package com.hunuo.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import com.hunuo.entity.vo.Page;
import com.hunuo.service.ArticleManager;
import com.hunuo.util.Jurisdiction;
import org.springframework.stereotype.Service;
import com.hunuo.dao.DaoSupport;
import com.hunuo.util.PageData;


/** 
 * 说明： 文章
 */
@Service("articleService")
public class ArticleService implements ArticleManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		pd.put("ADD_USER", Jurisdiction.getUsername());
		pd.put("ADD_TIME",new Date());
		pd.put("MOD_USER",Jurisdiction.getUsername());
		pd.put("MOD_TIME",new Date());
		if(pd.get("IS_NEW") == null){
			pd.put("IS_NEW","off");
		}
		if(pd.get("IS_RECOMMEND") == null){
			pd.put("IS_RECOMMEND","off");
		}
		String status = (String) pd.get("STATUS");
		String releaseTime = (String) pd.get("RELEASE_TIME");
		if(status == null || "".equals(status)){
			pd.put("STATUS","off");
			pd.put("RELEASE_TIME",new Date());
		}
		if( releaseTime != null){
			//获取发布时间，创建定时任务
			System.out.println("创建了定时任务" + releaseTime);
		}
		dao.save("ArticleMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ArticleMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		pd.put("MOD_USER",Jurisdiction.getUsername());
		pd.put("MOD_TIME",new Date());
		if(pd.get("IS_NEW") == null){
			pd.put("IS_NEW","off");
		}
		if(pd.get("IS_RECOMMEND") == null){
			pd.put("IS_RECOMMEND","off");
		}
		String status = (String) pd.get("STATUS");
		String RELEASE_TIME = (String) pd.get("RELEASE_TIME");
		if(status == null || "".equals(status)){
			pd.put("STATUS","off");
			pd.put("RELEASE_TIME",new Date());
		}
		if( status == "on" && RELEASE_TIME != null){
			//获取发布时间，创建定时任务
			System.out.println("创建了定时任务" + RELEASE_TIME);
		}
		dao.update("ArticleMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ArticleMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ArticleMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ArticleMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ArticleMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 定时修改发布状态
	 * @throws Exception
	 */
	@Override
	public void updataStatus() throws Exception {
		dao.update("ArticleMapper.updateStatus",null);
	}

}

