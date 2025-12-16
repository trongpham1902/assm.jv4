<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- FAVORITES ALL-IN-1 -->
<div class="container favorites-container">

	<!-- Scoped CSS chỉ áp dụng cho phần favorites -->
	<style>
.favorites-container {
	margin-top: 50px;
	margin-bottom: 50px;
	font-family: 'Roboto', Arial, sans-serif; /* Đảm bảo font chung */
}

.favorites-container h2 {
	text-align: center;
	margin-bottom: 30px;
	font-weight: 600;
	color: #343a40;
}

.favorites-container .video-card {
	border: 1px solid #dee2e6;
	border-radius: 8px;
	overflow: hidden;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
	transition: transform 0.2s;
	background-color: #fff;
}

.favorites-container .video-card:hover {
	transform: translateY(-3px);
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.favorites-container .video-card img {
	width: 100%;
	height: 180px;
	object-fit: cover;
}

.favorites-container .video-title {
	font-size: 1rem;
	padding: 5px;
	background-color: #f8f9fa;
	font-weight: 500;
}

.favorites-container .video-actions .btn {
	min-width: 60px;
	margin-top: 5px;
}

.favorites-container .like-date {
	font-size: 0.8rem;
	color: #6c757d;
	margin-top: 3px;
}

@media ( max-width : 576px) {
	.favorites-container h2 {
		font-size: 1.5rem;
	}
	.favorites-container .video-title {
		font-size: 0.95rem;
	}
}
</style>

	<h2>Danh sách Video yêu thích</h2>

	<div class="row g-3">
		<c:forEach var="fav" items="${favorites}">
			<div class="col-md-4 col-sm-6">
				<div class="video-card text-center p-2 h-100">
					<a href="<c:url value='/detail?id=${fav.video.id}'/>"> <img
						src="<c:url value='/logos/${fav.video.poster}'/>"
						alt="${fav.video.title}"
						onerror='this.onerror=null;this.src="<c:url value='/images/poly.jpg'/>";'>
					</a>

					<div class="video-title mt-2">${fav.video.title}</div>
					<div class="video-actions">
						<a href="like?id=${fav.video.id}" class="btn btn-danger btn-sm">Unlike</a>
						<a href="share?id=${fav.video.id}" class="btn btn-warning btn-sm">Share</a>
					</div>
					<div class="like-date">
						<fmt:formatDate value="${fav.likeDate}" pattern="dd/MM/yyyy" />
					</div>
				</div>
			</div>
		</c:forEach>

		<c:if test="${empty favorites}">
			<div class="col-12 text-center">
				<p>Chưa có video nào được thích.</p>
			</div>
		</c:if>
	</div>

	<!-- Pagination nếu cần -->
	<c:if test="${not empty totalPages}">
		<c:set var="currentPageInt" value="${currentPage}" />
		<c:set var="prevPage"
			value="${currentPageInt > 1 ? currentPageInt - 1 : 1}" />
		<c:set var="nextPage"
			value="${currentPageInt < totalPages ? currentPageInt + 1 : totalPages}" />

		<nav aria-label="Page navigation" class="mt-4">
			<ul class="pagination justify-content-center flex-wrap">
				<li class="page-item ${currentPageInt == 1 ? 'disabled' : ''}">
					<a class="page-link" href="?page=1">|&lt;</a>
				</li>
				<li class="page-item ${currentPageInt == 1 ? 'disabled' : ''}">
					<a class="page-link" href="?page=${prevPage}">&lt;&lt;</a>
				</li>
				<li class="page-item disabled"><span class="page-link">${currentPageInt}
						/ ${totalPages}</span></li>
				<li
					class="page-item ${currentPageInt == totalPages ? 'disabled' : ''}">
					<a class="page-link" href="?page=${nextPage}">&gt;&gt;</a>
				</li>
				<li
					class="page-item ${currentPageInt == totalPages ? 'disabled' : ''}">
					<a class="page-link" href="?page=${totalPages}">&gt;|</a>
				</li>
			</ul>
		</nav>
	</c:if>
</div>