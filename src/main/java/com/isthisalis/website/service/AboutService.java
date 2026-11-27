package com.isthisalis.website.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.isthisalis.website.dto.AboutDTO;
import com.isthisalis.website.entity.About;
import com.isthisalis.website.repository.AboutRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AboutService {
    
    private final AboutRepository repository;


    public AboutDTO getAbout() {
        try {
            return AboutDTO.wrap(repository.findAll().getLast());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void createAbout(AboutDTO aboutDTO) {
        try {
            repository.save(About.wrap(aboutDTO));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void editAbout(AboutDTO aboutDTO) {
        About about = repository.findById(1L)
            .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404))
        );

        about.setTitle(aboutDTO.getTitle());
        about.setContent(aboutDTO.getContent());
        about.setTechstack(aboutDTO.getTechStack());
    }

    public void deleteAbout() {
        repository.deleteById(1L);
    }
}
