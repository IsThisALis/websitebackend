package com.isthisalis.website.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isthisalis.website.entity.Post;
import com.isthisalis.website.entity.Tag;

/**
 * PostRepository
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findAllByTags(Set<Tag> tag);
}
