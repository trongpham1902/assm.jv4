<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- ===================== HEADER ===================== -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-4 shadow w-100">
    <div class="container-fluid">

        <!-- Brand / Logo -->
        <a class="navbar-brand fw-bold text-uppercase" href="<c:url value='/index'/>">
            Online Entertainment
        </a>

        <!-- Toggler -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#mainNavbar" aria-controls="mainNavbar"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navbar content -->
        <div class="collapse navbar-collapse" id="mainNavbar">

            <!-- Menu items -->
            <ul class="navbar-nav ms-auto">
                <!-- My Favorites -->
                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/favorites'/>">My Favorites</a></li>
                </c:if>

                <!-- Admin -->
                <c:if test="${not empty sessionScope.user && sessionScope.user.admin}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-danger fw-bold" href="#"
                           id="adminDropdown" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">ADMINISTRATION</a>
                        <ul class="dropdown-menu" aria-labelledby="adminDropdown">
                            <li><a class="dropdown-item" href="<c:url value='/admin/video-management'/>">Video Management</a></li>
                            <li><a class="dropdown-item" href="<c:url value='/admin/user-management'/>">User Management</a></li>
                            <li><a class="dropdown-item" href="<c:url value='/report'/>">Reports & Stats</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>

            <!-- Search form -->
            <form class="d-flex ms-3" action="<c:url value='/search'/>" method="get">
                <input class="form-control me-2" type="search" name="keyword"
                       placeholder="Search videos..." aria-label="Search" required>
                <button class="btn btn-outline-light" type="submit">Search</button>
            </form>

            <!-- My Account dropdown -->
            <ul class="navbar-nav ms-3">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="accountDropdown"
                       role="button" data-bs-toggle="dropdown" aria-expanded="false">My Account</a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="accountDropdown">
                        <c:if test="${empty sessionScope.user}">
                            <li><a class="dropdown-item" href="<c:url value='/Login'/>">Login</a></li>
                            <li><a class="dropdown-item" href="<c:url value='/RegistrationServlet'/>">Register</a></li>
                            <li><a class="dropdown-item" href="<c:url value='/ForgotPassword'/>">Forgot Password</a></li>
                        </c:if>
                        <c:if test="${not empty sessionScope.user}">
                            <li>
    <a class="dropdown-item" href="<c:url value='/edit-profile?id=${user.id}'/>">
        Edit Profile
    </a>
</li>
                            <li><a class="dropdown-item" href="<c:url value='/ChangePassword'/>">Change Password</a></li>
                            <li><a class="dropdown-item text-danger" href="<c:url value='/logout'/>">Logoff</a></li>
                        </c:if>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
