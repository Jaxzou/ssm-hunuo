package com.hunuo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.hunuo.dao.DaoSupport;
import com.hunuo.entity.vo.Page;
import com.hunuo.service.TimingBackUpManager;
import com.hunuo.util.PageData;
import org.springframework.stereotype.Service;


/** 
 * 说明： 定时备份
 */
@Service("timingbackupService")
public class TimingBackUpService implements TimingBackUpManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TimingBackUpMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TimingBackUpMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TimingBackUpMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TimingBackUpMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TimingBackUpMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TimingBackUpMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TimingBackUpMapper.deleteAll", ArrayDATA_IDS);
	}

	/**切换状态
	 * @param pd
	 * @throws Exception
	 */
	public void changeStatus(PageData pd) throws Exception {
		dao.update("TimingBackUpMapper.changeStatus", pd);
	}
	
}

