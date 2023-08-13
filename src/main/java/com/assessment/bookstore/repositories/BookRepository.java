package com.assessment.bookstore.repositories;
import com.assessment.bookstore.entities.Book;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	
	List<Book> findByIsbn(String isbn);
    
	List<Book> findByTitle(String title);
	
	List<Book> findByAuthorsAuthName(String authName);
	
	List<Book> findByTitleAndAuthorsAuthName(String title,String authName);
   // List<Book> findByTitleOrAuthor(String title,String author);
    
}
