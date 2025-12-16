<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style> /* ===== VIDEO DETAIL ===== */
.video-detail-card {
	background: #ffffff;
	border-radius: 14px;
	box-shadow: 0 6px 16px rgba(0, 0, 0, 0.08);
	overflow: hidden;
} /* ===== PLAYER ===== */
.video-player-box iframe {
	border-radius: 14px 14px 0 0;
} /* ===== INFO ===== */
.video-info-box {
	padding: 20px;
}

.video-info-box h4 {
	font-weight: 600;
	margin-bottom: 12px;
}

.video-info-box p {
	color: #555;
	line-height: 1.6;
} /* ===== ACTION BUTTONS ===== */
.video-actions {
	display: flex;
	gap: 12px;
	margin-bottom: 12px;
}

.btn-like-detail, .btn-share-detail {
	padding: 8px 22px;
	border-radius: 30px;
	font-size: 14px;
	text-decoration: none;
	transition: all 0.2s ease;
	color: #fff;
} /* Like */
.btn-like-detail {
	background: #ff4757;
}

.btn-like-detail:hover {
	background: #e84118;
} /* Share */
.btn-share-detail {
	background: #1e90ff;
}

.btn-share-detail:hover {
	background: #0c7cd5;
} /* ===== RELATED VIDEOS ===== */
.sidebar-video-item {
	display: flex;
	align-items: center;
	gap: 12px;
	padding: 10px;
	border-radius: 12px;
	text-decoration: none;
	margin-bottom: 10px;
	transition: background 0.2s ease;
}

.sidebar-video-item:hover {
	background: #f2f4f8;
}

.sidebar-poster img {
	width: 95px;
	height: 60px;
	object-fit: cover;
	border-radius: 10px;
}

.sidebar-title {
	font-size: 14px;
	font-weight: 500;
	color: #333;
	line-height: 1.4;
} /* Responsive */
@media ( max-width : 768px) {
	.video-player-box iframe {
		height: 260px;
	}
}
</style>
<div class="row">
	<!-- ===== MAIN VIDEO ===== -->
	<div class="col-md-8 mb-4">
		<div class="video-detail-card">
			<div class="video-player-box">
				<iframe width="100%" height="450"
					src="https://www.youtube.com/embed/${video.id}"
					title="${video.title}" frameborder="0"
					allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
					allowfullscreen> </iframe>
			</div>
			<div class="video-info-box">
				<h4>${video.title}</h4>
				<div class="video-actions">
					<a href="<c:url value='/like?id=${video.id}'/>"
						class="btn-like-detail"> ❤️ Like </a> <a
						href="<c:url value='/share?id=${video.id}'/>"
						class="btn-share-detail"> 🔗 Share </a>
				</div>
				<hr>
				<h6 class="fw-bold">DESCRIPTION</h6>
				<p>${video.description}</p>
			</div>
		</div>
	</div>
	<!-- ===== RELATED VIDEOS ===== -->
	<div class="col-md-4">
		<h5 class="mb-3 fw-bold">Related Videos</h5>
		<c:forEach var="related" items="${relatedVideos}">
			<a href="<c:url value='/detail?id=${related.id}'/>"
				class="sidebar-video-item">
				<div class="sidebar-poster">
					<img src="<c:url value='/logos/${related.poster}'/>"
						alt="${related.title}"
						onerror='this.onerror=null;this.src="<c:url value='/images/poly.jpg'/>";'>
				</div>

				<div class="sidebar-title">${related.title}</div>
			</a>
		</c:forEach>
	</div>
</div>