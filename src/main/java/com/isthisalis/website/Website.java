package com.isthisalis.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Website
 */ 
@SpringBootApplication @EnableCaching @EntityScan
public class Website {

  public static void main(String[] args) {
    SpringApplication.run(Website.class, args);
  }
}
