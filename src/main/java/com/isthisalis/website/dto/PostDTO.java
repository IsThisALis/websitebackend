package com.isthisalis.website.dto;

import java.time.Instant;
import java.util.Set;

import com.isthisalis.website.entity.Post;

import lombok.Value;

/**
 * Post
 */
@Value
public class PostDTO {
  long id;
  String title;
  String content;
  String image;
  Instant date;
  Set<TagDTO> tag;

  public static PostDTO wrap(Post post) {
        return new PostDTO(post.getId(), post.getTitle(), 
            post.getContent(), post.getImage(), post.getCreatedAt(),
            Set.copyOf(post.getTags().stream().map(TagDTO::wrap).toList())
        );
    }
}
