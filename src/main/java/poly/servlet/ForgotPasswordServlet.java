package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.UserDAO;
import poly.entity.EmailUtils;
import poly.entity.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.UserDAO;
import poly.entity.EmailUtils;
import poly.entity.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

@WebServlet("/ForgotPassword")
public class ForgotPasswordServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("showForm", true);
        request.setAttribute("view", "/site/user/forgot-password.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailInput = request.getParameter("email");
        String message = null;

        if (emailInput == null || emailInput.isEmpty()) {
            message = "Email is required!";
        } else {
            User user = userDAO.findByEmail(emailInput);
            if (user == null) {
                message = "Email not found!";
            } else {
                try {
                    // Tạo password ngẫu nhiên
                    String randomPass = generateRandomPassword(8);
                    String hashedPass = hashPassword(randomPass);

                    // Lưu hash vào DB
                    user.setPassword(hashedPass);
                    userDAO.update(user);

                    // Gửi email password gốc
                    EmailUtils.sendEmail(user.getEmail(), "Your new password", "Your new password: " + randomPass);

                    message = "A new password has been sent to your email!";
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "Failed to send email!";
                }
            }
        }

        request.setAttribute("message", message);
        request.setAttribute("view", "/site/user/forgot-password.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    // Hàm tạo password ngẫu nhiên
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // Hash password SHA-256
    public static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}