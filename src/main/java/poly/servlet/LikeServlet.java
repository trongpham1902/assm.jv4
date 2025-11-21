package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.FavoriteDAO;
import poly.entity.Favorite;
import poly.entity.User;
import poly.entity.Video;

import java.io.IOException;

@WebServlet("/like")
public class LikeServlet extends HttpServlet {
    private FavoriteDAO favoriteDAO = new FavoriteDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy video id từ request
        String videoId = request.getParameter("id");

        // Lấy user từ session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        // Kiểm tra xem user đã like video chưa
        Favorite favorite = favoriteDAO.findFavorite(user.getId(), videoId);

        if (favorite != null) {
            // Nếu đã like → xóa
            favoriteDAO.delete(favorite);
        } else {
            // Nếu chưa like → thêm mới
            favorite = new Favorite();
            favorite.setUser(user);

            Video video = new Video();
            video.setId(videoId);
            favorite.setVideo(video);

            favoriteDAO.create(favorite);
        }

        // Quay lại trang trước
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : request.getContextPath());
    }
}

