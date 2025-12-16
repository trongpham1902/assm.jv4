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

        request.setAttribute("view", "/site/user/login.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String userId = request.getParameter("username");
        String pass = request.getParameter("password");

        String hashedPass = null;
        try {
            hashedPass = hashPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi khi xử lý mật khẩu!");
            request.setAttribute("view", "/site/user/login.jsp");
            request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
            return;
        }

        // Kiểm tra login bằng username + password đã hash
        User user = userDAO.checkLogin(userId, hashedPass);

        if (user != null) {

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            
                response.sendRedirect(request.getContextPath() + "/index");
            

        } else {
            // Sai tài khoản hoặc mật khẩu
            request.setAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
            request.setAttribute("view", "/site/user/login.jsp");
            request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
        }
    }

    // Hash password SHA-256
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
