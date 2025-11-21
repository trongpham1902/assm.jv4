package poly.dao;


import poly.entity.Video;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class VideoDAO extends AbstractDAO<Video> {
    public VideoDAO() {
        super();
    }
    
    // Ví dụ: Lấy 6 video có lượt xem nhiều nhất (theo yêu cầu)
    public List<Video> findTop6ByViews() {
        String jsql = "SELECT o FROM Video o WHERE o.active = true ORDER BY o.views DESC";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setMaxResults(6); // Lấy tối đa 6
        return query.getResultList();
    }
    public Video findById(String id) {
    	Video video = null;
        try {
        	  String jpql = "SELECT v FROM Video v WHERE v.id = :id";
        	  TypedQuery<Video> query = em.createQuery(jpql, Video.class);
              query.setParameter("id", id);
              // getSingleResult sẽ ném NoResultException nếu không tìm thấy
              try {
                  video = query.getSingleResult();
              } catch (javax.persistence.NoResultException e) {
                  video = null; // không tìm thấy
              }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(em != null) {
                em.close();
            }
        }
        return video;
    }
    
    // Thêm các phương thức phân trang...
}