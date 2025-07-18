package id.co.xinix.spring.modules.example.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExampleUpdatedResult(
    Long id,
    String code,
    String name,
    String nik,
    String hobbies,
    Integer citizen,
    String phone,
    Integer age,
    String taxpayerNumber,
    LocalDate date,
    Boolean marriedStatus,
    String gender,
    String checkbox,
    String inputDateYear,
    LocalTime inputDate,
    String address,
    String ProfilePicture,
    String MultipleImage,
    String SupportingDocument,
    String MultipleFile
) {}
