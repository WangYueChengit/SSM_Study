<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<link href="${ pageContext.request.contextPath }/css/addOrder.css" rel="stylesheet" type="text/css">
	<style type="text/css">
        
        #container{height:200px}
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=3.0&ak=CQwgOvwWlXnQDzGQdRHmy3zjKFNXeHgC">
        //v3.0版本的引用方式：src="http://api.map.baidu.com/api?v=3.0&ak=您的密钥"
    </script>
    
</head>
<body>
	
	<%@ include file = "_head.jsp" %>
	<div class="warp">
		<form action="${pageContext.request.contextPath }/order/addOrder" name="form1" method="post">
			<h3>增加订单</h3>
			<div id="forminfo">
				<span class="lf" style="vertical-align: middle;">收货地址：</span> 
				<label for="textarea"></label>
				<textarea name="receiverinfo" id="textarea" cols="35" rows="3">${add}</textarea>
				<input type="button" onclick="setCity()" value="查找"/>
				<div id="container"></div>
				<script type="text/javascript">
   				 var map = new BMap.Map("container");
    			// 创建地图实例
    			var point = new BMap.Point(116.404, 39.915);
    			// 创建点坐标
    			map.centerAndZoom(point, 15);
    			// 初始化地图，设置中心点坐标和地图级别
    			map.enableScrollWheelZoom();
    			map.addControl(new BMap.NavigationControl());    //添加控件：缩放地图的控件，默认在左上角；
    		    map.addControl(new BMap.MapTypeControl());        //添加控件：地图类型控件，默认在右上方；
    		    map.addControl(new BMap.ScaleControl());        //添加控件：地图显示比例的控件，默认在左下方；
    		    map.addControl(new BMap.OverviewMapControl());  //添加控件：地图的缩略图的控件，默认在右下方； TrafficControl
    		    
    		    map.addEventListener('click', function(e) {
    		        alert('click');
    		        
    		    });
    		    
    		    var search = new BMap.LocalSearch("中国", {
    		        onSearchComplete: function(result){
    		          if (search.getStatus() == BMAP_STATUS_SUCCESS){
    		            var res = result.getPoi(0);
    		            var point = res.point;
    		            map.centerAndZoom(point, 11);
    		          }
    		        },renderOptions: {  //结果呈现设置，
    		          map: map,  
    		          autoViewport: true,  
    		          selectFirstResult: true 
    		        } ,onInfoHtmlSet:function(poi,html){//标注气泡内容创建后的回调函数，有了这个，可以简单的改一下返回的html内容了。
    		          // alert(html.innerHTML)
    		        }
    		      });
    		    function setCity(){
    		        search.search(document.getElementById("textarea").value);
    		        
    		      }
    		   
    		      search.search(document.getElementById("textarea").value);
				</script>
				<br> 支付方式：<input name="" type="radio" value="1" checked>&nbsp;在线支付
				  	   <input type="hidden" name="cartIds" value="${cartIds }">
			</div>
			<table width="999" height="80" border="1" cellpadding="0" cellspacing="0" bordercolor="#d8d8d8">
				<tr>
					<th width="276">商品图片</th>
					<th width="247">商品名称</th>
					<th width="231">商品单价</th>
					<th width="214">购买数量</th>
					<th width="232">总价</th>
				</tr>
				<c:set var="sum" value="0"/>
				<c:forEach items="${carts}" var="cart">
				<tr>
					<td><img src="${pageContext.request.contextPath }${cart.imgurl}"
					width="90" height="90" class="prodimg" /></td>
					<td>${cart.name}</td>
					<td>${cart.price }元</td>
					<td>${cart.num }件</td>
					<td>${cart.price*cart.num }元</td>
				</tr>
				<c:set var="sum" value="${sum+cart.price*cart.num }"/>
				</c:forEach>
				
			</table>

			<div class="Order_price">总价：${sum }元</div>

			<div class="add_orderbox">
				<input name="" type="submit" value="增加订单" class="add_order_but">
			</div>
		</form>
	</div>
	<%@ include file = "_foot.jsp" %>
	
</body>
</html>
