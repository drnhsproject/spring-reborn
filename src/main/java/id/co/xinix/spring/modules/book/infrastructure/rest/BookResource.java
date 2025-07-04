package id.co.xinix.spring.modules.book.infrastructure.rest;

import id.co.xinix.spring.modules.book.application.dto.*;
import id.co.xinix.spring.modules.book.application.usecase.*;
import id.co.xinix.spring.services.ListResponse;
import id.co.xinix.spring.services.SingleResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book API", description = "Operation book")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class BookResource {

    private final CreateBook create;

    @Operation(summary = "Create Book", description = "create new book")
    @PostMapping("")
    public ResponseEntity<SingleResponse<BookCreatedResult>> createBook(
        @Valid @RequestBody BookCommand command
    ) throws URISyntaxException
    {
        BookCreatedResult result = create.handle(command);
        SingleResponse<BookCreatedResult> response = new SingleResponse<>("book created", result);

        return ResponseEntity.created(new URI("/api/v1/books/")).body(response);
    }
}
