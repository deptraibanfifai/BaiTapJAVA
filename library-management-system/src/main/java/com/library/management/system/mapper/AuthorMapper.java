package com.library.management.system.mapper;

import com.library.management.system.dto.request.AuthorRequest;
import com.library.management.system.dto.response.AuthorResponse;
import com.library.management.system.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorRequest request) {
        if (request == null) return null;

        Author author = Author.builder()
                .name(request.getName())
                .nationality(request.getNationality())
                .biography(request.getBiography())
                .build();

        if (request.getId() != null) {
            author.setId(request.getId());
        }

        return author;
    }

    public AuthorResponse toResponse(Author entity) {
        if (entity == null) return null;

        AuthorResponse response = new AuthorResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setNationality(entity.getNationality());
        response.setBiography(entity.getBiography());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}