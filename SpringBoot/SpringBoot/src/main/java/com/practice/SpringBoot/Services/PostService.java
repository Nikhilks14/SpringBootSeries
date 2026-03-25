package com.practice.SpringBoot.Services;

import com.practice.SpringBoot.Dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();
    PostDto createNewPost(PostDto postDto);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto inputDto, long id);
}
