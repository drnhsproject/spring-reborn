package id.co.xinix.spring.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.xinix.spring.modules.example.application.dto.ExampleCommand;
import id.co.xinix.spring.modules.example.application.dto.ExampleCreatedResult;
import id.co.xinix.spring.modules.example.application.usecase.CreateExample;
import id.co.xinix.spring.modules.example.domain.Example;
import id.co.xinix.spring.modules.example.domain.ExampleRepository;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import id.co.xinix.spring.services.GenerateRandomCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
public class CreateExampleUseCaseTest {

    @Mock
    private ExampleRepository exampleRepository;

    private CreateExample createExample;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ObjectMapper objectMapper = new ObjectMapper();
        createExample = new CreateExample(exampleRepository, objectMapper);
    }

    @Test
    void should_create_example_successfully() {
        ExampleCommand command = getExampleCommand();

        String generatedCode = new GenerateRandomCode().generate("EXP_");
        Example example = new Example();
        example.setId(1L);
        example.setCode(generatedCode);
        example.setAge(command.getAge());
        example.setName(command.getName());

        when(exampleRepository.save(any(Example.class))).thenReturn(example);

        ExampleCreatedResult result = createExample.handle(command);

        assertNotNull(result);
        assertEquals(generatedCode, result.code());
        assertEquals(20, result.age());
        assertEquals("Ani", result.name());
        verify(exampleRepository).save(any(Example.class));
    }

    private ExampleCommand getExampleCommand() {
        ExampleCommand command = new ExampleCommand();
        command.setName("Ani");
        command.setNik("1234567890");
        command.setHobbies(List.of("Reading"));
        command.setCitizen(1);
        command.setPhone("08123456789");
        command.setAge(20);
        command.setTaxpayer_number("NPWP12345");
        command.setDob(LocalDate.of(2000, 1, 1));
        command.setMarried_status(true);
        command.setGender("FEMALE");
        command.setCheckbox(List.of(1, 2));
        command.setInput_date_year("2025");
        command.setInput_time(LocalTime.of(10, 0));
        command.setAddress("Jl. Contoh No. 1");

        command.setProfile_picture(null);
        command.setMultiple_image(null);
        command.setSupporting_document(null);
        command.setMultiple_file(null);

        return command;
    }

    @Test
    void should_throw_exception_when_name_is_null() {
        ExampleCommand command = new ExampleCommand();
        command.setName(null);
        command.setAge(20);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ExampleCommand>> violations = validator.validate(command);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("cannot be null")));
    }

    @Test
    void should_throw_exception_when_name_is_empty() {
        ExampleCommand command = new ExampleCommand();
        command.setName("");
        command.setAge(20);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ExampleCommand>> violations = validator.validate(command);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank")));
    }

    @Test
    void should_throw_exception_when_age_is_negative() {
        ExampleCommand command = new ExampleCommand();
        command.setName("Jane Doe");
        command.setAge(-1);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ExampleCommand>> violations = validator.validate(command);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("age")));
    }

    @Test
    void should_throw_exception_when_repository_returns_null() {
        ExampleCommand command = getExampleCommand();

        when(exampleRepository.save(any())).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> createExample.handle(command));
    }
}
