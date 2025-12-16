package poly.dao;


import poly.entity.Favorite;
import poly.entity.User;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class FavoriteDAO extends AbstractDAO<Favorite> {

    public FavoriteDAO() {
        super();
    }

    // ==================================================
    // CÁC HÀM CŨ (GIỮ NGUYÊN – ĐÃ ĐÚNG)
    // ==================================================

    /**
     * Lấy tất cả video yêu thích của một người dùng
     */
    public List<Favorite> findByUser(String userId) {
        String jpql =
            "SELECT f FROM Favorite f WHERE f.user.id = :uid";

        TypedQuery<Favorite> query =
            em.createQuery(jpql, Favorite.class);
        query.setParameter("uid", userId);

        return query.getResultList();
    }

    /**
     * Tìm một Favorite theo user + video
     */
    public Favorite findFavorite(String userId, String videoId) {
        String jpql =
            "SELECT f FROM Favorite f " +
            "WHERE f.user.id = :uid AND f.video.id = :vid";

        TypedQuery<Favorite> query =
            em.createQuery(jpql, Favorite.class);
        query.setParameter("uid", userId);
        query.setParameter("vid", videoId);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // ==================================================
    // BÁO CÁO – THỐNG KÊ (ALL IN)
    // ==================================================

    /**
     * 1️⃣ Thống kê số người yêu thích của từng tiểu phẩm (Video)
     * @return List<Object[]> [0]=title, [1]=count
     */
    public List<Object[]> countFavoriteByVideo() {
        String jpql =
            "SELECT f.video.title, COUNT(f.id) " +
            "FROM Favorite f " +
            "GROUP BY f.video.title";

        return em.createQuery(jpql, Object[].class)
                 .getResultList();
    }

    /**
     * 2️⃣ Lọc người yêu thích theo tiểu phẩm
     * @param videoId ID video
     * @return danh sách User
     */
    public List<User> findUsersByVideo(String videoId) {
        String jpql =
            "SELECT f.user " +
            "FROM Favorite f " +
            "WHERE f.video.id = :vid";

        TypedQuery<User> query =
            em.createQuery(jpql, User.class);
        query.setParameter("vid", videoId);

        return query.getResultList();
    }
}