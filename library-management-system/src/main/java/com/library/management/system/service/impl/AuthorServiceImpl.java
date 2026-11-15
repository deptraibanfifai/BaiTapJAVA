package com.library.management.system.service.impl;

import com.library.management.system.dto.request.AuthorRequest;
import com.library.management.system.dto.response.AuthorResponse;
import com.library.management.system.entity.Author;
import com.library.management.system.mapper.AuthorMapper;
import com.library.management.system.repository.AuthorRepository;
import com.library.management.system.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponse getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả với ID: " + id));
        return authorMapper.toResponse(author);
    }

    @Override
    public AuthorResponse createAuthor(AuthorRequest request) {
        Author author = authorMapper.toEntity(request);
        author = authorRepository.save(author);
        return authorMapper.toResponse(author);
    }

    @Override
    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả với ID: " + id));

        existingAuthor.setName(request.getName());
        existingAuthor.setNationality(request.getNationality());
        existingAuthor.setBiography(request.getBiography());

        existingAuthor = authorRepository.save(existingAuthor);
        return authorMapper.toResponse(existingAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy tác giả với ID: " + id);
        }
        authorRepository.deleteById(id);
    }
}