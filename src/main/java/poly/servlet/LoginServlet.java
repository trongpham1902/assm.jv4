package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import poly.dao.UserDAO;
import poly.entity.User;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import poly.dao.UserDAO;
import poly.entity.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward sang JSP login
        request.setAttribute("view", "/site/user/login.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String userId = request.getParameter("username");
        String pass = request.getParameter("password");
        String remember = request.getParameter("remember");

        String hashedPass = null;
        try {
            hashedPass = hashPassword(pass); // hash password nhập
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi khi xử lý mật khẩu!");
            request.setAttribute("view", "/site/user/login.jsp");
            request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
            return;
        }

        // Tìm user theo username và password hash
        User user = userDAO.checkLogin(userId, hashedPass); // bạn cần viết checkLoginHash trong DAO

        if (user != null) {
            // Đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // Lưu user vào session

            // Xử lý "Remember me" nếu cần
            // if (remember != null) { ... }

            response.sendRedirect(request.getContextPath() + "/index");
        } else {
            // Đăng nhập thất bại
            request.setAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
            request.setAttribute("view", "/site/user/login.jsp");
            request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
        }
    }

    // Hàm hash mật khẩu SHA-256
    public static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
