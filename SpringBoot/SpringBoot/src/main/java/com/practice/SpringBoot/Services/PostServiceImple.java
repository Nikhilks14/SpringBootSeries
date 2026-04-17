package com.practice.SpringBoot.Services;

import com.practice.SpringBoot.Dto.PostDto;
import com.practice.SpringBoot.entity.PostEntity;
import com.practice.SpringBoot.entity.User;
import com.practice.SpringBoot.exception.ResourceNotFoundException;
import com.practice.SpringBoot.reposistory.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImple implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map( postEntity -> modelMapper.map(postEntity, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createNewPost(PostDto postDto) {
        PostEntity postEntity = modelMapper.map(postDto, PostEntity.class);

        return modelMapper.map(postRepository.save(postEntity), PostDto.class);
    }

    @Override
    public PostDto getPostById(long postid) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        log.info("user: {}", user);

        PostEntity postEntity = postRepository.findById(postid)
                .orElseThrow(() -> {
                    System.out.println("Exception triggered");
                    return new ResourceNotFoundException("resource not found with {} " + postid);
                });
        return modelMapper.map( postEntity, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto inputPost, long id) {
        PostEntity oldpost = postRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post is not available with id :" + id));
        inputPost.setId(id);
        modelMapper.map(inputPost, oldpost);
        PostEntity savePost = postRepository.save(oldpost);
        return modelMapper.map(savePost, PostDto.class);

    }
}
