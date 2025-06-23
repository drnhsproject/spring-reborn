package id.co.xinix.spring.modules.example.domain;

import id.co.xinix.spring.services.BaseColumnEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "example")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Example extends BaseColumnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    private String name;

    private String nik;

    private String hobbies;

    private Integer citizen;

    private String phone;

    private Integer age;

    private String taxpayerNumber;

    private LocalDate dob;

    private Boolean marriedStatus;

    private String gender;

    private String checkbox;

    private String inputDateYear;

    private LocalTime inputTime;

    private String address;
}
