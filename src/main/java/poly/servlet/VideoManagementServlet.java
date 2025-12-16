package poly.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import poly.dao.VideoDAO;
import poly.entity.Video;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

//@MultipartConfig(
//	    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//	    maxFileSize = 1024 * 1024 * 10,      // 10MB
//	    maxRequestSize = 1024 * 1024 * 50    // 50MB
//	)
@WebServlet("/admin/video-management")
@MultipartConfig
public class VideoManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO;

    @Override
    public void init() throws ServletException {
        videoDAO = new VideoDAO();
    }

    // ====================== GET ======================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String videoId = request.getParameter("id");

        String formState = "create";
        Video formVideo = new Video();

        if ("edit".equals(action) && videoId != null) {
            Video video = videoDAO.findById(Video.class, videoId);
            if (video != null) {
                formVideo = video;
                formState = "edit";
            }

        } else if ("delete".equals(action) && videoId != null) {
            try {
                Video v = new Video();
                v.setId(videoId);
                videoDAO.delete(v);
                request.setAttribute("message", "Xóa video thành công!");
            } catch (Exception e) {
                request.setAttribute("message", "Không thể xóa video!");
            }

        } else if ("reset".equals(action)) {
            formVideo = new Video();
            formState = "create";
        }

        // ---------- PHÂN TRANG ----------
        int pageSize = 5;
        int currentPage = 1;

        try {
            currentPage = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignored) {}

        long totalVideos = videoDAO.count();
        int totalPages = (int) Math.ceil((double) totalVideos / pageSize);

        List<Video> videoList = videoDAO.findAll(currentPage, pageSize);

        // ---------- ATTR ----------
        request.setAttribute("videoList", videoList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);

        request.setAttribute("formVideo", formVideo);
        request.setAttribute("formState", formState);
        request.setAttribute("view", "/admin/video-management.jsp");

        request.getRequestDispatcher("/site/layout.jsp")
               .forward(request, response);
    }

    // ====================== POST ======================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                Video video = new Video();
                video.setId(request.getParameter("id"));
                video.setTitle(request.getParameter("title"));
                video.setDescription(request.getParameter("description"));

                String views = request.getParameter("views");
                video.setViews(views != null && !views.isEmpty() ? Integer.parseInt(views) : 0);

                video.setActive(Boolean.parseBoolean(request.getParameter("active")));

                // ---------- UPLOAD POSTER ----------
                Part part = request.getPart("cover");
                if (part != null && part.getSize() > 0) {
                    String uploadDir = request.getServletContext().getRealPath("/logos");
                    File dir = new File(uploadDir);
                    if (!dir.exists()) dir.mkdirs();

                    String fileName = Paths.get(part.getSubmittedFileName())
                                           .getFileName().toString();
                    part.write(uploadDir + File.separator + fileName);
                    video.setPoster(fileName);
                } else {
                    video.setPoster("placeholder.jpg");
                }

                // ---------- DB ----------
                if (videoDAO.findById(Video.class, video.getId()) != null) {
                    request.setAttribute("message", "ID video đã tồn tại!");
                    request.setAttribute("formVideo", video);
                    request.setAttribute("formState", "create");
                    doGet(request, response);
                    return;
                }
                videoDAO.create(video);
                response.sendRedirect(request.getContextPath() + "/admin/video-management");

            } else if ("update".equals(action)) {
                // Lấy video cũ từ DB
                Video old = videoDAO.findById(Video.class, request.getParameter("id"));
                if (old != null) {
                    old.setTitle(request.getParameter("title"));
                    old.setDescription(request.getParameter("description"));

                    String views = request.getParameter("views");
                    old.setViews(views != null && !views.isEmpty() ? Integer.parseInt(views) : 0);

                    old.setActive(Boolean.parseBoolean(request.getParameter("active")));

                    // ---------- UPLOAD POSTER ----------
                    Part part = request.getPart("cover");
                    if (part != null && part.getSize() > 0) {
                        String uploadDir = request.getServletContext().getRealPath("/logos");
                        File dir = new File(uploadDir);
                        if (!dir.exists()) dir.mkdirs();

                        String fileName = Paths.get(part.getSubmittedFileName())
                                               .getFileName().toString();
                        part.write(uploadDir + File.separator + fileName);
                        old.setPoster(fileName);
                    }
                    // Nếu không upload ảnh mới thì giữ nguyên poster cũ

                    // ---------- DB ----------
                    videoDAO.update(old);

                    // ✅ PRG – load lại DB để hiển thị đúng form
                    response.sendRedirect(
                        request.getContextPath()
                        + "/admin/video-management?action=edit&id=" + old.getId()
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi hệ thống!");
            doGet(request, response);
        }
    }
}
