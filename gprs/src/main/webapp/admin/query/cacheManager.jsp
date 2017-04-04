<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style>
.ks-tabs-panel {
	margin-top: -20px;
}
</style>
<div id="province" style="display:none">
	<div id="package-tabs" class="ks-tabs ks-tabs-top">
		<div class="ks-tabs-bar">
			<div class="ks-tabs-tab ks-button ks-tabs-tab-selected" id="provice">省份</div>
			<div class="ks-tabs-tab ks-button " id="channel">上游通道</div>
		</div>
		<div class="ks-tabs-body">
			<div class="ks-tabs-panel ks-tabs-panel-selected" id="provice_panel">
				<div class="user_Manager_style ">
					<div class="add_user_style">
						<ul class="clearfix">

							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">北京</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">天津</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">河北</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">山西</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">内蒙古</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">辽宁</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">吉林</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">黑龙江</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">上海</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">江苏</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">浙江</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">安徽</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">福建</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">江西</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">山东</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">河南</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">湖北</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">湖南</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">广东</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">广西</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">海南</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">重庆</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">四川</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">贵州</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">云南</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">西藏</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">陕西</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">甘肃</span></label>
							</div>



							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">青海</span></label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" class="ace" /><span
									class="lbl">新疆</span></label>
							</div>


						</ul>
						<!-- <div class="center"> <button class="btn btn-primary" type="button" id="submit">确认修改</button> -->
					</div>
				</div>
			</div>
			<div class="ks-tabs-panel " id="channel_panel">
				<div class="user_Manager_style ">
					<div class="add_user_style">
						<ul class="clearfix" id="channel_content">
						</ul>
					</div>
				</div>
			</div>			
		</div>
	</div>
</div>

<script>

	getCacheManagerData();
	function getCacheManagerData() {
		$.ajax({
			url : "getCacheMangerData.action",
			type : "get",
			dataType : 'json',
			cache : false,
			success : function(data) {
				if (data && data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						var province = data[i].province;
						if (province) {
							$("#provice_panel input").each(function() {
								if ($(this).next().text() == province) {
									$(this).attr("checked", "checked");
								}
							});
						}
						var channel = data[i].channel;
						if (channel) {						
							$("#channel_panel input").each(function() {
								if ($(this).next().text() == channel) {
									$(this).attr("checked", "checked");
								}
							});
						}						
						
					}
				}
			}
		});
	};

	$(".ks-tabs-tab").click(function() {
		$(".ks-tabs-tab").removeClass("ks-tabs-tab-selected");
		$(this).addClass("ks-tabs-tab-selected");
		$(".ks-tabs-panel").removeClass("ks-tabs-panel-selected");
		var id = $(this).attr("id");
		$("#" + id + "_panel").addClass("ks-tabs-panel-selected");
	});


	$("#channel").click(function(){
		$.ajax({
				url : "getAllChannelList.action",
				type : "post",
				dataType : 'json',
				cache : false,
				success : function(data) {
					if (data && data.length > 0) {
						var html = [];
						for (var i = 0; i < data.length; i++) {
							html.push('<div class="checkbox"><label><input type="checkbox" class="ace" /><span class="lbl">'
											+ data[i].name
											+ '</span></label></div>');
						}
						$("#channel_content").append(html.join(""));
						getCacheManagerData();
					} else {
						layer.alert(data.error);
					}
				}
		});	
	});
</script>
