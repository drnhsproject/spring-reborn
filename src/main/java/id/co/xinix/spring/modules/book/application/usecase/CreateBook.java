package id.co.xinix.spring.modules.book.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.book.application.dto.*;
import id.co.xinix.spring.modules.book.domain.*;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class CreateBook {

    private final BookRepository bookRepository;

    public BookCreatedResult handle(BookCommand command) {
        Book book = new Book();
        book.setId(command.getId());
        book.setName(command.getName());
        book.setPrice(command.getPrice());
        book.setInStock(command.getInStock());

        Book savedBook = bookRepository.save(book);

        return new BookCreatedResult(
            savedBook.getId(),
            savedBook.getName(),
            savedBook.getPrice(),
            savedBook.getInStock()
        );
    }
}
