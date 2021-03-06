<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<title></title>
</head>
<style>
body{
	padding: 0 15px;
}
.layui-form{
	padding-bottom: 5px;
}
.pack-input{
	width: 50px;
	margin: 0 auto;
	line-height: 40px;
}

</style>
<body>
	<fieldset class="layui-elem-field" style="margin-top: 5px;">
		<legend>添加报价单</legend>
		<div class="layui-form" action="">
			<input type="hidden" name="id" value="${pricepaper.id }"/>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">名称</label>
					<div class="layui-input-block">
						<input type="text" name="name" value="${pricepaper.name }" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
					</div>	
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">展示名称</label>
					<div class="layui-input-block">
						<input type="text" name="alias" value="${pricepaper.alias}" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">支持流量包</label>
					<div class="layui-input-block">
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">名称</label>
								<div class="layui-input-inline">
									<input type="hidden" id="pageNo"  value="1"/>
									<input type="hidden" id="packCode" name="items" value="${pricepaper.items }"/>
									<input type="text" id="packageName" placeholder="请输入流量包名称" autocomplete="off" class="layui-input">
								</div>
								<button  id="searchPackage" class="layui-btn">查询</button>
								<button  class="layui-btn checkAll" style="background-color: #5FB878;" ><i class="layui-icon">&#xe616;</i>全选择</button>
								<button  class="layui-btn layui-btn-danger cancelAll" ><i class="layui-icon">&#x1007;</i>全取消</button>
								<button  class="layui-btn layui-btn-normal setAllDiscount" ><i class="layui-icon">&#xe614;</i>设置折扣</button>
								<button  class="layui-btn showDetail" >详情</button>
								<table class="layui-table">
									<thead>
										<tr>
											<th>编号</th>
											<th>名称</th>
											<th width="100">是否支持</th>
											<th width="100">折扣</th>
											<th width="100"><input type="checkbox" name="" lay-skin="primary"
												lay-filter="allPaybill">带票</th>
											
										</tr>
									</thead>
									<tbody>
										
									</tbody>
								</table>
								<div id="pageinfo" style="text-align: center;">
									<span id="page-total"></span>
									<div id="pate"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">备注</label>
					<div class="layui-input-block">
						<input type="text" name="memo" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div>
				<div  class="layui-inline">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" lay-filter="channel-submit">提交</button>
					</div>
				</div>
			</div>
		</div>
	</fieldset>
	<div id="single-page-content" style="display: none; padding-left: 5px;padding-right: 5px;">
		<table class="layui-table">
			<colgroup>
				<col width="150">
				<col width="80">
				<col width="80">
				<col width="80">
				<col width="150">
			</colgroup>
			<thead>
				<tr>
					<th>名称</th>
           			<th>接入</th>
           			<th>放出</th>
           			<th>是否带票</th>
           			<th>优先级</th>
           			<th>通道名称</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
	layui.config({
		base : '/assts/js/gprs/' //你的模块目录
	}).extend({ //设定模块别名
		base: 'base', //如果test.js是在根目录，也可以不用设定别名
		addPricePaper: 'admin/costmanage/addPricePaper' //设定别名
	}).use('addPricePaper'); //加载入口 
	
</script>
</html>