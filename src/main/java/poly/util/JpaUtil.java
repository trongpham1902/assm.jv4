package poly.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

    /**
     * EntityManagerFactory chịu trách nhiệm quản lý các EntityManager.
     * Nó được khởi tạo một lần duy nhất.
     */
    private static EntityManagerFactory factory;

    /**
     * Phương thức này trả về một EntityManager để thực hiện các thao tác
     * với cơ sở dữ liệu.
     * * @return EntityManager
     */
    public static EntityManager getEntityManager() {
        // Nếu factory chưa được khởi tạo hoặc đã bị đóng, hãy tạo mới
        if (factory == null || !factory.isOpen()) {
            // "PolyOE" là tên của persistence-unit trong file persistence.xml
            // Đảm bảo tên này khớp chính xác!
            factory = Persistence.createEntityManagerFactory("PolyOE");
        }
        // Tạo và trả về một EntityManager mới từ factory
        return factory.createEntityManager();
    }

    /**
     * Phương thức này đóng EntityManagerFactory khi ứng dụng kết thúc
     * để giải phóng tài nguyên.
     */
    public static void shutdown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
        factory = null; // Gán lại là null để có thể khởi tạo lại nếu cần
    }
}