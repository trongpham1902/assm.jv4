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
import java.util.Random;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("view", "/site/user/register.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username").trim();
        String fullname = request.getParameter("fullname").trim();
        String email = request.getParameter("email").trim();

        // Validate dữ liệu
        if(username.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
            request.setAttribute("message", "Vui lòng điền đầy đủ thông tin!");
            request.setAttribute("view", "/site/user/register.jsp");
            request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
            return;
        }

        // Kiểm tra username/email trùng
        User existingUser = userDAO.findById(User.class, username);
        if(existingUser != null) {
            request.setAttribute("message", "Tên đăng nhập đã tồn tại!");
            request.setAttribute("view", "/site/user/register.jsp");
            request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
            return;
        }

        try {
            // Tạo mật khẩu ngẫu nhiên
            String randomPass = generateRandomPassword(8);
            String hashedPass = hashPassword(randomPass);

            // Tạo User mới
            User user = new User();
            user.setId(username);
            user.setPassword(hashedPass);
            user.setFullname(fullname);
            user.setEmail(email);
            user.setAdmin(false);

            // Lưu vào DB
            userDAO.create(user);

            // Gửi email mật khẩu
            EmailUtils.sendEmail(email, "Thông tin đăng ký tài khoản", 
                                 "Chào " + fullname + ", mật khẩu đăng nhập của bạn là: " + randomPass);

            request.getSession().setAttribute("message", 
                "Đăng ký thành công! Mật khẩu đã được gửi vào email.");
            response.sendRedirect(request.getContextPath() + "/Login");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Đăng ký thất bại, có lỗi xảy ra!");
            request.setAttribute("view", "/site/user/register.jsp");
            request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
        }
    }

    // Hàm tạo password ngẫu nhiên
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
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
