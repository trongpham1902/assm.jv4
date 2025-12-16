<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="report-container">

<style>
/* ===== REPORT PAGE ===== */
.report-container {
    background: #fff;
    padding: 20px;
    border-radius: 6px;
    box-shadow: 0 1px 4px rgba(0,0,0,0.1);
    font-family: Arial, sans-serif;
}

.report-container h2 {
    margin: 0 0 20px 0;
}

/* ===== TABS ===== */
.report-tabs {
    display: flex;
    gap: 10px;
    margin-bottom: 20px;
    border-bottom: 2px solid #e0e0e0;
}

.report-tabs a {
    padding: 8px 14px;
    text-decoration: none;
    color: #333;
    background: #f3f3f3;
    border-radius: 4px 4px 0 0;
    font-weight: 500;
}

.report-tabs a.active {
    background: #0d6efd;
    color: #fff;
}

/* ===== TABLE ===== */
.report-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
}

.report-table th,
.report-table td {
    padding: 10px;
    border: 1px solid #ddd;
}

.report-table th {
    background: #f8f9fa;
    text-align: left;
}

/* ===== FORM ===== */
.report-form {
    margin-bottom: 15px;
}

.report-form input {
    padding: 6px 8px;
    width: 220px;
}

.report-form button {
    padding: 6px 12px;
    background: #0d6efd;
    color: #fff;
    border: none;
    cursor: pointer;
}

.report-form button:hover {
    background: #0b5ed7;
}
</style>

<h2>📊 BÁO CÁO – THỐNG KÊ</h2>

<!-- ================= TABS ================= -->
<div class="report-tabs">
    <a href="report?action=favorite"
       class="${empty param.action || param.action=='favorite' ? 'active' : ''}">
        📌 Thống kê yêu thích
    </a>

    <a href="report?action=favorite-users"
       class="${param.action=='favorite-users' ? 'active' : ''}">
        👥 Người yêu thích
    </a>

    <a href="report?action=share-users"
       class="${param.action=='share-users' ? 'active' : ''}">
        ✉️ Người nhận
    </a>
</div>

<!-- ================= TAB 1 ================= -->
<c:if test="${empty param.action || param.action=='favorite'}">
    <h3>📌 Thống kê số lượt yêu thích</h3>

    <table class="report-table">
        <tr>
            <th>Tiểu phẩm</th>
            <th>Số lượt yêu thích</th>
        </tr>

        <c:forEach var="row" items="${stats}">
            <tr>
                <td>${row[0]}</td>
                <td>${row[1]}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<!-- ================= TAB 2 ================= -->
<c:if test="${param.action=='favorite-users'}">
    <h3>👥 Người yêu thích theo tiểu phẩm</h3>

    <form class="report-form" method="get" action="report">
        <input type="hidden" name="action" value="favorite-users"/>
        Video ID:
        <input type="text" name="videoId" value="${param.videoId}" required/>
        <button type="submit">Xem</button>
    </form>

    <c:if test="${not empty users}">
        <table class="report-table">
            <tr>
                <th>User ID</th>
                <th>Email</th>
                <th>Họ tên</th>
            </tr>

            <c:forEach var="u" items="${users}">
                <tr>
                    <td>${u.id}</td>
                    <td>${u.email}</td>
                    <td>${u.fullname}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</c:if>

<!-- ================= TAB 3 ================= -->
<c:if test="${param.action=='share-users'}">
    <h3>✉️ Người nhận video đã gửi</h3>

    <form class="report-form" method="get" action="report">
        <input type="hidden" name="action" value="share-users"/>
        Video ID:
        <input type="text" name="videoId" value="${param.videoId}" required/>
        <button type="submit">Xem</button>
    </form>

    <c:if test="${not empty emails}">
        <ul>
            <c:forEach var="e" items="${emails}">
                <li>${e}</li>
            </c:forEach>
        </ul>
    </c:if>
</c:if>

</div>
