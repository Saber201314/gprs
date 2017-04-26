<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<title>Insert title here</title>
</head>
<style>
form {
	margin-top: 15px
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
	background-color: #c2c2c2;
}
.lable-wait{
	background-color: #01AAED;
}
.lable-success{
	background-color: #5FB878;
}
.lable-fail{
	background-color: #FF5722;
}

</style>

<body>
	<fieldset class="layui-elem-field site-demo-button" style="margin-top: 5px;">
		<legend></legend>
		<form class="layui-form" action="">
			<div class="layui-form-item">
				<input type="hidden" name="cacheFlag" value="1" class="layui-input">
				<div class="layui-inline">
					<label class="layui-form-label">代理商</label>
					<div class="layui-input-inline">
						<select id="agent" name="agent" lay-filter="agent" lay-search>
							<option value="000">请选择</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">手机号码</label>
					<div class="layui-input-inline">
						<input type="tel" name="phone" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">归属地</label>
					<div class="layui-input-inline">
						<select id="location" lay-search>
							<option value="000">请选择</option>
							<option>北京</option>
							<option>天津</option>
							<option>河北</option>
							<option>山西</option>
							<option>内蒙古</option>
							<option>辽宁</option>
							<option>吉林</option>
							<option>黑龙江</option>
							<option>上海</option>
							<option>江苏</option>
							<option>浙江</option>
							<option>安徽</option>
							<option>福建</option>
							<option>江西</option>
							<option>山东</option>
							<option>河南</option>
							<option>湖北</option>
							<option>湖南</option>
							<option>广东</option>
							<option>广西</option>
							<option>海南</option>
							<option>重庆</option>
							<option>四川</option>
							<option>贵州</option>
							<option>云南</option>
							<option>西藏</option>
							<option>陕西</option>
							<option>甘肃</option>
							<option>宁夏</option>
							<option>青海</option>
							<option>新疆</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">开始时间</label>
					<div class="layui-input-inline">
						<input id="start" name="start" class="layui-input" onclick="">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">开始时间</label>
					<div class="layui-input-inline">
						<input id="end" name="end" class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">号码类型</label>
					<div class="layui-input-inline">
						<select name="type" lay-verify="" lay-search>
							<option value="000">请选择</option>
							<option value="">移动</option>
							<option value="">联通</option>
							<option value="">电信</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">流量值</label>
					<div class="layui-input-inline">
						<select name="amount">
							<option value="000">请选择</option>
							<option value="5">5M</option>
							<option value="10">10M</option>
							<option value="20">20M</option>
							<option value="30">30M</option>
							<option value="50">50M</option>
							<option value="70">70M</option>
							<option value="100">100M</option>
							<option value="150">150M</option>
							<option value="200">200M</option>
							<option value="300">300M</option>
							<option value="500">500M</option>
							<option value="700">700M</option>
							<option value="1024">1G</option>
							<option value="2048">2G</option>
							<option value="3072">3G</option>
							<option value="4096">4G</option>
							<option value="6144">6G</option>
							<option value="11264">11G</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">流量类型</label>
					<div class="layui-input-inline">
						<select name="locationType" lay-verify="" lay-search>
							<option value="000">请选择</option>
							<option value="001">全国流量</option>
							<option value="002">省内流量</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">充值状态</label>
					<div class="layui-input-inline">
						<select name="submitStatus" lay-verify="" lay-search>
							<option value="000">请选择</option>
							<option value="">未提交</option>
							<option value="">充值中</option>
							<option value="">充值失败</option>
							<option value="">充值成功</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">充值通道</label>
					<div class="layui-input-inline">
						<select id="submitChannel" name="submitChannel" lay-filter="submitChannel" lay-search>
							<option value="000">请选择</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" lay-filter="btn-submit">查询</button>
					</div>
				</div>


			</div>
		</form>
	</fieldset>
	<button class="layui-btn layui-btn-mini " >缓存管理</button>
	<button class="layui-btn layui-btn-mini getallcheck" >批量提交</button>
	<button class="layui-btn layui-btn-mini getallcheck" >停止提交</button>
	<button class="layui-btn layui-btn-mini getallcheck" >批量失败</button>
	<button class="layui-btn layui-btn-mini" >导出Excel</button>
	
	<div class="layui-form">
		<table class="layui-table">
			<thead>
				<tr>
					<th><input type="checkbox" name="" lay-skin="primary"
						lay-filter="allChoose"></th>
					<th>代理商</th>
					<th>手机号码</th>
					<th>号码类型</th>
					<th>流量类型</th>
					<th>流量值</th>
					<th>基础价格</th>
					<th>扣费金额</th>
					<th>充值时间</th>
					<th>回调时间</th>
					<th>充值方式</th>
					<th>充值结果</th>
					<th>充值通道</th>
					<th>接入</th>
					<th>外放</th>
					<th>带票</th>
					<th>盈利</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="checkbox" name="" data-id="1" lay-skin="primary"></td>
					<td>adminRuiCheng</td>
					<td>15858509302</td>
					<td>浙江&nbsp;移动</td>
					<td>全国流量</td>
					<td>500M</td>
					<td>30元</td>
					<td>14.4元</td>
					<td>2017-04-24 13:39:19</td>
					<td></td>
					<td>接口充值</td>
					<td>
						<span class="status lable-nosubmit">未提交</span>
					</td>
					<td>乐尧通道</td>
					<td>4.90</td>
					<td>4.80</td>
					<td><i class="layui-icon" style="font-size: 25px; color:#FF5722;">&#x1007;</i></th>
					<td><span class="profit">￥-0.3</span> </td>
					<td>
						<button class="layui-btn layui-btn-mini">详情</button>
						<button class="layui-btn layui-btn-mini">推送回调</button>
					</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="" data-id="2" lay-skin="primary"></td>
					<td>adminShuoRuan2</td>
					<td>15316092513</td>
					<td>上海&nbsp;电信</td>
					<td>省内流量</td>
					<td>200M</td>
					<td>30元</td>
					<td>14.4元</td>
					<td>2017-04-24 14:38:45</td>
					<td></td>
					<td>接口充值</td>
					<td>
						<span class="status lable-wait">充值中</span>
					</td>
					<td>乐尧通道</td>
					<td>4.90</td>
					<td>4.80</td>
					<td><i class="layui-icon" style="font-size: 25px; color:#FF5722;">&#x1007;</i></th>
					<td>￥-0.3</td>
					<td>
						<button class="layui-btn layui-btn-mini">详情</button>
						<button class="layui-btn layui-btn-mini">推送回调</button>
					</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="" data-id="3" lay-skin="primary"></td>
					<td>adminShuoRuan2</td>
					<td>15316092513</td>
					<td>上海&nbsp;电信</td>
					<td>省内流量</td>
					<td>200M</td>
					<td>30元</td>
					<td>14.4元</td>
					<td>2017-04-24 14:38:45</td>
					<td></td>
					<td>接口充值</td>
					<td>
						<span class="lable-success">充值成功</span>
					</td>
					<td>乐尧通道</td>
					<td>4.90</td>
					<td>4.80</td>
					<td><i class="layui-icon" style="font-size: 25px; color:#FF5722;">&#x1007;</i></th>
					<td><span style="color:#5FB878; ">￥0.3</span>   </td>
					<td>
						<button class="layui-btn layui-btn-mini">详情</button>
						<button class="layui-btn layui-btn-mini">推送回调</button>
					</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="" data-id="4" lay-skin="primary"></td>
					<td>adminShuoRuan2</td>
					<td>15316092513</td>
					<td>上海&nbsp;电信</td>
					<td>省内流量</td>
					<td>200M</td>
					<td>30元</td>
					<td>14.4元</td>
					<td>2017-04-24 14:38:45</td>
					<td></td>
					<td>接口充值</td>
					<td>
						<span class="lable-fail" >充值失败</span>
					</td>
					<td>乐尧通道</td>
					<td>4.90</td>
					<td>4.80</td>
					<td><i class="layui-icon" style="font-size: 25px; color:#FF5722;">&#x1007;</i></th>
					<td>￥-0.3</td>
					<td>
						<button class="layui-btn layui-btn-mini">详情</button>
						<button class="layui-btn layui-btn-mini">推送回调</button>
					</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="" data-id="5" lay-skin="primary"></td>
					<td>adminShuoRuan2</td>
					<td>15316092513</td>
					<td>上海&nbsp;电信</td>
					<td>省内流量</td>
					<td>200M</td>
					<td>30元</td>
					<td>14.4元</td>
					<td>2017-04-24 14:38:45</td>
					<td></td>
					<td>接口充值</td>
					<td>
						<span class="lable-success">充值成功</span>
					</td>
					<td>乐尧通道</td>
					<td>4.90</td>
					<td>4.80</td>
					<td><i class="layui-icon" style="font-size: 25px; color:#FF5722;">&#x1007;</i></th>
					<td><span style=" ">￥0.5</span></td>
					<td>
						<button class="layui-btn layui-btn-mini">详情</button>
						<button class="layui-btn layui-btn-mini">推送回调</button>
					</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="" data-id="6" lay-skin="primary"></td>
					<td>adminShuoRuan2</td>
					<td>15316092513</td>
					<td>上海&nbsp;电信</td>
					<td>省内流量</td>
					<td>200M</td>
					<td>30元</td>
					<td>14.4元</td>
					<td>2017-04-24 14:38:45</td>
					<td></td>
					<td>接口充值</td>
					<td>
						<span class="lable-success">充值成功</span>
					</td>
					<td>乐尧通道</td>
					<td>4.90</td>
					<td>4.80</td>
					<td><i class="layui-icon" style="font-size: 25px; color:#FF5722;">&#x1007;</i></th>
					<td>￥-0.3</td>
					<td>
						<button class="layui-btn layui-btn-mini">详情</button>
						<button class="layui-btn layui-btn-mini">推送回调</button>
					</td>
				</tr>
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
	}).use('charge-order-admin'); //加载入口
</script>
</html>