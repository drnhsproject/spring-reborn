package id.co.xinix.spring.modules.example.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.example.application.dto.ExampleCommand;
import id.co.xinix.spring.modules.example.application.dto.ExampleCreatedResult;
import id.co.xinix.spring.modules.example.application.dto.ExampleHobbiesDTO;
import id.co.xinix.spring.modules.example.domain.Example;
import id.co.xinix.spring.modules.example.domain.ExampleRepository;
import id.co.xinix.spring.services.GenerateRandomCode;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@UseCase
@AllArgsConstructor
public class CreateExample {

    private final ExampleRepository exampleRepository;

    public ExampleCreatedResult handle(ExampleCommand command) {
        Example example = buildExampleFromCommand(command);
        Example savedExample = exampleRepository.save(example);

        if (savedExample == null) {
            throw new IllegalStateException("failed to save example");
        }

        return buildResultFromExample(savedExample);
    }

    private Example buildExampleFromCommand(ExampleCommand command) {
        Example example = new Example();
        example.setCode(generateCode());
        example.setName(command.getName());
        example.setNik(command.getNik());
        example.setHobbies(joinHobbies(command));
        example.setCitizen(command.getCitizen());
        example.setPhone(command.getPhone());
        example.setAge(command.getAge());
        example.setTaxpayerNumber(command.getTaxpayer_number());
        example.setDob(command.getDob());
        example.setMarriedStatus(command.getMarried_status());
        example.setGender(command.getGender());
        example.setCheckbox(joinCheckbox(command));
        example.setInputDateYear(command.getInput_date_year());
        example.setInputTime(command.getInput_time());
        example.setAddress(command.getAddress());
        return example;
    }

    private String generateCode() {
        return new GenerateRandomCode().generate("EXP_");
    }

    private String joinHobbies(ExampleCommand command) {
        return command.getHobbies().stream()
            .map(ExampleHobbiesDTO::value)
            .collect(Collectors.joining(","));
    }

    private String joinCheckbox(ExampleCommand command) {
        return command.getCheckbox().stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));
    }

    private ExampleCreatedResult buildResultFromExample(Example example) {
        return new ExampleCreatedResult(
            example.getCode(),
            example.getName(),
            example.getNik(),
            example.getHobbies(),
            example.getCitizen(),
            example.getPhone(),
            example.getAge(),
            example.getTaxpayerNumber(),
            example.getDob(),
            example.getMarriedStatus(),
            example.getGender(),
            example.getCheckbox(),
            example.getInputDateYear(),
            example.getInputTime(),
            example.getAddress()
        );
    }
}
