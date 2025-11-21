package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.ShareDAO;
import poly.dao.VideoDAO;
import poly.entity.Share;
import poly.entity.User;
import poly.entity.Video;

import java.io.IOException;

@WebServlet("/share")
public class ShareServlet extends HttpServlet {
    private VideoDAO videoDAO = new VideoDAO();
    private ShareDAO shareDAO = new ShareDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy user từ session
        User user = (User) request.getSession().getAttribute("user");

        // Nếu chưa login → redirect ngay
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return; // quan trọng: stop tiếp
        }

        // Lấy video id
        String videoId = request.getParameter("id");
        if (videoId != null) {
            Video video = videoDAO.findById(videoId);
            if (video != null) {
                request.setAttribute("video", video); // gửi video qua JSP
            }
        }

        // Chỉ forward 1 lần, sau khi đã check xong
        request.setAttribute("view", "/site/home/share.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // AuthFilter cũng đã chặn nếu chưa login
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String videoId = request.getParameter("videoId");
        String emails = request.getParameter("emails");

        Video video = videoDAO.findById(videoId);

        if (video != null && emails != null && !emails.isEmpty()) {
            // Tạo Share entity
            Share share = new Share();
            share.setUser(user);
            share.setVideo(video);
            share.setEmails(emails);
            shareDAO.create(share); // DAO phải mở/đóng EntityManager đúng

            request.setAttribute("message", "Gửi link thành công đến: " + emails);
        } else {
            request.setAttribute("message", "Lỗi: Không thể gửi link.");
        }

        // Gửi lại video và view để hiển thị
        request.setAttribute("video", video);
        request.setAttribute("view", "/site/home/share.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}

