package poly.dao;


import poly.entity.Video;
import poly.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class VideoDAO extends AbstractDAO<Video> {
    public VideoDAO() {
        super();
    }

    public List<Video> searchByKeyword(String keyword) {
        String jpql = "SELECT v FROM Video v "
                    + "WHERE v.active = true AND "
                    + "(LOWER(v.title) LIKE :kw OR LOWER(v.description) LIKE :kw) "
                    + "ORDER BY v.views DESC";
        TypedQuery<Video> query = em.createQuery(jpql, Video.class);
        query.setParameter("kw", "%" + keyword.toLowerCase() + "%");
        return query.getResultList();
    }

    public List<Video> findTop6ByViews() {
        String jsql = "SELECT o FROM Video o WHERE o.active = true ORDER BY o.views DESC";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setMaxResults(6);
        return query.getResultList();
    }

    public Video findById(String id) {
        String jpql = "SELECT v FROM Video v WHERE v.id = :id";
        TypedQuery<Video> query = em.createQuery(jpql, Video.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public long count() {
        String jsql = "SELECT count(o) FROM Video o";
        TypedQuery<Long> query = em.createQuery(jsql, Long.class);
        return query.getSingleResult();
    }

    public List<Video> findAll(int pageNumber, int pageSize) {
        String jsql = "SELECT o FROM Video o";
        TypedQuery<Video> query = em.createQuery(jsql, Video.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}