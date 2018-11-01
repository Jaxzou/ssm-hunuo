<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container" overflow:hidden>
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">

					<form action="aboutus/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ABOUTUS_ID" id="ABOUTUS_ID" value="${pd.ABOUTUS_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td>
									<textarea id="CONTENT" name="CONTENT" style="width:1200px;height:400px;visibility:hidden;">${pd.CONTENT}</textarea>
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
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 百度富文本编辑框-->
	<!-- 富文本编辑器 -->
	<link rel="stylesheet" href="static/kindeditor/themes/default/default.css"/>
	<script charset="utf-8" type="text/javascript" src="static/kindeditor/kindeditor-all.js"></script>
	<script charset="utf-8" type="text/javascript" src="static/kindeditor/lang/zh-CN.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
            // 将编辑器的HTML数据同步到textarea
            editor.sync();
			$("#CONTENT").val(getContent());
			if($("#CONTENT").val()==""){
				$("#CONTENT").tips({
					side:3,
		            msg:'请输入内容',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CONTENT").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

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
                    allowFileManager : true,
                    width:"100%",
					height:"690px"
                });
            });
        });
		</script>
</body>
</html>