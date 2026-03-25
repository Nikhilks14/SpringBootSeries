package com.practice.SpringBoot.controller;

import com.practice.SpringBoot.Dto.PostDto;
import com.practice.SpringBoot.Services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping()
    public PostDto createPost(@RequestBody PostDto inputDto) {
        return postService.createNewPost(inputDto);
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable long id) {
        return postService.getPostById(id);
    }


    @PutMapping("/{id}")
    public PostDto updatePost(@RequestBody PostDto inputDto, @PathVariable long id) {
        return postService.updatePost(inputDto, id);
    }
}
