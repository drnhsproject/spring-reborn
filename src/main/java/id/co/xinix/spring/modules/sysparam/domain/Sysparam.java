package id.co.xinix.spring.modules.sysparam.domain;

import id.co.xinix.spring.services.BaseColumnEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sysparams")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sysparam extends BaseColumnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "`group`")
    private String group;

    @Column(name = "`key`")
    private String key;

    @Column(name = "`value`")
    private String value;

    @Column(name = "long_value")
    private String longValue;

}