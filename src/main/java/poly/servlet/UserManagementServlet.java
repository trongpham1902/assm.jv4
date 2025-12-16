package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.UserDAO;
import poly.entity.User;

import java.io.IOException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;


import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import poly.dao.UserDAO;
import poly.entity.User;

@WebServlet("/admin/user-management")
public class UserManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    public UserManagementServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("id");

        String formState = "create";
        User formUser = null;

        if ("edit".equals(action) && userId != null && !userId.isEmpty()) {
            formUser = userDAO.findById(userId);
            if (formUser != null) {
                formState = "edit";
            } else {
                formState = "create";
            }
        }

        // Pagination
        int pageSize = 10;
        long totalUsers = userDAO.count();
        int totalPages = (int) Math.max(1, Math.ceil((double) totalUsers / pageSize));

        String pageParam = request.getParameter("page");
        int currentPage = 1;
        try {
            if (pageParam != null && !pageParam.isEmpty()) {
                currentPage = Integer.parseInt(pageParam);
                if (currentPage < 1) currentPage = 1;
                if (currentPage > totalPages) currentPage = totalPages;
            }
        } catch (NumberFormatException e) {
            currentPage = 1;
        }

        List<User> userList = userDAO.findAll(currentPage, pageSize);

        request.setAttribute("formUser", formUser);
        request.setAttribute("formState", formState);
        request.setAttribute("userList", userList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalUsers", totalUsers);

        // view (nếu bạn dùng layout.jsp)
        request.setAttribute("view", "/admin/user-management.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action != null) {
            try {
            	if ("update".equals(action)) {
            	    String id = request.getParameter("id");
            	    User existing = userDAO.findById(id);
            	    if (existing != null) {
            	        String fullname = request.getParameter("fullname");
            	        String email = request.getParameter("email");
            	        String adminParam = request.getParameter("admin");
            	        String newPassword = request.getParameter("password");

            	        // ⭐ Check trùng email
            	        if (userDAO.isEmailExists(email, id)) {
            	            request.setAttribute("message", "Email đã tồn tại!");
            	            request.setAttribute("formUser", existing);
            	            request.setAttribute("formState", "edit");
            	            doGet(request, response);
            	            return;
            	        }

            	        // Cập nhật field cơ bản
            	        existing.setFullname(fullname);
            	        existing.setEmail(email);
            	        existing.setAdmin("true".equals(adminParam));

            	        if (newPassword != null && !newPassword.trim().isEmpty()) {
            	            existing.setPassword(newPassword);
            	        }

            	        userDAO.update(existing);

            	        request.setAttribute("message", "Cập nhật thành công!");
            	        request.setAttribute("formUser", existing);
            	        request.setAttribute("formState", "edit");
            	    }
            	}else if ("delete".equals(action)) {
                    String id = request.getParameter("id");
                    if (id != null && !id.isEmpty()) {
                        userDAO.delete(id);
                        request.setAttribute("message", "Xóa người dùng thành công!");
                    } else {
                        request.setAttribute("message", "Không tìm thấy id để xóa.");
                    }
                    request.setAttribute("formState", "create");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Lỗi: " + e.getMessage());
            }
        }

        // reload danh sách & trạng thái
        doGet(request, response);
    }
}
