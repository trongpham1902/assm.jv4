<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="detail-container container mt-4">

    <div class="row">
        <!-- Video Detail -->
        <div class="col-lg-8">
            <div class="video-detail-card">
                <div class="video-player-box">
                    <c:choose>
                        <c:when test="${videoValid}">
                            <iframe 
                                width="100%" 
                                height="500" 
                                src="https://www.youtube.com/embed/${video.id}" 
                                title="${video.title}" 
                                frameborder="0" 
                                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                                allowfullscreen>
                            </iframe>
                        </c:when>
                        <c:otherwise>
                            <div class="no-video-box text-center p-5 bg-dark text-light">
                                <p>Video này hiện không khả dụng.</p>
                                <img src="<c:url value='/images/default-video.jpg'/>" alt="No video available" style="max-width:300px;">
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="video-info-box mt-3">
                    <h3 class="video-title">${video.title}</h3>
                    <div class="video-actions mt-2">
                        <c:if test="${videoValid}">
                            <a href="<c:url value='/like?id=${video.id}'/>" class="btn btn-primary btn-sm me-2">Like</a>
                            <a href="<c:url value='/share?id=${video.id}'/>" class="btn btn-warning btn-sm">Share</a>
                        </c:if>
                    </div>
                    <hr>
                    <h5>Description</h5>
                    <c:choose>
                        <c:when test="${not empty video.description}">
                            <p>${video.description}</p>
                        </c:when>
                        <c:otherwise>
                            <p><em>Không có mô tả cho video này.</em></p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Related Videos -->
        <div class="col-lg-4">
            <h5 class="mb-3">Related Videos</h5>
            <c:forEach var="related" items="${relatedVideos}">
                <a href="<c:url value='/detail?id=${related.id}'/>" class="related-video-item d-flex mb-3 p-2 rounded">
                    <div class="related-thumb me-2">
                        <img src="${related.poster}" alt="${related.title}" class="img-fluid rounded"
                             style="width: 100px; height: 60px; object-fit: cover;"
                             onerror="this.onerror=null;this.src='<c:url value='/images/default-video.jpg'/>';"/>
                    </div>
                    <div class="related-title align-self-center">${related.title}</div>
                </a>
            </c:forEach>
        </div>
    </div>
</div>

<style>
/* CSS chỉ áp dụng cho detail-container */
.detail-container .video-detail-card {
    background-color: #1e1e1e;
    color: #f0f0f0;
    padding: 15px;
    border-radius: 10px;
    box-shadow: 0 0 15px rgba(0,0,0,0.4);
}

.detail-container .video-title {
    font-weight: bold;
    font-size: 1.5rem;
}

.detail-container .video-info-box h5 {
    color: #ccc;
}

.detail-container .video-info-box p {
    color: #ddd;
    line-height: 1.5;
}

.detail-container .video-actions .btn {
    padding: 6px 12px;
    border-radius: 5px;
    text-decoration: none;
}

.detail-container .related-video-item {
    background-color: #2
