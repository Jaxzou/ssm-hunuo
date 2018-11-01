package com.hunuo.util;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 下载html中的图片并替换
 */
public class UpdateImg {
    /*
     *获取图片连接并判断是否为网址
     */
    public static String getImgStr(String count) throws Exception {
        Pattern p = Pattern.compile("<(img|IMG)(.*?)(>|></img>|/>)");//匹配字符串中的img标签
        Matcher matcher = p.matcher(count);
        boolean hasPic = matcher.find();
        if (hasPic == true) {//判断是否含有图片
            while (hasPic) { //如果含有图片，那么持续进行查找，直到匹配不到
                String group = matcher.group();
                if (group.contains("data-src") == true) {
                    String src = group.substring(group.lastIndexOf("src"));
                    Pattern srcText = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");//匹配图片的地址
                    Pattern srcText2 = Pattern.compile("(data-src|DATA-SRC)=(\"|\')(.*?)(\"|\')");//匹配图片的地址
                    Matcher matcher2 = srcText2.matcher(group);
                    Matcher matcher3 = srcText.matcher(src);
                    //判断是否为域名，不是域名则下载到服务器
                    Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
                    if (matcher2.find() == true && pattern.matcher(matcher2.group()).matches() == false && matcher3.find() == true) {
                        URL url = new URL(matcher2.group().substring(10, matcher2.group().length() - 1));
                        URL url1 = new URL(matcher3.group().substring(5, matcher3.group().length() - 1));
                        String last = getUrl(String.valueOf(url));
                        String uuid32 = getUUID32();

                        String fileName = uuid32 + last;
                        MultipartFile file = createImg("" + url, last.substring(1, last.length()));
                        String ffile = DateUtil.getDays();
                        String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;

                        String fileUp = FileUpload.fileUp(file, filePath, fileName);

                        //替换图片url
                        String replaceHttp = "/uploadFiles/uploadImgs/" + ffile + "/" + fileUp;
                        String countx = count.replace(String.valueOf(url), replaceHttp);
                        count = countx;
                    }
                } else {
                    Pattern srcText = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");//匹配图片的地址
                    Matcher matcher2 = srcText.matcher(group);
                    Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
                    if (matcher2.find() == true && pattern.matcher(matcher2.group()).matches() == false) {
                        URL url = new URL(matcher2.group().substring(5, matcher2.group().length() - 1));
                        String last = getUrl(String.valueOf(url));
                        String uuid32 = getUUID32();

                        String fileName = uuid32;
                        MultipartFile file = createImg("" + url, last.substring(1, last.length()));
                        String ffile = DateUtil.getDays();
                        String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + ffile;

                        String fileUp = FileUpload.fileUp(file, filePath, fileName);

                        //替换图片url
                        String replaceHttp = "/uploadFiles/uploadImgs/" + ffile + "/" + fileUp;
                        String countx = count.replace(String.valueOf(url), replaceHttp);
                        count = countx;
                    }
                }
                hasPic = matcher.find();//判断是否还有img标签
            }
        }
        return count;
    }

    /*
     *生成随机32位数字
     */
    public static String getUUID32() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }

    /*
     *删除文件
     */
    public static void deleteFile(File... files) {
        File[] var2 = files;
        int var3 = files.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            File file = var2[var4];
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /*
     *判断链接是否带有？后缀
     */
    public static String getUrl(String url) {
        if (!String.valueOf(url).contains("?")) {
            String last = url.substring(url.lastIndexOf("."));
            return last;
        } else {
            String lastIndex = url.substring(0, url.indexOf("?"));
            String last = lastIndex.substring(lastIndex.lastIndexOf("."));
            return last;
        }
    }

    /**
     * 根据url拿取file
     *
     * @param url
     * @param suffix 文件后缀名
     */
    public static File createFileByUrl(String url, String suffix) {
        byte[] byteFile = getImageFromNetByUrl(url);
        if (byteFile != null) {
            File file = getFileFromBytes(byteFile, suffix);
            return file;
        } else {
            return null;
        }
    }

    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl 网络连接地址
     * @return
     */
    private static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    // 创建临时文件
    private static File getFileFromBytes(byte[] b, String suffix) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = File.createTempFile("pattern", "." + suffix);
            System.out.println("临时文件位置：" + file.getCanonicalPath());
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 转换
     * @param url 文件路径
     * @param last 后缀
     * @return
     */
    public static MultipartFile createImg(String url,String last){
        try {
            // File转换成MutipartFile
            File file = createFileByUrl(url, last);
            FileInputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(),url.substring(url.lastIndexOf("/"),url.length()), "application/octet-stream",inputStream);
            //注意这里面填啥，MultipartFile里面对应的参数就有啥，比如我只填了name，则
            //MultipartFile.getName()只能拿到name参数，但是originalFilename是空。
            return multipartFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        try {
            String imgStr = UpdateImg.getImgStr("<p style=\"text-align: center;\"><span style=\"font-size:14px;\"><span style=\"font-family: 宋体;\">　　</span></span><img alt=\"\" src=\"http://www.kingmed.com.cn/UploadFiles/main/Images/2016/3/20160303105022.jpg\" style=\"text-align: center; font-size: 14px; height: 740px; font-family: calibri; width: 740px;\"></p>");
            System.out.println(imgStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
