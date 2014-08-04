package ua.com.itinterview.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

/**
 * Created by Bohdan on 04/08/2014.
 */
@Entity
@Table(name = "reset_password")
public class ResetPasswordEntity {

    @Id
    private String hash;
    private String email;

    public ResetPasswordEntity() {
    }

    public ResetPasswordEntity(String hash, String email) {
        this.hash = hash;
        this.email = email;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResetPasswordEntity that = (ResetPasswordEntity) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (hash != null ? !hash.equals(that.hash) : that.hash != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hash != null ? hash.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResetPassword{" +
                "hash='" + hash + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
