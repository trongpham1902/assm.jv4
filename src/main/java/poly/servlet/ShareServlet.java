package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import poly.dao.ShareDAO;
import poly.dao.VideoDAO;
import poly.entity.EmailUtils;
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

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/Login");
			return;
		}

		String videoId = request.getParameter("videoId");
		String email = request.getParameter("email");

		// 1️⃣ Validate dữ liệu
		if (videoId == null || email == null || email.trim().isEmpty()) {
			request.setAttribute("message", "Vui lòng nhập email người nhận!");
			forward(request, response);
			return;
		}

		email = email.trim();

		// 2️⃣ Validate email format (GIỐNG REGISTRATION)
		if (!isValidEmail(email)) {
			request.setAttribute("message", "Email không hợp lệ!");
			forward(request, response);
			return;
		}

		Video video = videoDAO.findById(videoId);
		if (video == null) {
			request.setAttribute("message", "Video không tồn tại!");
			forward(request, response);
			return;
		}

		try {
			// 3️⃣ Tạo link video
			String link = request.getRequestURL().toString().replace("/share", "/detail?id=" + videoId);

			// 4️⃣ Gửi email (GIỐNG REGISTRATION)
			EmailUtils.sendEmail(email, "Chia sẻ video hay cho bạn",
					"Xin chào,\n\n" + user.getFullname() + " đã chia sẻ cho bạn một video:\n" + video.getTitle()
							+ "\n\n" + "▶ Xem trên YouTube: https://www.youtube.com/watch?v=" + video.getId());

			// 5️⃣ Lưu lịch sử share
			Share share = new Share();
			share.setUser(user);
			share.setVideo(video);
			share.setEmails(email);
			shareDAO.create(share);

			request.setAttribute("message", "Đã gửi link thành công đến: " + email);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Gửi link thất bại!");
		}

		request.setAttribute("video", video);
		request.setAttribute("view", "/site/home/share.jsp");
		request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
	}

	private boolean isValidEmail(String email) {
		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return email != null && email.matches(regex);
	}

	private void forward(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("view", "/site/home/share.jsp");
		request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
	}
}
