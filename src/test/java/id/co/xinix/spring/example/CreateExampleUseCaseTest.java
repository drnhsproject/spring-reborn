package id.co.xinix.spring.example;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;


@ExtendWith(MockitoExtension.class)
public class CreateExampleUseCaseTest {

    @Mock
    private ExampleRepository exampleRepository;

    @InjectMocks
    private CreateExample createExample;

    @Test
    void should_create_example_successfully() {
        ExampleCommand command = new ExampleCommand();
        command.setName("Ani");
        command.setAge(20);

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
        ExampleCommand command = new ExampleCommand();
        command.setName("Valid");
        command.setAge(25);

        when(exampleRepository.save(any())).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> createExample.handle(command));
    }
}
