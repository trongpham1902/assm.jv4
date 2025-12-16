package poly.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import java.util.List;

@Entity
@Table(name = "\"User\"") // Vì User là từ khóa SQL Server
public class User {

    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Fullname", nullable = false)
    private String fullname;

    @Column(name = "Admin")
    private Boolean admin = false;

    // ========== RELATIONSHIPS ==========

    // User -> Favorite (1-N)
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.REMOVE,
        orphanRemoval = true
    )
    private List<Favorite> favorites;

    // User -> Share (1-N)
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.REMOVE,
        orphanRemoval = true
    )
    private List<Share> shares;

    // ========== CONSTRUCTORS ==========

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    // ========== GETTERS & SETTERS ==========

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }
}
