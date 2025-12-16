package poly.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import poly.dao.UserDAO;
import poly.entity.User;

@WebServlet("/edit-profile")
public class ProfileServlet extends HttpServlet {

    private UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");

        if (id == null || id.isEmpty()) {
            resp.sendError(400, "Missing user id");
            return;
        }

        User user = dao.findById(id);

        if (user == null) {
            resp.sendError(404, "User not found");
            return;
        }

        req.setAttribute("user", user);
        req.setAttribute("view", "/site/home/profile.jsp");

        req.getRequestDispatcher("/site/layout.jsp").forward(req, resp);
    }

    @Override
   
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            resp.sendError(400, "Invalid request: missing ID");
            return;
        }

        User user = dao.findById(id);
        if (user == null) {
            resp.sendError(404, "User not found");
            return;
        }

        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // ⭐ CHECK TRÙNG EMAIL
        if (dao.isEmailExists(email, id)) {
            req.setAttribute("error", "Email already exists!");
            req.setAttribute("user", user);
            req.setAttribute("view", "/site/home/profile.jsp");
            req.getRequestDispatcher("/site/layout.jsp").forward(req, resp);
            return;
        }

        // Update thông tin
        user.setFullname(fullname);
        user.setEmail(email);

        // Chỉ update password khi người dùng nhập
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }

        dao.update(user);

        req.setAttribute("message", "Update profile successfully!");
        req.setAttribute("user", user);
        req.setAttribute("view", "/site/home/profile.jsp");
        req.getRequestDispatcher("/site/layout.jsp").forward(req, resp);
    }

}
