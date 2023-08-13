package com.assessment.bookstore.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assessment.bookstore.entities.Author;
import com.assessment.bookstore.entities.Book;


@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
	
	List<Author> findByAuthName(String name);
	

}
