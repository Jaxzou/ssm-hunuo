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
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp" %>
    <style type="text/css">
        .yulantu {
            z-index: 9999999999999999;
            position: absolute;
            border: 2px solid #76ACCD;
            display: none;
        }
    </style>
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

                        <form action="information/${msg }.do" name="Form" id="Form" method="post">
                            <input type="hidden" name="INFORMATION_ID" id="INFORMATION_ID" value="${pd.INFORMATION_ID}"/>
                            <div id="zhongxin" style="padding-top: 13px;">
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:100px;text-align: right;padding-top: 13px;">网站名称:</td>
                                        <td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="30" placeholder="这里输入网站名称" title="网站名称" style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">官网:</td>
                                        <td><input type="text" name="WEBURL" id="WEBURL" value="${pd.WEBURL}" maxlength="50" placeholder="这里输入官网" title="官网" style="width:98%;"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">电话:</td>
                                        <td><input type="text" name="TEL" id="TEL" value="${pd.TEL}" maxlength="20" placeholder="这里输入电话" title="电话" style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">邮箱:</td>
                                        <td><input type="text" name="EMAIL" id="EMAIL" value="${pd.EMAIL}" maxlength="50" placeholder="这里输入邮箱" title="邮箱" style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">QQ:</td>
                                        <td><input type="text" name="QQ" id="QQ" value="${pd.QQ}" maxlength="20" placeholder="这里输入QQ" title="QQ" style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">传真:</td>
                                        <td><input type="text" name="FAX" id="FAX" value="${pd.FAX}" maxlength="20" placeholder="这里输入传真" title="传真" style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
                                        <td><input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" maxlength="100" placeholder="这里输入地址" title="地址"
                                                   style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">LOGO:</td>
                                        <td>
                                            <div class="col-lg-10">
                                                <div class="upload_1">
                                                    <div class="uploadArea_1">
                                                        <img width="300" height="200" id="logopath" src="${pd.LOGO}">
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
                                                <input data-val="true" data-val-maxlength-max="50" id="LOGO" name="LOGO" value="${pd.LOGO}" type="hidden">
                                                <span class="field-validation-valid" data-valmsg-for="QrCode" data-valmsg-replace="true"></span>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">版权:</td>
                                        <td><input type="text" name="COPYRIGHT" id="COPYRIGHT" value="${pd.COPYRIGHT}" maxlength="20" placeholder="这里输入版权" title="版权"
                                                   style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">备案:</td>
                                        <td><input type="text" name="BEIAN" id="BEIAN" value="${pd.BEIAN}" maxlength="15" placeholder="这里输入备案" title="备案" style="width:98%;"/></td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">关于我们:</td>
                                        <td><input type="text" name="ABOUTUS1" id="ABOUTUS1" value="${pd.ABOUTUS1}" maxlength="255" placeholder="这里输入关于我们(上)" title="关于我们(上)"
                                                   style="width:98%;"/></td>
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

    //保存
    function save() {
        if ($("#NAME").val() == "") {
            $("#NAME").tips({
                side: 3,
                msg: '请输入网站名称',
                bg: '#AE81FF',
                time: 2
            });
            $("#NAME").focus();
            return false;
        }
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
        if ($("#KEYWORDS").val() == "") {
            $("#KEYWORDS").tips({
                side: 3,
                msg: '请输入关键词',
                bg: '#AE81FF',
                time: 2
            });
            $("#KEYWORDS").focus();
            return false;
        }
        if ($("#DESCRIPTION").val() == "") {
            $("#DESCRIPTION").tips({
                side: 3,
                msg: '请输入描述',
                bg: '#AE81FF',
                time: 2
            });
            $("#DESCRIPTION").focus();
            return false;
        }
        if ($("#LOGO").val() == "") {
            $("#LOGO").tips({
                side: 3,
                msg: '请输入logo',
                bg: '#AE81FF',
                time: 2
            });
            $("#LOGO").focus();
            return false;
        }
        if ($("#TEL").val() == "") {
            $("#TEL").tips({
                side: 3,
                msg: '请输入电话',
                bg: '#AE81FF',
                time: 2
            });
            $("#TEL").focus();
            return false;
        }
        if ($("#EMAIL").val() == "") {
            $("#EMAIL").tips({
                side: 3,
                msg: '请输入邮箱',
                bg: '#AE81FF',
                time: 2
            });
            $("#EMAIL").focus();
            return false;
        }
        if ($("#QQ").val() == "") {
            $("#QQ").tips({
                side: 3,
                msg: '请输入QQ',
                bg: '#AE81FF',
                time: 2
            });
            $("#QQ").focus();
            return false;
        }
        if ($("#FAX").val() == "") {
            $("#FAX").tips({
                side: 3,
                msg: '请输入传真',
                bg: '#AE81FF',
                time: 2
            });
            $("#FAX").focus();
            return false;
        }
        if ($("#WEBURL").val() == "") {
            $("#WEBURL").tips({
                side: 3,
                msg: '请输入官网',
                bg: '#AE81FF',
                time: 2
            });
            $("#WEBURL").focus();
            return false;
        }
        if ($("#ADDRESS").val() == "") {
            $("#ADDRESS").tips({
                side: 3,
                msg: '请输入地址',
                bg: '#AE81FF',
                time: 2
            });
            $("#ADDRESS").focus();
            return false;
        }
        if ($("#COPYRIGHT").val() == "") {
            $("#COPYRIGHT").tips({
                side: 3,
                msg: '请输入版权',
                bg: '#AE81FF',
                time: 2
            });
            $("#COPYRIGHT").focus();
            return false;
        }
        if ($("#TECHNOLOGY").val() == "") {
            $("#TECHNOLOGY").tips({
                side: 3,
                msg: '请输入技术支持',
                bg: '#AE81FF',
                time: 2
            });
            $("#TECHNOLOGY").focus();
            return false;
        }
        if ($("#BEIAN").val() == "") {
            $("#BEIAN").tips({
                side: 3,
                msg: '请输入备案',
                bg: '#AE81FF',
                time: 2
            });
            $("#BEIAN").focus();
            return false;
        }
        if ($("#ABOUTUS1").val() == "") {
            $("#ABOUTUS1").tips({
                side: 3,
                msg: '请输入关于我们',
                bg: '#AE81FF',
                time: 2
            });
            $("#ABOUTUS1").focus();
            return false;
        }
        $("#Form").submit();
    }

    //上传图片
    layui.use('upload', function () {
        var upload = layui.upload;
        upload.render({
            elem: '#LOGO' //绑定元素
            , accept: 'images' //允许上传的文件类型
            , url: '/pictures/save' //上传接口
            , done: function (res, index, upload) {
                layer.msg("上传成功");
                var pro = window.location.protocol;
                var host = window.location.host;
                var domain = pro + "//" + host;
                var url = res.PATH;
                url = domain + url;
                $("#logopath").attr("src", url);
                $("#LOGO").val(res.PATH);
            }
        });
    });


</script>
</body>
</html>