<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- (style của bạn giữ nguyên) -->

<div class="container py-4">
    <h3 class="text-center fw-bold mb-4">QUẢN LÝ NGƯỜI DÙNG</h3>

    <c:if test="${not empty message}">
        <div class="alert alert-info">${message}</div>
    </c:if>

    <!-- USER EDITION FORM -->
    <div class="card shadow-sm mb-4">
        <div class="card-header">User Edition</div>
        <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/admin/user-management">
                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">Username</label>
                        <input type="text" name="id" class="form-control" readonly
                               value="${formUser != null ? formUser.id : ''}"
                               ${formState == 'create' ? 'placeholder="Click Edit từ bảng..."' : ''}>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Password</label>
                        <!-- Hiển thị dấu cố định, disabled (KHÔNG chứa mật khẩu thật) -->
                        <input type="password" class="form-control" value="******" disabled>
                        <!-- KHÔNG đặt hidden chứa mật khẩu thật. Nếu admin không nhập password khi update, servlet sẽ giữ mật khẩu cũ -->
                        <input type="password" name="password" class="form-control d-none" value="">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Fullname</label>
                        <input type="text" name="fullname" class="form-control"
                               value="${formUser != null ? formUser.fullname : ''}">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Email</label>
                        <input type="email" name="email" class="form-control"
                               value="${formUser != null ? formUser.email : ''}">
                    </div>

                    <div class="col-12">
                        <label class="form-label me-3">Role</label>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="admin"
                                   id="roleAdmin" value="true"
                                   ${formUser != null and formUser.admin ? 'checked' : ''}>
                            <label class="form-check-label" for="roleAdmin">Admin</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="admin"
                                   id="roleUser" value="false"
                                   ${formUser == null or not formUser.admin ? 'checked' : ''}>
                            <label class="form-check-label" for="roleUser">User</label>
                        </div>
                    </div>
                </div>

                <div class="mt-3 d-flex gap-2">
                    <button type="submit" name="action" value="update"
                            class="btn btn-secondary"
                            ${formState == 'create' ? 'disabled' : ''}>Update</button>

                    <button type="submit" name="action" value="delete"
                            class="btn btn-outline-danger"
                            ${formState == 'create' ? 'disabled' : ''}
                            onclick="return confirm('Bạn có chắc muốn xóa người dùng này?');">Delete</button>

                    <a href="<c:url value='/admin/user-management'/>" class="btn btn-dark">Reset</a>
                </div>
            </form>
        </div>
    </div>

    <!-- USER LIST -->
    <div class="card shadow-sm">
        <div class="card-header">User List (${totalUsers} users)</div>
        <div class="card-body table-responsive">
            <table class="table table-bordered table-hover align-middle">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Fullname</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr class="${formUser != null and user.id == formUser.id ? 'table-primary' : ''}">
                            <td>${user.id}</td>
                            <td>
                                <input type="password" value="******" disabled
                                       class="form-control form-control-sm" style="width: 100px;">
                            </td>
                            <td>${user.fullname}</td>
                            <td>${user.email}</td>
                            <td>${user.admin ? 'Admin' : 'User'}</td>
                            <td>
                                <a href="<c:url value='/admin/user-management?action=edit&amp;id=${user.id}'/>"
                                   class="btn btn-sm btn-primary">Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Pagination -->
            <nav>
                <ul class="pagination justify-content-center mt-3">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="<c:url value='/admin/user-management?page=1'/>">|&lt;</a>
                    </li>
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="<c:url value='/admin/user-management?page=${currentPage - 1}'/>">&lt;&lt;</a>
                    </li>

                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="<c:url value='/admin/user-management?page=${i}'/>">${i}</a>
                        </li>
                    </c:forEach>

                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="<c:url value='/admin/user-management?page=${currentPage + 1}'/>">&gt;&gt;</a>
                    </li>
                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="<c:url value='/admin/user-management?page=${totalPages}'/>">&gt;|</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>
