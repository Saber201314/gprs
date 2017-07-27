<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<jsp:include page="payBillInfo.jsp"></jsp:include>
<title>Insert title here</title>
</head>
<style>

body{
	padding: 0 15px;
}
.layui-table td, .layui-table th{
	padding: 9px 5px;

}
td .status{
	padding: 5px 10px;
	color: #fff;
}
td .profit{
	color:#5FB878;
	font-weight: bold;
}
td .unprofit{
	color:#FF5722;
	font-weight: bold;
}
.lable-nosubmit{
	padding: 4px 10px;
	color: #fff;
	background-color: #c2c2c2;
}
.lable-charging{
	padding: 4px 10px;
	color: #fff;
	background-color: #01AAED;
}
.lable-success{
	padding: 4px 10px;
	color: #fff;
	background-color: #5FB878;
}
.lable-fail{
	padding: 4px 10px;
	color: #fff;
	background-color: #FF5722;
}

body .ui-tooltip{
	border: 0px solid white;
}
.ui-widget-content{
	background : #2F4056;
	color: #fff;
}
.ui-tooltip, .arrow:after {
    background-color : #2F4056;
  }
.arrow {
    width: 70px;
    height: 16px;
    overflow: hidden;
    position: absolute;
    left: 50%;
    margin-left: -35px;
    bottom: -16px;
  }
  .arrow.top {
    top: -16px;
    bottom: auto;
  }
  .arrow.left {
    left: 20%;
  }
  .arrow:after {
    content: "";
    position: absolute;
    left: 20px;
    top: -20px;
    width: 25px;
    height: 25px;
    box-shadow: 6px 5px 9px -9px black;
    -webkit-transform: rotate(45deg);
    -moz-transform: rotate(45deg);
    -ms-transform: rotate(45deg);
    -o-transform: rotate(45deg);
    tranform: rotate(45deg);
  }
  .arrow.top:after {
    bottom: -20px;
    top: auto;
  }




</style>

<body>
	<fieldset class="layui-elem-field site-demo-button" style="margin-top: 5px;">
		<form class="layui-form" action="">
			<div class="layui-form-item">
				<input type="hidden" id="pageNo" name="pageNo" value="1" class="layui-input">
				<div class="layui-inline">
					<label class="layui-form-label">代理商</label>
					<div class="layui-input-inline">
						<select id="agent" name="account" lay-filter="agent" lay-search>
							<option value="-1">请选择</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">手机号码</label>
					<div class="layui-input-inline">
						<input type="tel" name="mobile" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">开始时间</label>
					<div class="layui-input-inline">
						<input id="start" name="from" class="layui-input" onclick="">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">结束时间</label>
					<div class="layui-input-inline">
						<input id="end" name="to" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">操作状态</label>
					<div class="layui-input-inline">
						<select name="status" lay-verify="" lay-search>
							<option value="-3">请选择</option>
							<option value="-2">充值失败</option>
							<option value="-1">已退款</option>
							<option value="0">充值中</option>
							<option value="1">充值成功</option>
						</select>
					</div>
				</div>
				<div  class="layui-inline">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" lay-filter="btn-submit">查询</button>
					</div>
				</div>


			</div>
		</form>
	</fieldset>
	<button class="layui-btn layui-btn-mini generate_bill" >生成账单</button>
	<button class="layui-btn layui-btn-mini" >导出Excel</button>
	
	<div class="layui-form">
		<table class="layui-table">
			<thead>
				<tr>
					<th>代理商</th>
					<th>类别</th>
					<th>流水号</th>
					<th>折扣</th>
					<th>扣费金额</th>
					<th>剩余金额</th>
					<th>盈利金额</th>
					<th>代理商订单号</th>
					<th>明细</th>
					<th>扣费时间</th>
					<th width="80">操作状态</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div id="pageinfo" style="text-align: center;">
		<span id="page-total"></span>
		<div id="pate"></div>
	</div>



</body>
<script type="text/javascript">
	layui.config({
		base : '/assts/js/gprs/' //你的模块目录
	}).use('paylog'); //加载入口
	
</script>
</html>