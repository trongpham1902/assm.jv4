<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container mt-4">

	<!-- ================= Video grid ================= -->
	<div class="row g-3">
		<c:forEach var="v" items="${videos}">
			<!-- Set liked dựa vào favoriteMap -->
			<c:set var="liked"
				value="${favoriteMap[v.id] != null && favoriteMap[v.id]}" />

			<div class="col-md-4">
				<div class="video-card text-center border p-2 h-100">
					<a href="<c:url value='/detail?id=${v.id}'/>"> <img
						class="card-img-top"
						src="<c:url value='/logos/${v.poster}'/>?t=${v.id}"
						alt="${v.title}"
						onerror='this.onerror=null;this.src="<c:url value='/images/poly.jpg'/>";'>

					</a>
					<div class="video-title bg-light p-1 fw-bold mt-2">${v.title}</div>

					<!-- Video actions -->
					<div class="video-actions mt-2">
						<c:choose>
							<c:when test="${not empty sessionScope.user}">
								<c:choose>
									<c:when test="${liked}">
										<a href="unlike?id=${v.id}" class="btn btn-danger btn-sm me-1">Unlike</a>
									</c:when>
									<c:otherwise>
										<a href="like?id=${v.id}" class="btn btn-success btn-sm me-1">Like</a>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<a href="<c:url value='/Login'/>"
									class="btn btn-success btn-sm me-1">Like</a>
							</c:otherwise>
						</c:choose>
						<a href="share?id=${v.id}" class="btn btn-warning btn-sm">Share</a>
					</div>
				</div>
			</div>
		</c:forEach>

		<!-- ================= Pagination ================= -->
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

	</div>

	<style>
.video-card img {
	width: 100%;
	height: 200px;
	object-fit: cover;
}

.video-title {
	font-size: 1rem;
}

.pagination .page-item .page-link {
	min-width: 40px;
	text-align: center;
}
</style>