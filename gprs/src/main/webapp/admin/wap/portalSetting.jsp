<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<jsp:include page="../../cssAndJs.jsp"></jsp:include>
<style>
label{width:85px;}
li{padding-top:10px;}
#file{ position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;}
</style>
<div id="screen" class="page-content">
	<div class="bar">
	</div>
	<br/>
	<form action="doPortalSetting.action" method="post" id="submit-form" enctype="multipart/form-data">
	  <div class="form-elem">
	  	<div class="Manager_style">
	  		<span class="title_name">页面设置</span>
			  <ul class="sendMsg Content_list clearfix">
					<li>
						<label class="label_name" >站点名称：</label>
						<input type="text" name="user.siteName" value="${user.siteName}" style="width:300px;"/>
					</li>
					<li><label class="label_name" >Logo：</label>
						<span class="upload-btn" style="display:inline-block;margin-left:10px;">
							<input type="file" id="file"name="file" hidefocus="true" value="上传图片" />
							<button class="btn Upload_btn">上传图片</button>
						</span>
						<p style="clear:both;margin-left:95px;" >
							<img class="upload-pic" style="height:45px;"  src="${pageContext.request.contextPath}/uploads/${user.siteLogo}" />
						</p>
						<input class="upload-input-hidden" type="hidden" name="user.siteLogo" value="${user.siteLogo }"  />
					</li>
					<li>
						<label class="label_name" style="margin-right:7px;">站点链接：</label>${payUrl}
					</li>
					<li>
						<label class="label_name" >二维码（右击图片下载）：</label>
						<img style="height:220px;" src="${pageContext.request.contextPath}/agent/wap/portalQrcode.action" />
					</li>
			  </ul>
			  <ul id="J_UploaderQueue" class="hidden"></ul>			  			  
			</div> 
			 <div class="sub_btn btn_operating">
				   <input class="submit-btn btn btn-info" type="button" value="提交">
		     </div>				 
		</div>	
	</form>
</div>
 <script>
	 KISSY.use("gprs/gprs-post,gprs/gprs-upload",function(S){
		GPRS.Post.post();
		GPRS.Upload.init("${pageContext.request.contextPath}/uploads/");
	});
</script>
