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

                        <form action="articlekind/${msg }.do" name="Form" id="Form" method="post">
                            <input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
                            <div id="zhongxin" style="padding-top: 13px;">
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">分类名称</td>
                                        <td><input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="255" placeholder="这里输入分类名称" title="分类名称" style="width:98%;"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">所属分类</td>
                                        <td>
                                            <select id="PARENT_ID" name="PARENT_ID" class="form-control input-sm"></select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">是否隐藏</td>
                                        <td>
                                            <input type="checkbox" id="STATUS" name="STATUS"> 是否隐藏&nbsp;&nbsp;<!--是否隐藏-->
                                            <label for="STATUS"></label>
                                        </td>
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
<script type="text/javascript">
    $(top.hangge());

    //加载事件
    initData();

    function initData() {
        //判断是否需要选中
        if('${pd.STATUS}' == 'on'){
            $('#STATUS').attr("checked","checked");
        }

        $.ajax({
            type: 'get',
            url: 'articlekind/getList.do',
            success: function (data) {
                var list = data.articleKindList;
                var select = $("#PARENT_ID");
                var c1 = null;
                var c2 = null;
                c2 = "<option value='0'>--不是顶级分类，请选择所属分类--</option>";
                var PARENT_ID ;
                var ID;
                if('${pd.PARENT_ID}'!=null && '${pd.PARENT_ID}'!= ''){
                    PARENT_ID = '${pd.PARENT_ID}';
                }
                if('${pd.ID}'!=null && '${pd.ID}'!= ''){
                    ID = '${pd.ID}';
                }
                for (var i = 0; i < list.length; i++) {
                    var d = list[i];
                    var id = d['ID'];
                    var name = d['NAME'];
                    if(ID == id){
                        break;
                    }
                    if( PARENT_ID == id){
                        c1 += "<option value='" + id + "'>" + name + "</option>";
                    }else{
                        c2 += "<option value='" + id + "'>" + name + "</option>";
                    }
                }
                select.append(c1 + c2);

            }
        });
    }

    function isStatus(status) {
        alert(status);
        if(status == 'on'){
            return 'checked';
        }else {
            return '';
        }
    }

    //保存
    function save() {
        if ($("#NAME").val() == "") {
            $("#NAME").tips({
                side: 3,
                msg: '请输入分类名称',
                bg: '#AE81FF',
                time: 2
            });
            $("#NAME").focus();
            return false;
        }
        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();
    }

    $(function () {
        //日期框
        $('.date-picker').datepicker({autoclose: true, todayHighlight: true});
    });
</script>
</body>
</html>