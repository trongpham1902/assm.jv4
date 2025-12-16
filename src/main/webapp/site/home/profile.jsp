<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container py-4 d-flex justify-content-center">

	<div class="card shadow-lg border-0 rounded-4"
		style="width: 100%; max-width: 650px;">

		<div
			class="card-header bg-primary text-white text-center py-3 rounded-top-4">
			<h3 class="mb-0">Edit Profile</h3>
		</div>

		<div class="card-body p-4">

			<!-- Message -->
			<c:if test="${not empty error}">
				<div class="alert alert-danger">${error}</div>
			</c:if>

			<c:if test="${not empty message}">
				<div class="alert alert-success">${message}</div>
			</c:if>

			<form action="edit-profile" method="post" class="mt-3">

				<!-- Hidden ID -->
				<input type="hidden" name="id" value="${user.id}">

				<!-- User ID -->
				<div class="mb-3">
					<label class="form-label fw-semibold">User ID</label> <input
						type="text" class="form-control form-control-lg"
						value="${user.id}" disabled>
				</div>

				<!-- Fullname -->
				<div class="mb-3">
					<label class="form-label fw-semibold">Fullname</label> <input
						type="text" name="fullname" class="form-control form-control-lg"
						value="${user.fullname}" required>
				</div>

				<!-- Email -->
				<div class="mb-3">
					<label class="form-label fw-semibold">Email</label> <input
						type="email" name="email" class="form-control form-control-lg"
						value="${user.email}" required>
				</div>

				<!-- Role -->
				<div class="mb-3">
					<label class="form-label fw-semibold">Role</label> <input
						type="text" class="form-control form-control-lg"
						value="${user.admin ? 'Admin' : 'User'}" disabled>
				</div>

				<!-- Buttons -->
				<div class="d-flex justify-content-center gap-3 mt-4">
					<button type="submit" class="btn btn-primary px-4 py-2 btn-lg">
						Save Changes</button>
					<a href="<c:url value='/index'/>"
						class="btn btn-outline-secondary px-4 py-2 btn-lg"> Cancel </a>
				</div>

			</form>
		</div>
	</div>

</div>
