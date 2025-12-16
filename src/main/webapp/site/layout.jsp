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
        /* Layout 3 phần tách biệt */
        html, body {
            height: 100%;
            margin: 0;
        }

        body {
            display: flex;
            flex-direction: column;
        }

        header, footer {
            width: 100%;
            flex-shrink: 0;
        }

        main {
            flex: 1 0 auto;
            width: 100%;
        }

        /* Navbar đẹp hơn */
        .navbar {
            padding-top: 18px;
            padding-bottom: 18px;
            font-size: 1.1rem;
        }

        .navbar-brand {
            font-size: 1.5rem;
        }

        /* Slider */
        .carousel-item img {
            height: 420px;
            object-fit: cover;
            width: 100%;
        }
    </style>
</head>
<body>

<!-- ===== HEADER ===== -->
<header>
    <jsp:include page="/common/header.jsp" />
</header>

<!-- ===== MAIN (QUAN TRỌNG) ===== -->
<main class="container mt-4">
    <jsp:include page="${view}" />
</main>

<!-- ===== FOOTER ===== -->
<footer>
    <jsp:include page="/common/footer.jsp" />
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
