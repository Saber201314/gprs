<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<style>
#paper_table th,#paper_table td{padding:3px !important;}
dt{font-size:14px;}
#single-page-content{display:none;}
</style>

<div id="single-page-content" class="page-content">
	<div class="bar">
	</div>
	<div class="upload">
		<dl>
			<dd>
				<table id="paper_table" class="table table-striped table-bordered table-hover">
               		<tr>
            			<th>名称</th>
            			<th>我的折扣</th>
            			<th>代理商折扣</th>
            			<th>是否带票</th>
            			<th>优先级</th>            			
            			<th>通道名称</th>
              		</tr>
               	</table>
			</dd>
		</dl>	  
	</div>	
</div>
