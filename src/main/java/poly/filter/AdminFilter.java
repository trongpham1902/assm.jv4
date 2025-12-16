package poly.filter;


import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import poly.entity.User;



@WebFilter(urlPatterns = {"/admin/*"}) // Chặn tất cả URL /admin/*
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            // Chưa đăng nhập
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (!Boolean.TRUE.equals(user.getAdmin())) {
            // Không phải admin
            req.getSession().setAttribute("message", "Bạn không có quyền truy cập trang admin!");
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }

        // Là admin → cho phép đi tiếp
        chain.doFilter(request, response);
    }
}
