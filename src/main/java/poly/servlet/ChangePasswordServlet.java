package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.UserDAO;
import poly.entity.User;

import java.io.IOException;

@WebServlet("/ChangePassword")
public class ChangePasswordServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("view", "/site/user/change-password.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email"); // từ form, nếu là user reset từ email
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        String message = null;
        User user = null;

        try {
            // 1. Lấy user
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("user") != null) {
                user = (User) session.getAttribute("user"); // user login
            } else if (email != null) {
                user = userDAO.findByEmail(email); // user reset từ email
            }
            if (user == null) {
                message = "User not found!";
            } else if (!hashPassword(currentPassword).equals(user.getPassword())) {
                message = "Current password is incorrect!";
            } else if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                message = "New password and confirm password are required!";
            } else if (!newPassword.equals(confirmPassword)) {
                message = "Passwords do not match!";
            } else {
                // Cập nhật password
                user.setPassword(hashPassword(newPassword));
                userDAO.update(user);
                message = "Password changed successfully!";
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "Error while processing password!";
        }

        request.setAttribute("message", message);
        request.setAttribute("view", "/site/user/change-password.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    // Hash password SHA-256
    private String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}