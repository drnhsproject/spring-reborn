package id.co.xinix.auth.modules.roleprivilege.domain;

import id.co.xinix.auth.services.BaseColumnEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "role_privilege")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RolePrivilege extends BaseColumnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "uri")
    private String uri;

    @Column(name = "method")
    private String method;
}
