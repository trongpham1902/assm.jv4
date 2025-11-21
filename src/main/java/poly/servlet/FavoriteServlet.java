package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.FavoriteDAO;
import poly.entity.Favorite;
import poly.entity.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/favorites")
public class FavoriteServlet extends HttpServlet {
    private FavoriteDAO favoriteDAO = new FavoriteDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy user từ session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        // Lấy danh sách video đã thích của user
        List<Favorite> favorites = favoriteDAO.findByUser(user.getId());

        // Đưa vào request để JSP hiển thị
        request.setAttribute("favorites", favorites);

        // Chuyển hướng đến trang JSP hiển thị danh sách
        
        
        request.setAttribute("view", "/views/favorite-report.jsp"); // View sẽ được include
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}
