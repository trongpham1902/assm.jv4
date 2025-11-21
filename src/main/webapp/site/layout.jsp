<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Online Entertainment</title>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Layout 3 phần tách biệt hoàn toàn */
        html, body {
            height: 100%;
            margin: 0;
        }

        body {
            display: flex;
            flex-direction: column;
        }

        header {
            flex-shrink: 0; /* không bị bóp */
            width: 100%;
        }

        main {
            flex: 1 0 auto;
            width: 100%;
        }

        footer { 
            flex-shrink: 0; /* không bị bóp */
            width: 100%;
        }

        /* Navbar dày hơn */
        .navbar {
            padding-top: 18px !important;
            padding-bottom: 18px !important;
            font-size: 1.1rem;
        }
        .navbar-brand {
            font-size: 1.5rem;
        }

        /* Slider height nếu cần */
        .carousel-item img {
            height: 420px;
            object-fit: cover;
            width: 100%;
        }
    </style>
</head>
<body>

<!-- ===== HEADER (đứng riêng) ===== -->
<header>
    <jsp:include page="/common/header.jsp" />
</header>

<!-- ===== MAIN (đứng riêng) ===== -->
<main>
    <div class="d-flex justify-content-center mt-5">
        <jsp:include page="${view}" />
    </div>
</main>

<!-- ===== FOOTER (đứng riêng) ===== -->
<footer>
    <jsp:include page="/common/footer.jsp" />
</footer>

<!-- Bootstrap 5 JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>

