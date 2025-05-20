package id.co.xinix.auth.modules.privilege.domain;

import id.co.xinix.auth.services.BaseColumnEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "privileges")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Privilege extends BaseColumnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uri")
    private String uri;

    @Column(name = "module")
    private String module;

    @Column(name = "submodule")
    private String submodule;

    @Column(name = "action")
    private String action;

    @Column(name = "method")
    private String method;

    @Column(name = "ordering")
    private String ordering;
}
