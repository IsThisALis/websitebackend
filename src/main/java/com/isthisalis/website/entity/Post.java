package com.isthisalis.website.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isthisalis.website.dto.PostDTO;

import jakarta.persistence.*;
import lombok.*;

/**
 * Post
 */  
@Entity
@Table(name = "posts") 
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor  
public class Post { 

  private @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    Long id;

  private @Setter @Column(name = "title", nullable = false)
    String title;

  private @Setter @Column(name = "content", nullable = false)
    String content;

  private @Setter @Column(name = "image")
    String image;

  private @Setter @Column(name = "createdat", nullable = false) 
    Instant createdAt;

  private @Builder.Default @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) @JoinColumn(name = "post_comments") 
    Set<Comment> comments = new HashSet<>();
    
  private @Getter @Setter @Builder.Default @JsonIgnore @ManyToMany @JoinTable(
        name = "post_tags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
      )
    Set<Tag> tags = new HashSet<>();

  public static Post wrap(PostDTO postDTO) {
    return Post.builder()
      .title(postDTO.getTitle())
      .content(postDTO.getContent())
      .createdAt(postDTO.getDate())
      .image(postDTO.getImage())
      .build();
  }
}