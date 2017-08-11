<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<jsp:include page="/cssjs.jsp"></jsp:include>
<title></title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
 
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
 
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<style>
body{
	padding: 15px 0px;
	background-color: #ecf0f5;
}
.container{
	width: 1700px;
}
.panel-body .charts div{
	float: left;
}
.pay-info .col{
	width : 25%;
	float: left;
}
</style>
<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-lg-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe609;</i>
						今日代理商消费
					</h3>
				</div>
				<div class="panel-body">
					<div class="charts">
						<div id="agent1" style="width: 390px;height:380px;"></div>
						<div id="agent2" style="width: 390px;height:380px;"></div>
					</div>
					<div class="pay-info">
						<div class="col">
							<div>提交成功 ： 250</div>
							<div>结算金额 ： 6846484</div>
						</div>
						<div class="col">
							<div>提交成功 ： 250</div>
							<div>结算金额 ： 6846484</div>
						</div>
						<div class="col">
							<div>提交成功 ： 250</div>
							<div>结算金额 ： 6846484</div>
						</div>
						<div class="col">
							<div>提交成功 ： 250</div>
							<div>结算金额 ： 6846484</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe609;</i>
						今日通道消费
					</h3>
				</div>
				<div class="panel-body">
					<div class="charts">
						<div id="main1" style="width: 390px;height:380px;"></div>
						<div id="main2" style="width: 390px;height:380px;"></div>
					</div>
					<div class="pay-info">
						<div class="col">
							<div>提交成功 ： 250</div>
							<div>结算金额 ： 6846484</div>
						</div>
						<div class="col">
							<div>提交成功 ： 250</div>
							<div>结算金额 ： 6846484</div>
						</div>
						<div class="col">
							<div>提交成功 ： 250</div>
							<div>结算金额 ： 6846484</div>
						</div>
						<div class="col">
							<div>提交成功 ： 250</div>
							<div>结算金额 ： 6846484</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
<script type="text/javascript">
var agent1 = echarts.init(document.getElementById('agent1'));
var agent2 = echarts.init(document.getElementById('agent2'));

var myChart1 = echarts.init(document.getElementById('main1'));
var myChart2 = echarts.init(document.getElementById('main2'));


option1 = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        data:['提交成功','提交失败','充值成功','充值失败']
    },
    series: [
        {
            name:'通道统计',
            type:'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '30',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {value:335, name:'提交成功'},
                {value:310, name:'提交失败'},
                {value:234, name:'充值成功'},
                {value:135, name:'充值失败'}
            ]
        }
    ]
};
option2 = {
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        x: 'left',
	        data:['提交成功','提交失败','充值成功','充值失败']
	    },
	    series: [
	        {
	            name:'通道统计',
	            type:'pie',
	            radius: ['50%', '70%'],
	            avoidLabelOverlap: false,
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                },
	                emphasis: {
	                    show: true,
	                    textStyle: {
	                        fontSize: '30',
	                        fontWeight: 'bold'
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            data:[
	                {value:0, name:'提交成功'},
	                {value:0, name:'提交失败'},
	                {value:0, name:'充值成功'},
	                {value:0, name:'充值失败'}
	            ]
	        }
	    ]
	};
myChart1.setOption(option1);
myChart2.setOption(option2);

agent1.setOption(option1);
agent2.setOption(option2);
</script>
</html>