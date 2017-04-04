<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<style>
	.msg-wrapper{width:100%;}
	.pop_win input,.pop_win select{width:300px;}
	select{margin-left:10px;}
	li{padding-top:10px;}
	label{width:90px;}
	textarea{width:300px !important;}
	.sendMsg{margin-left:10px !important;margin-top:20px;}
	.submit-btn{margin-left:130px;}
</style>

	<div class="agent_charge" style="display:none;">
		<input type="hidden" name="id" id="chargeId"/>
		<div class="pop_win">
			<ul class="sendMsg">
				<li><label class="label-name" >帐号：</label>
					<input type="text" disabled="disabled" id="account"/>
				</li>
				<li>
					<label class="label-name" >充值金额：</label>
					<input type="text" disabled="disabled" id="money"/>			
				</li>
				<li><label class="label-name">充值方式：</label>
				<select id="payType">
					<option value="1">对公账号收款</option>
					<option value="2">对公支付宝收款</option>
					<option value="3">对私账号收款</option>
					<option value="4">对私支付宝收款</option>
					<option value="5">账号金额转移</option>
				</select>
					
				</li>
				<li><label class="label-name" >有效期：</label>
					<input type="text"  id="validDate" disabled="disabled" class="inline laydate-icon"  />
				</li>
				<li><label class="label-name" >备注：</label>
					<textarea rows="4" cols="50" id="memo" style="width:500px;height:100px;"></textarea>
				</li>						
			</ul>
		</div>	
		
		 <div class="sub_btn btn_operating">
			   <input class="submit-btn btn btn-info" type="button" value="提交">
			   <input type="button" class="btn cancel-btn" value="取消">
	     </div>				
	<div style="display:none;" id="balance_tip"></div>
</div>
<script>

	$(".submit-btn").click(function(){
		var params = [];
		params.push("queryAgentChargeLogDO.id=");
		params.push($("#chargeId").val());
		params.push("&");		
		params.push("queryAgentChargeLogDO.account=");
		params.push($("#account").val());
		params.push("&");
		params.push("queryAgentChargeLogDO.money=");
		params.push($("#money").val());
		params.push("&");
		params.push("queryAgentChargeLogDO.payType=");
		params.push($("#payType").val());
		params.push("&");		
		params.push("queryAgentChargeLogDO.from=");
		params.push($("#validDate").val());
		params.push("&");				
		params.push("queryAgentChargeLogDO.memo=");
		params.push($("#memo").val());
				
 		$.ajax({
			url : "updateAgentChargeData.action",
			type : "post",
			data: params.join(""),
			dataType : 'json',
			cache : false,
			success : function(data) {
				if(data.success) {			
					window.location=window.location;
				}
			}
		});		
	});

	$(".cancel-btn").click(function(){
		layer.closeAll(); 
	});

	/* var validDate = {
	    elem: '#validDate',
	    format: 'YYYY-MM-DD',
	    max: '2099-06-16 23:59:59', //最大日期
	    istime: true,
	    istoday: false,
	    choose: function(datas){
	         end.min = datas; //开始日选好后，重置结束日的最小日期
	         end.start = datas //将结束日的初始值设定为开始日
	    }
	};
	laydate(validDate);	 */
</script>