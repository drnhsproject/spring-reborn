package id.co.xinix.spring.modules.userinformation.domain;

import id.co.xinix.spring.services.BaseColumnEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user_information")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserInformation extends BaseColumnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "gender")
    private String gender;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birth_date;

    @Column(name = "age")
    private Integer age;

}