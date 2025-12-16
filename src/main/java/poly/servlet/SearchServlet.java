package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.VideoDAO;
import poly.entity.Video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private VideoDAO videoDAO = new VideoDAO(); // DAO của bạn

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Video> result = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            result = videoDAO.searchByKeyword(keyword.trim());
        }

        request.setAttribute("videos", result);
        request.setAttribute("keyword", keyword);
        request.setAttribute("view", "/site/home/search-results.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}
