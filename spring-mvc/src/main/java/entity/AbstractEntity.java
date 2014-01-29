package entity;

import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Transient
    public static final long serialVersionUID = 1L;

    @Field
    @Id
    @GeneratedValue
    private long id;

    @Version
    private Integer version;

    @Field
    @Column(insertable = true, updatable = false, nullable = false)
    private String  createdBy;

    @Field
    @Column(insertable = true, updatable = false, nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdAt;

    @Field
    @Column(insertable = false, updatable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    Integer getVersion() {
        return version;
    }

    void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    void onCreate() {
        if(createdAt == null) {
            createdAt = new Date();
        }

        this.updatedAt = new Date();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = new Date();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        AbstractEntity other = (AbstractEntity) obj;

        if (id != other.id) {
            return false;
        }

        return true;
    }
}
