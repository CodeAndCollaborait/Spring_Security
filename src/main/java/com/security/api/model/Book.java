package com.security.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "books")

public class Book {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  @Column( nullable = false)
  private String name;
  
  @Column(nullable = false)
  private String author;
  
  @Column(nullable = false)
  private double price;
  
  public Book() {
  }
  
  public Book(long id, String name, String author, double price) {
    this.id = id;
    this.name = name;
    this.author = author;
    this.price = price;
  }
  
  public Book(String name, String author, double price) {
    this.name = name;
    this.author = author;
    this.price = price;
  }
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getAuthor() {
    return author;
  }
  
  public void setAuthor(String author) {
    this.author = author;
  }
  
  public double getPrice() {
    return price;
  }
  
  public void setPrice(double price) {
    this.price = price;
  }
}
