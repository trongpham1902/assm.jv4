package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.UserDAO;
import poly.entity.User;

import java.io.IOException;

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

	    // ================== Xử lý tiếng Việt ==================
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");

	    // ================== Lấy dữ liệu từ form ==================
	    String username = request.getParameter("username").trim();
	    String pass = request.getParameter("password").trim();
	    String fullname = request.getParameter("fullname").trim();
	    String email = request.getParameter("email").trim();

	    // ================== Validate dữ liệu ==================
	    if(username.isEmpty() || pass.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
	        request.setAttribute("message", "Vui lòng điền đầy đủ thông tin!");
	        request.setAttribute("view", "/site/user/register.jsp");
	        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
	        return;
	    }

	    // ================== Kiểm tra username/email trùng ==================
	    User existingUser = userDAO.findById(User.class, username);
	    if(existingUser != null) {
	        request.setAttribute("message", "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác!");
	        request.setAttribute("view", "/site/user/register.jsp");
	        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
	        return;
	    }

	    // ================== Tạo User mới ==================
	    try {
	        User user = new User();
	        user.setId(username);
	        user.setPassword(pass);
	        user.setFullname(fullname);
	        user.setEmail(email);
	        user.setAdmin(false); // mặc định user thường

	        userDAO.create(user); // Hibernate insert vào DB

	        // ================== Thành công ==================
	        request.getSession().setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
	        response.sendRedirect(request.getContextPath() + "/Login");

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("message", "Đăng ký thất bại, có lỗi xảy ra!");
	        request.setAttribute("view", "/site/user/register.jsp");
	        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
	    }
	}


}
