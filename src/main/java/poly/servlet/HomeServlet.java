package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.FavoriteDAO;
import poly.dao.VideoDAO;
import poly.entity.Favorite;
import poly.entity.User;
import poly.entity.Video;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/index")
public class HomeServlet extends HttpServlet {
	private VideoDAO videoDAO = new VideoDAO();
	private FavoriteDAO favoriteDAO = new FavoriteDAO();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 1. Lấy danh sách video (ví dụ top 6)
	    List<Video> videos = videoDAO.findTop6ByViews(); 

	    // 2. Lấy user từ session
	    User currentUser = (User) request.getSession().getAttribute("user");

	    // 3. Tạo favoriteMap nếu user đã login
	    Map<String, Boolean> favoriteMap = new HashMap<>();
	    if (currentUser != null) {
	        List<Favorite> favorites = favoriteDAO.findByUser(currentUser.getId());
	        for (Favorite f : favorites) {
	            favoriteMap.put(f.getVideo().getId(), true); // Key là String → ok
	        }
	    }
	    request.setAttribute("favoriteMap", favoriteMap);

	    // 4. Đưa dữ liệu xuống JSP
	    request.setAttribute("videos", videos);
	    request.setAttribute("favoriteMap", favoriteMap);
	    request.setAttribute("view", "/site/home/index.jsp"); // View sẽ được include

	    // 5. Forward
	    request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
	}




}
