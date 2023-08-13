package com.assessment.bookstore.repositories;

import java.util.Arrays;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.assessment.bookstore.entities.Author;




@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepository; 
    
    

    @Override
    public void run(String[] args) throws Exception {
   
        Author author1 = new Author("Author 1",new Date(01-01-2000));
        Author author2 = new Author("Author 2",new Date(01-01-2000));
        Author author3 = new Author("Author 3",new Date(01-01-2000));
        
        authorRepository.saveAll(Arrays.asList(author1, author2,author3));
        
      

    }
}