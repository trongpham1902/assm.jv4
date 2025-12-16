package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.VideoDAO;
import poly.entity.Video;

import java.io.IOException;
import java.util.List;
@WebServlet("/detail")
public class VideoDetailServlet extends HttpServlet {

    private VideoDAO videoDAO = new VideoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String videoId = request.getParameter("id");

        // 1️⃣ Không có id → về trang chủ
        if (videoId == null || videoId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // 2️⃣ Lấy video chi tiết
        Video video = videoDAO.findById(videoId);

        if (video == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // 3️⃣ Tăng lượt xem
        video.setViews(video.getViews() + 1);
        videoDAO.update(video);

        request.setAttribute("video", video);

        // ==================================================
        // 4️⃣ RELATED VIDEOS (DÙNG DAO CÓ SẴN)
        // ==================================================

        // Lấy top 6 video nhiều view
        List<Video> relatedVideos = videoDAO.findTop6ByViews();

        // ❌ Loại bỏ video đang xem
        relatedVideos.removeIf(v -> v.getId().equals(video.getId()));

        request.setAttribute("relatedVideos", relatedVideos);

        // ==================================================
        // 5️⃣ FORWARD QUA LAYOUT
        // ==================================================
        request.setAttribute("view", "/site/home/detail.jsp");
        request.getRequestDispatcher("/site/layout.jsp")
               .forward(request, response);
    }
}
