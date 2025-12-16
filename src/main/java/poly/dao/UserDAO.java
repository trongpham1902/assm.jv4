package poly.dao;

import poly.entity.User;
import poly.util.JpaUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;

public class UserDAO extends AbstractDAO<User> {

	public UserDAO() {
		super();
	}

	// Tìm user theo email
	public User findByEmail(String email) {
		String jsql = "SELECT o FROM User o WHERE o.email = :email";
		TypedQuery<User> query = em.createQuery(jsql, User.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// Check login
	public User checkLogin(String userId, String password) {
		String jsql = "SELECT o FROM User o WHERE o.id = :uid AND o.password = :pass";
		TypedQuery<User> query = em.createQuery(jsql, User.class);
		query.setParameter("uid", userId);
		query.setParameter("pass", password);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// ⭐⭐ Tìm user theo ID (bổ sung theo yêu cầu)
	public User findById(String id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			return em.find(User.class, id);
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

	// ⭐⭐ Update thông tin user (bổ sung theo yêu cầu)
	public User update(User user) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();

			// Lấy entity hiện tại từ DB
			User existing = em.find(User.class, user.getId());
			if (existing == null) {
				throw new IllegalArgumentException("User not found with id: " + user.getId());
			}

			// Giữ lại password nếu user không gửi cập nhật password
			if (user.getPassword() == null) {
				user.setPassword(existing.getPassword());
			}

			// Merge entity an toàn
			User merged = em.merge(user);

			trans.commit();
			return merged;
		} catch (Exception e) {
			trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	public void delete(String id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			User entity = em.find(User.class, id);
			if (entity != null) {
				em.remove(entity);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			em.close();
		}
	}

	// Đếm user
	public long count() {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			String jsql = "SELECT count(o) FROM User o";
			TypedQuery<Long> query = em.createQuery(jsql, Long.class);
			return query.getSingleResult();
		} finally {
			em.close();
		}
	}

	// Phân trang
	public List<User> findAll(int pageNumber, int pageSize) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			String jsql = "SELECT o FROM User o";
			TypedQuery<User> query = em.createQuery(jsql, User.class);
			query.setFirstResult((pageNumber - 1) * pageSize);
			query.setMaxResults(pageSize);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	public boolean isEmailExists(String email, String excludeUserId) {
	    EntityManager em = JpaUtil.getEntityManager();
	    try {
	        String jsql = "SELECT COUNT(u) FROM User u "
	                    + "WHERE u.email = :email AND u.id <> :id";
	        TypedQuery<Long> query = em.createQuery(jsql, Long.class);
	        query.setParameter("email", email);
	        query.setParameter("id", excludeUserId);

	        return query.getSingleResult() > 0;
	    } finally {
	        em.close();
	    }
}}
