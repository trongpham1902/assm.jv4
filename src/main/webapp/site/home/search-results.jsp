<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Bootstrap 5 CSS -->
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
    rel="stylesheet">

<h2 class="mb-4 text-center">Search Results for: "${keyword}"</h2>

<c:choose>
    <c:when test="${empty videos}">
        <p class="text-center">No videos found.</p>
    </c:when>
    <c:otherwise>
        <div class="container mt-4">

            <!-- ================= Video grid ================= -->
            <div class="row g-4 justify-content-center">
                <c:forEach var="v" items="${videos}">
                    <c:set var="liked"
                        value="${favoriteMap[v.id] != null && favoriteMap[v.id]}" />

                    <div class="col-md-4 d-flex">
                        <div class="video-card border p-2 h-100 w-100 d-flex flex-column align-items-center text-center">
                            <a href="<c:url value='/detail?id=${v.id}'/>" class="w-100">
                                <img class="card-img-top"
                                     src="<c:url value='${v.poster}'/>"
                                     alt="${v.title}"
                                     onerror="this.onerror=null;this.src='<c:url value="/images/poly.jpg"/>';"
                                     style="width: 100%; height: 200px; object-fit: cover;">
                            </a>

                            <div class="video-title bg-light p-1 fw-bold mt-2 w-100 text-truncate"
                                 title="${v.title}">
                                ${v.title}
                            </div>

                            <!-- Video actions -->
                            <div class="video-actions mt-2 d-flex justify-content-center flex-wrap gap-1 w-100">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.user}">
                                        <c:choose>
                                            <c:when test="${liked}">
                                                <a href="unlike?id=${v.id}"
                                                   class="btn btn-danger btn-sm">Unlike</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="like?id=${v.id}"
                                                   class="btn btn-success btn-sm">Like</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<c:url value='/Login'/>"
                                           class="btn btn-success btn-sm">Like</a>
                                    </c:otherwise>
                                </c:choose>
                                <a href="share?id=${v.id}" class="btn btn-warning btn-sm">Share</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

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
                    <li class="page-item disabled">
                        <span class="page-link">${currentPageInt} / ${totalPages}</span>
                    </li>
                    <li class="page-item ${currentPageInt == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="?page=${nextPage}">&gt;&gt;</a>
                    </li>
                    <li class="page-item ${currentPageInt == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="?page=${totalPages}">&gt;|</a>
                    </li>
                </ul>
            </nav>

        </div>

        <style>
            .video-card {
                transition: transform 0.2s;
            }
            .video-card:hover {
                transform: translateY(-5px);
            }
            .video-title {
                font-size: 1rem;
                text-align: center;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .video-actions a {
                margin: 2px;
            }
        </style>
    </c:otherwise>
</c:choose>

