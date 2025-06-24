package id.co.xinix.spring.modules.example.application.usecase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.xinix.media.modules.MediaDataResult;
import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.exception.NotFoundException;
import id.co.xinix.spring.modules.example.application.dto.ExampleDetailResult;
import id.co.xinix.spring.modules.example.domain.Example;
import id.co.xinix.spring.modules.example.domain.ExampleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetExampleDetailById {

    private final ExampleRepository exampleRepository;
    private final ObjectMapper objectMapper;

    public ExampleDetailResult handle(Long id) {
        Example entity = exampleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("example not found"));
        return mapToDto(entity);
    }

    private ExampleDetailResult mapToDto(Example entity) {
        return new ExampleDetailResult(
            entity.getId(),
            entity.getCode(),
            entity.getName(),
            entity.getNik(),
            splitCommaSeparated(entity.getHobbies()),
            entity.getCitizen(),
            entity.getPhone(),
            entity.getAge(),
            entity.getTaxpayerNumber(),
            entity.getDob(),
            entity.getMarriedStatus(),
            entity.getGender(),
            splitCommaSeparatedInt(entity.getCheckbox()),
            entity.getInputDateYear(),
            entity.getInputTime(),
            entity.getAddress(),
            parse(entity.getProfilePicture(), MediaDataResult.class),
            parse(entity.getMultipleImage(), new TypeReference<List<MediaDataResult>>() {}),
            parse(entity.getSupportingDocument(), MediaDataResult.class),
            parse(entity.getMultipleFile(), new TypeReference<List<MediaDataResult>>() {})
        );
    }

    private List<String> splitCommaSeparated(String input) {
        return input == null ? List.of() :
            Arrays.stream(input.split(",")).map(String::trim).toList();
    }

    private List<Integer> splitCommaSeparatedInt(String input) {
        return input == null ? List.of() :
            Arrays.stream(input.split(",")).map(String::trim).map(Integer::parseInt).toList();
    }

    private <T> T parse(String json, Class<T> clazz) {
        if (json == null || json.isBlank()) return null;
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse json: " + json, e);
        }
    }

    private <T> T parse(String json, TypeReference<T> typeRef) {
        if (json == null || json.isBlank()) return null;
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse json: " + json, e);
        }
    }
}
