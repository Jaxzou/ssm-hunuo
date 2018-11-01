<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/ace/css/chosen.css"/>
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp" %>
    <!-- 日期框 -->
    <link rel="stylesheet" href="static/ace/css/datepicker.css"/>
    <!-- 富文本编辑器 -->
    <link rel="stylesheet" href="static/kindeditor/themes/default/default.css"/>
    <script charset="utf-8" type="text/javascript" src="static/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" type="text/javascript" src="static/kindeditor/lang/zh-CN.js"></script>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">

                        <form action="article/${msg }.do" name="Form" id="Form" method="post">
                            <input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
                            <div id="zhongxin" style="padding-top: 13px;">
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">文章所属分类:</td>
                                        <td>
                                            <select id="ARTCLE_KIND_ID" name="ARTCLE_KIND_ID" class="form-control input-sm" style="width:1200px;"></select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">标题:</td>
                                        <td><input type="text" name="TITLE" id="TITLE" value="${pd.TITLE}" maxlength="255" placeholder="这里输入标题" title="标题" style="width:1200px;"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">内容摘要:</td>
                                        <td><input type="text" name="NOTES" id="NOTES" value="${pd.NOTES}" maxlength="500" placeholder="这里输入内容摘要" title="内容摘要"
                                                   style="width:1200px;height: 100px"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">详细内容:</td>
                                        <td>
                                            <textarea id="CONTENT" name="CONTENT" style="width:1200px;height:400px;visibility:hidden;">${pd.CONTENT}</textarea>
                                        </td>
                                    </tr>


                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">上传图片:</td>
                                        <td>
                                            <div class="col-lg-10">
                                                <div class="upload_1">
                                                    <div class="uploadArea_1">
                                                        <img width="300" height="200" id="PICPATH" src="${pd.PIC}">
                                                    </div>
                                                    <div class="left">
                                                        <div class="uploadProgressBar"></div>
                                                        <div class="uploadBtn_1 webuploader-container">
                                                            <div style="position: absolute; top: 0px; left: 0px; width: 82px; height: 38px; overflow: hidden; bottom: auto; right: auto;">
                                                                <label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="clear"></div>
                                                </div>
                                                <input data-val="true" data-val-maxlength-max="50" id="PIC" name="PIC" value="${pd.PIC}" type="hidden">
                                                <span class="field-validation-valid" data-valmsg-for="QrCode" data-valmsg-replace="true"></span>
                                            </div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;height: 70px">上传附件:</td>
                                        <td >
                                            <div class="col-lg-10">
                                                <div class="upload_1">
                                                    <div class="left">
                                                        <div class="uploadProgressBar"></div>
                                                        <div class="uploadBtn_1 webuploader-container">
                                                            <div id="FILE" style="position: absolute; top: 0px; left: 0px; width: 82px; height: 38px; overflow: hidden; bottom: auto; right: auto;">
                                                                <label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="clear"></div>
                                                </div>
                                                <input data-val="true" data-val-maxlength-max="50" id="FILEURL" name="FILE" value="${pd.FILE}" type="hidden">
                                                <span class="field-validation-valid" data-valmsg-for="QrCode" data-valmsg-replace="true"></span>
                                                请上传 zip、rar 格式文件
                                                <p/><span id="FILEPATH" style="color: red;">${pd.FILE}</span>
                                            </div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">属性:</td>
                                        <td>

                                            <input type="checkbox" id="STATUS" name="STATUS">
                                            <label for="STATUS"></label> 是否隐藏&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--是否隐藏-->

                                            <input type="checkbox" id="IS_NEW" name="IS_NEW">
                                            <label for="IS_NEW"></label> 是否最新&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--是否最新-->

                                            <input type="checkbox" id="IS_RECOMMEND" name="IS_RECOMMEND">
                                            <label for="IS_RECOMMEND"></label> 是否推荐&nbsp;&nbsp;<!--是否推荐-->

                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">访问权限:</td>
                                        <td>
                                            <select id="IS_USER" name="IS_USER" class="form-control input-sm" style="width:150px;"></select>
                                        </td>
                                    </tr>


                                    <tr id="sample" style="display:none">
                                        <td style="width:75px;text-align: right;padding-top: 13px;">发布时间</td>
                                        <td><input id="RELEASE_TIME" name="RELEASE_TIME" value="${pd.RELEASE_TIME}"></td>
                                    </tr>

                                    <tr>
                                        <td style="text-align: center;" colspan="10">
                                            <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                                            <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif"/><br/><h4
                                    class="lighter block green">提交中...</h4></div>
                        </form>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
    </div>
    <!-- /.main-content -->
</div>
<!-- /.main-container -->


<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp" %>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript" src="static/layui/layui.js"></script>
<script type="text/javascript">
    $(top.hangge());
    layui.use(['layer', 'laydate'], function () {
        var layer = layui.layer;
    });

    var articleKindId = "${pd.ARTCLE_KIND_ID}";
    var permission = "${pd.IS_USER}";

    $(function () {
        var status = '${pd.STATUS}';
        var isNew = '${pd.IS_NEW}';
        var isRecommend = '${pd.IS_RECOMMEND}';

        if(status == 'on'){
            $('#STATUS').prop("checked",true);
            var tr1 = document.getElementById("sample");
            //选中打开发布时间选择
            tr1.removeAttribute("style");
        }
        if(isNew == 'on'){
            $('#IS_NEW').prop("checked",true);
        }
        if(isRecommend == 'on'){
            $('#IS_RECOMMEND').prop("checked",true);
        }

        $.ajax({
            type: 'get',
            url: '/articlekind/getList',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                var c1;
                var c2 = "<option value=\"0\">--请选择分类--</option>";
                $(data.articleKindList).each(function (i) {
                    var t = data.articleKindList[i];
                    if (articleKindId != "" && t.ID == articleKindId) {
                        c1 = "<option value=" + t.ID + ">--" + t.NAME + "--</option>";
                    } else {
                        c2 += "<option value=" + t.ID + ">--" + t.NAME + "--</option>";
                    }
                });
                $("#ARTCLE_KIND_ID").append(c1 + c2);
            }
        });

        if(permission == 1 && permission != ""){
            $('#IS_USER').append("<option value=\"1\">所有人可访问</option>\n" +
                "<option value=\"2\">仅限会员访问</option>");
        }else{
            $('#IS_USER').append("<option value=\"2\">仅限会员访问</option>\n" +
                "<option value=\"1\">所有人可访问</option>");
        }
    });


    KindEditor.ready(function (K) {
        KindEditor.ready(function (K) {
            window.editor = K.create('textarea[name="CONTENT"]', {
                //指定上传文件请求的url。
                uploadJson: '/pictures/savePic',
                //允许上传图片
                allowImageUpload: true,
                //修改图片空间的地址
                fileManagerJson:'/pictures/fileManager',
                //允许使用图片空间
                allowFileManager : true
            });
        });
    });

    //上传图片
    layui.use('upload', function(){
        var upload = layui.upload;
        upload.render({
            elem: '#PIC' //绑定元素
            ,accept: 'images' //允许上传的文件类型
            ,url: '/pictures/save' //上传接口
            ,done: function(res, index, upload){
                layer.msg("上传成功");
                var pro = window.location.protocol;
                var host = window.location.host;
                var domain = pro + "//" + host;
                var url = res.PATH;
                url = domain +  url;
                $("#PICPATH").attr("src",url);
                $("#PIC").val(res.PATH);
            }
        });
    });

    //上传文件
    layui.use('upload', function(){
        var upload = layui.upload;
        upload.render({
            elem: '#FILE' //绑定元素
            ,accept: 'file' //允许上传的文件类型
            //,exts:'pdf|docx|pptx|xlsx|doc|ppt|xls|zip|rar'
            ,exts:'zip|rar'
            ,url: '/pictures/saveFile' //上传接口
            ,done: function(res, index, upload){
                layer.msg("上传成功");
                $("#FILEURL").val("/uploadFiles/uploadImgs/" +res.PATH);
                $("#FILEPATH").html(res.PATH);
            }
        });
    });

    //是否隐藏监听
    $("#STATUS").change(function() {
        var tr1 = document.getElementById("sample");
        var status = $("#STATUS").is(':checked');
        if(status){
            //选中打开发布时间选择
            tr1.removeAttribute("style");
        }else{
            //不选中，即立刻发布
            tr1.style.display = 'none';
            $('releaseTime').val('');//置空时间
        }
    });

    //时间控件
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#RELEASE_TIME',
            type: 'datetime'
        });
    });

    //保存
    function save() {
        // 将编辑器的HTML数据同步到textarea
        editor.sync();
        if ($("#TITLE").val() == "") {
            $("#TITLE").tips({
                side: 3,
                msg: '请输入标题',
                bg: '#AE81FF',
                time: 2
            });
            $("#TITLE").focus();
            return false;
        }
        if ($("#CONTENT").val() == "") {
            $("#CONTENT").tips({
                side: 3,
                msg: '请输入详细内容',
                bg: '#AE81FF',
                time: 2
            });
            $("#CONTENT").focus();
            return false;
        }
        if ($("#NOTES").val() == "") {
            $("#NOTES").tips({
                side: 3,
                msg: '请输入内容摘要',
                bg: '#AE81FF',
                time: 2
            });
            $("#NOTES").focus();
            return false;
        }
        var status = $("#STATUS").is(':checked');
        if (status && $("#RELEASE_TIME").val() == "") {
            $("#STATUS").tips({
                side: 3,
                msg: '请选择立即发布或设定发布时间',
                bg: '#AE81FF',
                time: 2
            });
            $("#STATUS").focus();
            return false;
        }
        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();
    }

</script>
</body>
</html>