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
		<legend>添加通道</legend>
		<div class="layui-form" action="">
			<input type="hidden" name="id" value="${channel.id }"/>
			<input type="hidden" name="userId" value="${channel.user_id }"/>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">名称</label>
					<div class="layui-input-block">
						<input type="text" name="name" value="${channel.name }" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
					</div>	
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">展示名称</label>
					<div class="layui-input-block">
						<input type="text" name="alias" value="${channel.alias}" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">通道模板</label>
					<div class="layui-input-block">
						<select id="template" name="template" >
							<option value="0">请选择</option>
							<c:forEach items="${templateList}" var="item">
								<option value="${item.identity}" <c:if test="${channel.template == item.identity  }">selected</c:if> >${item.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">月门限值</label>
					<div class="layui-input-block">
						<input type="text" name="" value="${channel.month_limit}" autocomplete="off" class="layui-input">
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
									<input type="hidden" id="packCode" name="packages" value="${channel.packages }"/>
									<input type="text" id="packageName" placeholder="请输入流量包名称" autocomplete="off" class="layui-input">
								</div>
								<button  id="searchPackage" class="layui-btn">查询</button>
								<button  class="layui-btn checkAll" style="background-color: #5FB878;" ><i class="layui-icon">&#xe616;</i>全选择</button>
								<button  class="layui-btn layui-btn-danger cancelAll" ><i class="layui-icon">&#x1007;</i>全取消</button>
								<button  class="layui-btn layui-btn-normal setAllDiscount" ><i class="layui-icon">&#xe614;</i>设置折扣</button>
								<button  class="layui-btn layui-btn-normal setAllLevel" ><i class="layui-icon">&#xe614;</i>设置优先级</button>
								<button  class="layui-btn showDetail" >详情</button>
								<table class="layui-table">
									<thead>
										<tr>
											<th>名称</th>
											<th width="100">是否支持</th>
											<th width="100">折扣</th>
											<th width="100">优先级</th>
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
	<div id="single-page-content" style="display: none;padding-left: 5px;padding-right: 5px;">
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
					<th>流量包名称</th>
           			<th>基础价格</th>
           			<th>折扣</th>
           			<th>我的价格</th>
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
		addChannel: 'admin/channel/addChannel' //设定别名
	}).use('addChannel'); //加载入口 
	
</script>
</html>