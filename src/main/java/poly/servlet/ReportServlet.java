package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import poly.dao.FavoriteDAO;
import poly.dao.ShareDAO;
import poly.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    FavoriteDAO favoriteDAO = new FavoriteDAO();
    ShareDAO shareDAO = new ShareDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            thongKeYeuThich(req, resp);
            return;
        }

        switch (action) {
            case "favorite":
                thongKeYeuThich(req, resp);
                break;

            case "favorite-users":
                nguoiYeuThichTheoVideo(req, resp);
                break;

            case "share-users":
                nguoiNhanTheoVideo(req, resp);
                break;

            default:
                thongKeYeuThich(req, resp);
        }
    }

    // 1️⃣ Thống kê số người yêu thích theo tiểu phẩm
    private void thongKeYeuThich(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Object[]> list = favoriteDAO.countFavoriteByVideo();
        req.setAttribute("stats", list);

        req.setAttribute("view", "/site/home/report.jsp"); // View sẽ được include

	    // 5. Forward
	    req.getRequestDispatcher("/site/layout.jsp").forward(req, resp);
    }

    // 2️⃣ Lọc người yêu thích theo tiểu phẩm
    private void nguoiYeuThichTheoVideo(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String videoId = req.getParameter("videoId");
        List<User> users = favoriteDAO.findUsersByVideo(videoId);

        req.setAttribute("users", users);
        req.setAttribute("videoId", videoId);

        req.setAttribute("view", "/site/home/report.jsp"); // View sẽ được include

	    // 5. Forward
	    req.getRequestDispatcher("/site/layout.jsp").forward(req, resp);
    }

    // 3️⃣ Lọc người nhận theo tiểu phẩm đã gửi
    private void nguoiNhanTheoVideo(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String videoId = req.getParameter("videoId");
        List<String> emails = shareDAO.findEmailsByVideo(videoId);

        req.setAttribute("emails", emails);
        req.setAttribute("videoId", videoId);

        req.setAttribute("view", "/site/home/report.jsp"); // View sẽ được include

	    // 5. Forward
	    req.getRequestDispatcher("/site/layout.jsp").forward(req, resp);
    }
}
