<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3 class="fw-bold text-secondary">QUẢN LÝ VIDEO</h3>
<hr>

<!-- MESSAGE -->
<c:if test="${not empty message}">
    <div class="alert alert-info">${message}</div>
</c:if>

<!-- FORM -->
<div class="card shadow-sm border-0 mt-3">
    <div class="card-body">
        <form action="<c:url value='/admin/video-management'/>" method="post" enctype="multipart/form-data" class="row g-4">

            <!-- LEFT SIDE: IMAGE + DESCRIPTION -->
            <div class="col-md-4">
                <img id="poster-preview"
                     src="${empty formVideo.poster 
                            ? 'https://placehold.co/350x220?text=No+Image'
                            : pageContext.request.contextPath.concat('/logos/').concat(formVideo.poster)}"
                     class="img-fluid rounded border border-secondary"
                     style="object-fit: cover; width: 100%; height: 220px;">

                <input type="file" name="cover" accept="image/*" class="form-control mt-3" onchange="previewImage(this)">

                <label class="form-label fw-semibold mt-3">DESCRIPTION</label>
                <textarea name="description" rows="6" class="form-control">${formVideo.description}</textarea>
            </div>

            <!-- RIGHT SIDE: INPUT FIELDS -->
            <div class="col-md-8">
                <input type="hidden" name="action" value="${formState == 'edit' ? 'update' : 'create'}">

                <div class="mb-3">
                    <label class="form-label fw-semibold">YOUTUBE VIDEO ID</label>
                    <input type="text" name="id" class="form-control"
                           value="${formVideo.id}"
                           ${formState == 'edit' ? 'readonly style="background:#ececec;"' : ''}>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">TITLE</label>
                    <input type="text" name="title" class="form-control" value="${formVideo.title}">
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">VIEWS</label>
                    <input type="number" name="views" class="form-control" value="${formVideo.views}">
                </div>

                <div class="d-flex align-items-center gap-4 mb-3">
                    <div>
                        <input type="radio" id="active" name="active" value="true"
                               ${formVideo.active == true ? "checked" : ""}>
                        <label for="active" class="ms-1">Active</label>
                    </div>

                    <div>
                        <input type="radio" id="inactive" name="active" value="false"
                               ${formVideo.active == false ? "checked" : ""}>
                        <label for="inactive" class="ms-1">Inactive</label>
                    </div>
                </div>

                <!-- BUTTONS -->
                <div class="mt-4 d-flex gap-2">
                    <button type="submit" class="btn btn-secondary px-4"
                            ${formState == 'edit' ? 'disabled' : ''}>Create</button>

                    <button type="submit" class="btn btn-warning px-4"
                            ${formState == 'create' ? 'disabled' : ''}>Update</button>

                    <a href="<c:url value='/admin/video-management?action=delete&id=${formVideo.id}'/>"
                       class="btn btn-danger px-4 ${formState == 'create' ? 'disabled' : ''}"
                       onclick="return confirm('Xóa video này?');">Delete</a>

                    <a href="<c:url value='/admin/video-management?action=reset'/>"
                       class="btn btn-outline-secondary px-4">Reset</a>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- TABLE LIST VIDEO -->
<div class="card shadow-sm border-0 mt-4">
    <div class="card-body">
        <table class="table table-hover align-middle">
            <thead class="table-secondary">
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Views</th>
                    <th>Status</th>
                    <th width="90">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${videoList}">
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.title}</td>
                        <td>${item.views}</td>
                        <td>
                            <span class="badge ${item.active ? 'bg-success' : 'bg-secondary'}">
                                ${item.active ? 'Active' : 'Inactive'}
                            </span>
                        </td>
                        <td>
                            <a href="<c:url value='/admin/video-management?action=edit&id=${item.id}'/>"
                               class="btn btn-sm btn-outline-primary fw-bold">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- PHÂN TRANG -->
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center mt-3">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="<c:url value='/admin/video-management?page=${i}'/>">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>

<script>
    function previewImage(input) {
        if (input.files && input.files[0]) {
            let reader = new FileReader();
            reader.onload = e => document.getElementById('poster-preview').src = e.target.result;
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>
