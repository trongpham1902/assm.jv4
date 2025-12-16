<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="login-container">
    <div class="login-card">
        <h3>Forgot Password</h3>

        <c:if test="${not empty message}">
            <div class="alert alert-info">${message}</div>
        </c:if>

        <form action="<c:url value='/ForgotPassword'/>" method="post">
            <div class="mb-3">
                <label>Email</label>
                <input type="email" class="form-control" name="email" placeholder="Enter your email" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Reset Password</button>
        </form>

        <div style="text-align:center; margin-top:15px;">
            <a href="<c:url value='/Login'/>" style="color:#7ab4ff;">Back to Login</a>
        </div>
    </div>
</div>

<style>
/* Body nền tối */
body, html {
    margin: 0;
    padding: 0;
    background-color: #1a1a1a;
}

/* Container căn giữa ngang, không quá cao */
.login-container {
    display: flex;
    justify-content: center; /* căn giữa ngang */
    padding: 50px 0;         /* khoảng cách trên/dưới vừa đủ */
}

/* Card */
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
