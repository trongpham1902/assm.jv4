<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="login-container">
    <div class="login-card">
        <h3>Login</h3>

        <c:if test="${not empty message}">
            <div class="alert alert-danger">${message}</div>
        </c:if>

        <form action="<c:url value='/Login'/>" method="post">
            <div class="mb-3">
                <label class="form-label">Username</label>
                <input type="text" class="form-control" name="username" value="${userId}">
            </div>
            <div class="mb-3">
                <label class="form-label">Password</label>
                <input type="password" class="form-control" name="password">
            </div>
            <div class="form-check mb-3">
                <input type="checkbox" class="form-check-input" name="remember" id="rememberCheck">
                <label class="form-check-label" for="rememberCheck">Remember me</label>
            </div>
            <button type="submit" class="btn btn-primary w-100">Login</button>
        </form>
    </div>
</div>

<style>
/* Căn giữa container toàn trang */
body, html {
    margin: 0;
    padding: 0;
    height: 100%;
    background-color: #1a1a1a; /* nền tối */
}

.login-container {
    display: flex;
    justify-content: center;
    align-items: center;     
    min-height: 80vh;  /* vừa đủ, không quá to */
}


/* Login card */
.login-card {
    background-color: #2c2c2c;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 0 15px rgba(0,0,0,0.5);
    width: 100%;
    max-width: 400px;
    color: #f0f0f0;
}

.login-card h3 {
    text-align: center;
    margin-bottom: 25px;
}

.form-control {
    background-color: #3a3a3a;
    border: 1px solid #555;
    color: #fff;
}

.form-control:focus {
    background-color: #3a3a3a;
    border-color: #007bff;
    box-shadow: none;
    color: #fff;
}

.form-check-label {
    color: #ccc;
}

.btn-primary {
    background-color: #007bff;
    border-color: #007bff;
    margin-top: 15px;
}

.btn-primary:hover {
    background-color: #0056b3;
    border-color: #0056b3;
}

.alert {
    background-color: #ff4d4d;
    color: #fff;
    border: none;
}
</style>
