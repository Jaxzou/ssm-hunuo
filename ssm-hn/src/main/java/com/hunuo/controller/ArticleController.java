package com.hunuo.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.hunuo.entity.vo.Page;
import com.hunuo.service.ArticleManager;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hunuo.util.AppUtil;
import com.hunuo.util.ObjectExcelView;
import com.hunuo.util.PageData;
import com.hunuo.util.Jurisdiction;
import com.hunuo.util.Tools;


/** 
 * 说明：文章
 */
@Controller
@RequestMapping(value="/article")
public class ArticleController extends BaseController {
	
	String menuUrl = "article/list.do"; //菜单地址(权限用)
	@Resource(name="articleService")
	private ArticleManager articleService;


	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Article");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ADD_USER",Jurisdiction.getUsername());
		pd.put("ADD_TIME",new Date());
		pd.put("MOD_USER",Jurisdiction.getUsername());
		pd.put("MOD_TIME",new Date());
		String status = (String) pd.get("STATUS");
		if(status == null || "".equals("")){
			pd.put("STATUS","off");
		}
		if( status == "on"){
			//获取发布时间，创建定时任务
			String RELEASE_TIME = (String) pd.get("RELEASE");
			System.out.println("创建了定时任务");
		}
		articleService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Article");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		articleService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Article");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		articleService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Article");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = articleService.list(page);	//列出Article列表
		mv.setViewName("web/article/article_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("web/article/article_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = articleService.findById(pd);	//根据ID读取
		mv.setViewName("web/article/article_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Article");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			articleService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Article到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("ID");	//1
		titles.add("文章分类ID");	//2
		titles.add("标题");	//3
		titles.add("图片");	//4
		titles.add("附件");	//5
		titles.add("详细内容");	//6
		titles.add("路径");	//7
		titles.add("备注8");	//8
		titles.add("内容摘要");	//9
		titles.add("添加用户");	//10
		titles.add("添加时间");	//11
		titles.add("修改用户");	//12
		titles.add("修改时间");	//13
		titles.add("备注14");	//14
		titles.add("备注15");	//15
		titles.add("备注16");	//16
		titles.add("备注17");	//17
		titles.add("是否最新");	//18
		titles.add("是否推荐");	//19
		titles.add("是否所有人可访问");	//20
		titles.add("发布状态");	//21
		dataMap.put("titles", titles);
		List<PageData> varOList = articleService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("ID").toString());	//1
			vpd.put("var2", varOList.get(i).get("ARTCLE_KIND_ID").toString());	//2
			vpd.put("var3", varOList.get(i).getString("TITLE"));	    //3
			vpd.put("var4", varOList.get(i).getString("PIC"));	    //4
			vpd.put("var5", varOList.get(i).getString("FILE"));	    //5
			vpd.put("var6", varOList.get(i).getString("CONTENT"));	    //6
			vpd.put("var7", varOList.get(i).getString("URL"));	    //7
			vpd.put("var8", varOList.get(i).get("HIST").toString());	//8
			vpd.put("var9", varOList.get(i).getString("NOTES"));	    //9
			vpd.put("var10", varOList.get(i).getString("ADD_USER"));	    //10
			vpd.put("var11", varOList.get(i).getString("ADD_TIME"));	    //11
			vpd.put("var12", varOList.get(i).getString("MOD_USER"));	    //12
			vpd.put("var13", varOList.get(i).getString("MOD_TIME"));	    //13
			vpd.put("var14", varOList.get(i).getString("PAGE_TITLE"));	    //14
			vpd.put("var15", varOList.get(i).getString("PAGE_KEYWORDS"));	    //15
			vpd.put("var16", varOList.get(i).getString("PAGE_DESCRIPTION"));	    //16
			vpd.put("var17", varOList.get(i).get("SEO_TYPE").toString());	//17
			vpd.put("var18", varOList.get(i).getString("IS_NEW"));	    //18
			vpd.put("var19", varOList.get(i).getString("IS_RECOMMEND"));	    //19
			vpd.put("var20", varOList.get(i).getString("IS_USER"));	    //20
			vpd.put("var21", varOList.get(i).getString("STATUS"));	    //21
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
