package id.co.xinix.auth.services;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseColumnEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private Instant createdTime;

    @LastModifiedDate
    @Column(name = "updated_time")
    private Instant updatedTime;

    @Column(name = "status", nullable = false)
    private Integer status = 1;

    @Size(max = 5000)
    @Column(name = "value_1", length = 5000)
    private String value1;

    @Size(max = 5000)
    @Column(name = "value_2", length = 5000)
    private String value2;
}
