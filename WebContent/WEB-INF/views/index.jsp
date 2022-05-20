<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Shop Điện thoại</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script src="<c:url value="/resources/js/jquery-3.1.1.js"/>"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/bootstrap/css/bootstrap.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/style_site.css"/>">
</head>
<body>
	<div class="container">
		<div class="row" style="margin-top: 3px; height: 125px">
			<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4 clearpadding">
				<a href="${pageContext.servletContext.contextPath}/trangchu.htm"><img
					src="<c:url value="/resources/upload/logo.png"/>" alt=""
					class="img-responsive"></a>
			</div>
		</div>
		<div class="row">
			<nav class="navbar navbar-info re-navbar">
				<div class="container-fluid re-container-fluid">
					<!-- Brand and toggle get grouped for better mobile display -->
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse"
							data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
							<span class="sr-only">Toggle navigation</span> 
							<span class="icon-bar"></span> 
							<span class="icon-bar"></span> 
							<span class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="#">--- Menu ---</a>
					</div>
					<!-- Collect the nav links, forms, and other content for toggling -->
					<div class="collapse navbar-collapse re-navbar-collapse"
						id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav">
							<li class="active"><a href="${pageContext.servletContext.contextPath}/trangchu.htm"><span
									class="glyphicon glyphicon-home" aria-hidden="true"></span>
									HOME<span class="sr-only">(current)</span></a></li>
							<c:forEach var="category" items="${listCategorys }">
								<li><a href="${category.categoryNameToURL()}.htm">${category.categoryName}</a></li>
							</c:forEach>
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<li><a href="${pageContext.servletContext.contextPath}/user/mycart.htm"> <span
									class="glyphicon glyphicon-shopping-cart"></span> Giỏ Hàng
							</a></li>
							<c:choose>
								<c:when test="${role=='GUEST'||role==null}">
									<li><a href="${pageContext.servletContext.contextPath}/dangnhap.htm">Đăng nhập</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/dangki.htm">Đăng kí</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.servletContext.contextPath}/user/contact.htm">Thông tin cá nhân</a></li>
									<li><a href="${pageContext.servletContext.contextPath}/dangxuat.htm">Đăng xuất</a></li>
								</c:otherwise>
							</c:choose>
							
						</ul>
					</div>
					<!-- /.navbar-collapse -->

				</div>
				<!-- /.container-fluid -->
			</nav>
		</div>

		<div class="row">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title text-center">
						<img src="<c:url value = "/resources/upload/icon/hot.gif"/>"
							alt=""><a href="" class='product_title'>Các sản phẩm đang kinh doanh </a><img
							src="<c:url value = "/resources/upload/icon/hot.gif"/>" alt="">
					</h3>
				</div>
				<div class="panel-body">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 clearpadding">
						<c:forEach var="batch" items="${listBatchs }">
							<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
								<div class="product_item" style="height: 450px">
									<p>
										<a href="${pageContext.servletContext.contextPath}/product/${batch.batchNameToURL()}.htm" class="product_name">${batch.batchName}</a>
									</p>
									<div class="product-image">
										<a href="${pageContext.servletContext.contextPath}/product/${batch.batchNameToURL()}.htm"><img
											src="${pageContext.servletContext.contextPath}/resources/upload/product/${batch.photo}"
											alt="" class=""></a>
									</div>
									<p>
										
										<c:choose>
											<c:when test="${batch.discount!=0}">
												<span class='price text-right'>${batch.priceDiscountToString()} VNĐ</span>
        										<del class="product-discount">${batch.priceToString()} VNĐ</del>
    										</c:when>
											<c:otherwise>
												<span class='price text-right'>${batch.priceDiscountToString()} VNĐ</span>
    										</c:otherwise>
										</c:choose>
										
									</p>
									<c:choose>
								<c:when test="${batch.status==1}">
									<a href="${pageContext.servletContext.contextPath}/user/mycart/addCart/${batch.batchNameToURL()}.htm"><button class='btn btn-info'>
											<span class="glyphicon glyphicon-shopping-cart"
												aria-hidden="true"></span> Thêm giỏ hàng
										</button></a>
								</c:when>
								<c:when test="${batch.status==2}">
									<p><span style="font-weight: bold;color: green">HẾT HÀNG</span></p>
								</c:when>
								<c:otherwise>
									<p><span style="font-weight: bold;color: red">NGỪNG KINH DOANH</span></p>
								</c:otherwise>
							</c:choose>
									
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>

		<div class="row" id="footer">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">
					<address>
						<strong> Shop Điện thoại ALENE</strong><br> <span
							class="glyphicon glyphicon-home" aria-hidden="true"></span> Địa
						chỉ: 11 Đình Phong Phú, Tăng Nhơn Phú B, TP. Thủ Đức, TP. Hồ Chí Minh<br> <span
							class="glyphicon glyphicon-phone" aria-hidden="true"></span> Điện
						thoại: 0328512380<br> Copyright ©2017 - Design by GoS ---
					</address>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<script
		src="<c:url value="/reesources/bootstrap/js/bootstrap.min.js"/>"></script>
</body>
</html>