package com.isthisalis.website.entity;

import java.util.HashSet;
import java.util.Set;

import com.isthisalis.website.dto.TagDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {
    
    private @Id @GeneratedValue
        Long id;

    private @Getter @Setter @Column(nullable = false, unique = true) String name;

    @OneToMany(mappedBy = "tag") @Builder.Default @Getter
    private Set<Post> posts = new HashSet<>();

    public static Tag wrap(TagDTO tagDTO) {
        return Tag.builder()
            .name(tagDTO.getName())
            .build();
    }
}
