package com.hunuo.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.hunuo.entity.vo.Page;
import com.hunuo.service.PicturesManager;
import com.hunuo.util.*;
import org.apache.poi.hslf.blip.PNG;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/**
 * 类名称：图片/文件 管理
 */
@Controller
@RequestMapping(value = "/pictures")
public class PicturesController extends BaseController {

    String menuUrl = "pictures/list.do"; //菜单地址(权限用)
    @Resource(name = "picturesService")
    private PicturesManager picturesService;


    /**
     * 列表
     *
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String KEYW = pd.getString("keyword");    //检索条件
        if (null != KEYW && !"".equals(KEYW)) {
            pd.put("KEYW", KEYW.trim());
        }
        page.setPd(pd);
        List<PageData> varList = picturesService.list(page);    //列出Pictures列表
        mv.setViewName("information/pictures/pictures_list");
        mv.addObject("varList", varList);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC());    //按钮权限
        return mv;
    }

    /**
     * 列表(用于弹窗)
     *
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listfortc")
    public ModelAndView listfortc(Page page) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String KEYW = pd.getString("keyword");    //检索条件
        if (null != KEYW && !"".equals(KEYW)) {
            pd.put("KEYW", KEYW.trim());
        }
        page.setPd(pd);
        List<PageData> varList = picturesService.list(page);    //列出Pictures列表
        mv.setViewName("information/pictures/pictures_list_tc");
        mv.addObject("varList", varList);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC());    //按钮权限
        return mv;
    }

    /**
     * 新增
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(@RequestParam(required = false) MultipartFile file) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        logBefore(logger, Jurisdiction.getUsername() + "新增图片");

        String ffile = DateUtil.getDays(), fileName = "";
        PageData pd = new PageData();
        if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            if (null != file && !file.isEmpty()) {
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;        //文件上传路径
                fileName = FileUpload.fileUp(file, filePath, this.get32UUID());                //执行上传
            } else {
                System.out.println("上传失败");
            }
            pd.put("PICTURES_ID", this.get32UUID());            //主键
            pd.put("TITLE", "图片");                                //标题
            pd.put("NAME", fileName);                            //文件名
            pd.put("PATH", ffile + "/" + fileName);                //路径
            pd.put("CREATETIME", Tools.date2Str(new Date()));    //创建时间
            pd.put("MASTER_ID", "1");                            //附属与
            pd.put("BZ", "图片管理处上传");                        //备注
            Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);//加水印
            picturesService.save(pd);
        }
        map.put("result", "ok");
        map.put("PATH", "/uploadFiles/uploadImgs/" + ffile + "/" + fileName);
        return AppUtil.returnObject(pd, map);
    }

    private String ffile = DateUtil.getDays();

    /**
     * 文章图片上传
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/savePic")
    @ResponseBody
    public Object savePic(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        Map<String, Object> map = new HashMap<String, Object>();
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        logBefore(logger, Jurisdiction.getUsername() + "新增图片");

        String fileName = "";
        PageData pd = new PageData();
        if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            Iterator item = multipartRequest.getFileNames();
            while (item.hasNext()) {

                fileName = (String) item.next();

                MultipartFile file = multipartRequest.getFile(fileName);

                if (null != file && !file.isEmpty()) {
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;        //文件上传路径
                    fileName = FileUpload.fileUp(file, filePath, this.get32UUID());                //执行上传
                } else {
                    System.out.println("上传失败");
                }
                pd.put("PICTURES_ID", this.get32UUID());            //主键
                pd.put("TITLE", "图片");                                //标题
                pd.put("NAME", fileName);                            //文件名
                pd.put("PATH", ffile + "/" + fileName);                //路径
                pd.put("CREATETIME", Tools.date2Str(new Date()));    //创建时间
                pd.put("MASTER_ID", "1");                            //附属与
                pd.put("BZ", "文章文件上传");                        //备注
                Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);//加水印
                picturesService.save(pd);
            }
            map.put("error", 0);
            map.put("url", "/uploadFiles/uploadImgs/" + ffile + "/" + fileName);
            return AppUtil.returnObject(pd, map);

        }
        return null;
    }

    /**
     * 图片空间
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/fileManager", method = RequestMethod.GET)
    public void fileManager(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        ServletContext application = request.getSession().getServletContext();
        ServletOutputStream out = response.getOutputStream();
        // 根目录路径，可以指定绝对路径，比如 /var/www/attached/
        String rootPath = PathUtil.getClasspath() + Const.FILEPATHIMG + "/";
        // 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
        String rootUrl = "http://127.0.0.1:8080/uploadFiles/uploadImgs/";
        // 图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

        String dirName = request.getParameter("dir");
        if (dirName != null) {
            if (!Arrays.<String>asList(
                    new String[]{"image", "flash", "media", "file"})
                    .contains(dirName)) {
                out.println("Invalid Directory name.");
                return;
            }
            //rootPath += dirName + "/";
            //rootUrl += dirName + "/";
            File saveDirFile = new File(rootPath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
        }
        // 根据path参数，设置各路径和URL
        String path = request.getParameter("path") != null ? request
                .getParameter("path") : "";
        String currentPath = rootPath + path;
        String currentUrl = rootUrl + path;
        String currentDirPath = path;
        String moveupDirPath = "";
        if (!"".equals(path)) {
            String str = currentDirPath.substring(0,
                    currentDirPath.length() - 1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0,
                    str.lastIndexOf("/") + 1) : "";
        }

        // 排序形式，name or size or type
        String order = request.getParameter("order") != null ? request
                .getParameter("order").toLowerCase() : "name";

        // 不允许使用..移动到上一级目录
        if (path.indexOf("..") >= 0) {
            out.println("Access is not allowed.");
            return;
        }
        // 最后一个字符不是/
        if (!"".equals(path) && !path.endsWith("/")) {
            out.println("Parameter is not valid.");
            return;
        }
        // 目录不存在或不是目录
        File currentPathFile = new File(currentPath);
        if (!currentPathFile.isDirectory()) {
            out.println("Directory does not exist.");
            return;
        }
        // 遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt = fileName.substring(
                            fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes)
                            .contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }

        if ("size".equals(order)) {
            Collections.sort(fileList, new SizeComparator());
        } else if ("type".equals(order)) {
            Collections.sort(fileList, new TypeComparator());
        } else {
            Collections.sort(fileList, new NameComparator());
        }
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("moveup_dir_path", moveupDirPath);
        msg.put("current_dir_path", currentDirPath);
        msg.put("current_url", currentUrl);
        msg.put("total_count", fileList.size());
        msg.put("file_list", fileList);
        response.setContentType("application/json; charset=UTF-8");
        String msgStr = JSON.toJSONString(msg);
        out.println(msgStr);
    }

    class NameComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir"))
                    && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir"))
                    && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String) hashA.get("filename"))
                        .compareTo((String) hashB.get("filename"));
            }
        }
    }

    class SizeComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir"))
                    && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir"))
                    && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                if (((Long) hashA.get("filesize")) > ((Long) hashB
                        .get("filesize"))) {
                    return 1;
                } else if (((Long) hashA.get("filesize")) < ((Long) hashB
                        .get("filesize"))) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    class TypeComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir"))
                    && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir"))
                    && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String) hashA.get("filetype"))
                        .compareTo((String) hashB.get("filetype"));
            }
        }
    }

    /**
     * 保存附件
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveFile")
    @ResponseBody
    public Object saveFile(@RequestParam(required = false) MultipartFile file) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        logBefore(logger, Jurisdiction.getUsername() + "上附件");

        String ffile = DateUtil.getDays(), fileName = "";
        PageData pd = new PageData();
        if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            if (null != file && !file.isEmpty()) {
                String filePath = PathUtil.getClasspath() + Const.FILEPATHFILEOA + ffile;        //文件上传路径
                fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
                fileName = FileUpload.fileUp(file, filePath, fileName);                //执行上传
            } else {
                System.out.println("上传失败");
            }
            pd.put("PICTURES_ID", this.get32UUID());            //主键
            pd.put("TITLE", "附件");                                //标题
            pd.put("NAME", fileName);                            //文件名
            pd.put("PATH", ffile + "/" + fileName);                //路径
            pd.put("CREATETIME", Tools.date2Str(new Date()));    //创建时间
            pd.put("MASTER_ID", "1");                            //附属与
            pd.put("BZ", "附件上传");                        //备注
            picturesService.save(pd);
        }
        map.put("result", "ok");
        map.put("PATH", ffile + "/" + fileName);
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 删除
     *
     * @param out
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) throws Exception {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        logBefore(logger, Jurisdiction.getUsername() + "删除图片");
        PageData pd = new PageData();
        if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            pd = this.getPageData();
            if (Tools.notEmpty(pd.getString("PATH").trim())) {
                DelAllFile.delFolder(PathUtil.getClasspath() + Const.FILEPATHIMG + pd.getString("PATH")); //删除图片
            }
            picturesService.delete(pd);
        }
        out.write("success");
        out.close();
    }

    /**
     * 修改
     *
     * @param request
     * @param file
     * @param tpz
     * @param PICTURES_ID
     * @param TITLE
     * @param MASTER_ID
     * @param BZ
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit(
            HttpServletRequest request,
            @RequestParam(value = "tp", required = false) MultipartFile file,
            @RequestParam(value = "tpz", required = false) String tpz,
            @RequestParam(value = "PICTURES_ID", required = false) String PICTURES_ID,
            @RequestParam(value = "TITLE", required = false) String TITLE,
            @RequestParam(value = "MASTER_ID", required = false) String MASTER_ID,
            @RequestParam(value = "BZ", required = false) String BZ
    ) throws Exception {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        logBefore(logger, Jurisdiction.getUsername() + "修改图片");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            pd.put("PICTURES_ID", PICTURES_ID);        //图片ID
            pd.put("TITLE", TITLE);                    //标题
            pd.put("MASTER_ID", MASTER_ID);            //属于ID
            pd.put("BZ", BZ);                        //备注
            if (null == tpz) {
                tpz = "";
            }
            String ffile = DateUtil.getDays(), fileName = "";
            if (null != file && !file.isEmpty()) {
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;    //文件上传路径
                fileName = FileUpload.fileUp(file, filePath, this.get32UUID());            //执行上传
                pd.put("PATH", ffile + "/" + fileName);                                    //路径
                pd.put("NAME", fileName);
            } else {
                pd.put("PATH", tpz);
            }
            Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);//加水印
            picturesService.edit(pd);                //执行修改数据库
        }
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去新增页面
     *
     * @return
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("information/pictures/pictures_add");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 去修改页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = picturesService.findById(pd);    //根据ID读取
        mv.setViewName("information/pictures/pictures_edit");
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 批量删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() throws Exception {
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        pd = this.getPageData();
        if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            List<PageData> pdList = new ArrayList<PageData>();
            List<PageData> pathList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                pathList = picturesService.getAllById(ArrayDATA_IDS);
                for (int i = 0; i < pathList.size(); i++) {
                    if (Tools.notEmpty(pathList.get(i).getString("PATH").trim())) {
                        DelAllFile.delFolder(PathUtil.getClasspath() + Const.FILEPATHIMG + pathList.get(i).getString("PATH"));//删除图片
                    }
                }
                picturesService.deleteAll(ArrayDATA_IDS);
                pd.put("msg", "ok");
            } else {
                pd.put("msg", "no");
            }
            pdList.add(pd);
            map.put("list", pdList);
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 删除图片
     *
     * @param out
     * @throws Exception
     */
    @RequestMapping(value = "/deltp")
    public void deltp(PrintWriter out) throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        String PATH = pd.getString("PATH");                                                            //图片路径
        DelAllFile.delFolder(PathUtil.getClasspath() + Const.FILEPATHIMG + pd.getString("PATH"));    //删除图片
        if (PATH != null) {
            picturesService.delTp(pd);                                                                //删除数据库中图片数据
        }
        out.write("success");
        out.close();
    }

    /**
     * 去图片爬虫页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goImageCrawler")
    public ModelAndView goImageCrawler() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("information/pictures/imageCrawler");
        return mv;
    }

    /**
     * 请求连接获取网页中每个图片的地址
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/getImagePath")
    @ResponseBody
    public Object getImagePath() {
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        List<String> imgList = new ArrayList<String>();
        String errInfo = "success";
        String serverUrl = pd.getString("serverUrl");    //网页地址
        String msg = pd.getString("msg");                //msg:save 时保存到服务器
        if (!serverUrl.startsWith("http://")) {            //检验地址是否http://
            errInfo = "error";                            //无效地址
        } else {
            try {
                imgList = GetWeb.getImagePathList(serverUrl);
                if ("save".equals(msg)) {
                    String ffile = DateUtil.getDays();
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;        //文件上传路径
                    for (int i = 0; i < imgList.size(); i++) {    //把网络图片保存到服务器硬盘，并数据库记录
                        String fileName = FileUpload.getHtmlPicture(imgList.get(i), filePath, null);                                //下载网络图片上传到服务器上
                        //保存到数据库
                        pd.put("PICTURES_ID", this.get32UUID());            //主键
                        pd.put("TITLE", "图片");                                //标题
                        pd.put("NAME", fileName);                            //文件名
                        pd.put("PATH", ffile + "/" + fileName);                //路径
                        pd.put("CREATETIME", Tools.date2Str(new Date()));    //创建时间
                        pd.put("MASTER_ID", "1");                            //附属与
                        pd.put("BZ", serverUrl + "爬取");                        //备注
                        Watermark.setWatemark(PathUtil.getClasspath() + Const.FILEPATHIMG + ffile + "/" + fileName);//加水印
                        picturesService.save(pd);
                    }
                }
            } catch (Exception e) {
                errInfo = "error";                        //出错
            }
        }
        map.put("imgList", imgList);                    //图片集合
        map.put("result", errInfo);                        //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}
