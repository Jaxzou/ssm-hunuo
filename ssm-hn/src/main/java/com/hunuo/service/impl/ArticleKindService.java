package com.hunuo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.hunuo.entity.vo.Page;
import com.hunuo.service.ArticleKindManager;
import org.springframework.stereotype.Service;
import com.hunuo.dao.DaoSupport;
import com.hunuo.util.PageData;


/** 
 * 说明： 内容分类
 */
@Service("articlekindService")
public class ArticleKindService implements ArticleKindManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ArticleKindMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ArticleKindMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ArticleKindMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ArticleKindMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ArticleKindMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ArticleKindMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ArticleKindMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public Map<String,Object> getList() throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		List<PageData> articleKindList = (List<PageData>) dao.findForList("ArticleKindMapper.listAll", null);
		map.put("articleKindList",articleKindList);
		return map;
	}

}

