package com.assessment.bookstore.controllers;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.validation.Valid;

import com.assessment.bookstore.entities.*;
import com.assessment.bookstore.repositories.AuthorRepository;
import com.assessment.bookstore.repositories.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
	
	@Autowired
    private BookRepository bookRepository;
	@Autowired
    private AuthorRepository authorRepository;

     
    @GetMapping("/books")
    public ResponseEntity<List<Book>>  showBooks() {
    	List<Book> books = new ArrayList<Book>();
    	bookRepository.findAll().forEach(books::add);
    	
    	return new ResponseEntity<>(books, HttpStatus.OK);
    }
    
    @GetMapping("/authors")
    public ResponseEntity<List<Author>> showAuthors() {
    	List<Author> authors = new ArrayList<Author>();

    	authorRepository.findAll().forEach(authors::add);
    	return new ResponseEntity<>(authors, HttpStatus.OK);
    }
    
    
    @PostMapping("/newbook")
    public ResponseEntity<?> addNewBook(@RequestBody Book book) {
    	
        List<Author> authorList = book.getAuthors();
        Map<String, Object> response = new HashMap<>();
        for(int i=0;i<authorList.size();i++) {
        	Author author = authorList.get(i);
        	String authorName = author.getAuthName();
        	List<Author> authors = authorRepository.findByAuthName(authorName);
			if(authors.isEmpty())  {
				 authorRepository.save(author);
			}
        }
        try {
	    	bookRepository.save(book);
	    	response.put("status", 200);
	        response.put("message", "New Book successfully added");
	    	return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception exception) {
        	response.put("status", 400);
        	response.put("message", exception.getMessage());
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        	
        }
    }
    
    @PostMapping("/updatebook")
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
    	
        List<Author> authorList = book.getAuthors();
        Map<String, Object> response = new HashMap<>();
        for(int i=0;i<authorList.size();i++) {
        	Author author = authorList.get(i);
        	String authorName = author.getAuthName();
        	List<Author> authors = authorRepository.findByAuthName(authorName);
			if(authors.isEmpty())  {
				 authorRepository.save(author);
			}
        }
        List<Book> books = bookRepository.findByIsbn(book.getIsbn());
        if(books.isEmpty()) {
        	response.put("status", 404);
            response.put("message", "Book cannot be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
        	 try {
		        bookRepository.save(book);
		    	response.put("status", 200);
		        response.put("message", "Book successfully updated");
		    	return ResponseEntity.status(HttpStatus.OK).body(response);
        	 } catch (Exception exception) {
             	response.put("status", 400);
             	response.put("message", exception.getMessage());
             	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
             	
             }
        }
        
    }
    @PostMapping("/deletebook")
    public ResponseEntity<?> deleteBook(@RequestBody Book bookToDel) {
    	
    	  Map<String, Object> response = new HashMap<>();
        List<Book> books = bookRepository.findByIsbn(bookToDel.getIsbn());
        if(books.isEmpty()) {
        	response.put("status", 404);
         	response.put("message", "Book cannot be found.");
         	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
        	Book book = books.get(0);
        
	    	 try {
		        bookRepository.delete(book);
		    	response.put("status", 200);
		        response.put("message", "Book successfully deleted");
		    	return ResponseEntity.status(HttpStatus.OK).body(response);
	    	 } catch (Exception exception) {
	         	response.put("status", 400);
	         	response.put("message", exception.getMessage());
	         	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	         	
	         }
        }
    }
    @PostMapping("/findbook")
    public ResponseEntity<List<Book>> findBook(@RequestBody Book bookToFind) {

    	List<Book> books = new ArrayList<Book>();
    	if(bookToFind.getTitle() != null && !bookToFind.getTitle().isEmpty() && !bookToFind.getAuthors().isEmpty()) {
    		for(int i=0;i<bookToFind.getAuthors().size();i++) {
    			Author author = bookToFind.getAuthors().get(i);
    			books = bookRepository.findByTitleAndAuthorsAuthName(bookToFind.getTitle(),author.getAuthName());
    		}
    	} else if(bookToFind.getTitle() != null && !bookToFind.getTitle().isEmpty()) {
    		books = bookRepository.findByTitle(bookToFind.getTitle());
    	} else if(!bookToFind.getAuthors().isEmpty()) {
    		for(int i=0;i<bookToFind.getAuthors().size();i++) {
    			Author author = bookToFind.getAuthors().get(i);
    			books = bookRepository.findByAuthorsAuthName(author.getAuthName());
    		}
    	} else {
    		
         	return new ResponseEntity<>(books,HttpStatus.BAD_REQUEST);
    	}
    	if(!books.isEmpty()) {
    		return new ResponseEntity<>(books, HttpStatus.OK);
    	}
		return null;
        
       
        
    }
   

}
