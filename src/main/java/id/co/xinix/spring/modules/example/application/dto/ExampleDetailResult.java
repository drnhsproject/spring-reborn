package id.co.xinix.spring.modules.example.application.dto;

import id.co.xinix.media.modules.MediaDataResult;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ExampleDetailResult(
    Long id,
    String code,
    String name,
    String nik,
    List<String> hobbies,
    Integer citizen,
    String phone,
    Integer age,
    String taxpayer_number,
    LocalDate dob,
    Boolean married_status,
    String gender,
    List<Integer> checkbox,
    String input_date_year,
    LocalTime input_time,
    String address,
    MediaDataResult profile_picture,
    List<MediaDataResult> multiple_image,
    MediaDataResult supporting_document,
    List<MediaDataResult> multiple_file
) {}
