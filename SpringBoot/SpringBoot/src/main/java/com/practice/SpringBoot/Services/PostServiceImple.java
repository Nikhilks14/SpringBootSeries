package com.practice.SpringBoot.Services;

import com.practice.SpringBoot.Dto.PostDto;
import com.practice.SpringBoot.entity.PostEntity;
import com.practice.SpringBoot.exception.ResourceNotFoundException;
import com.practice.SpringBoot.reposistory.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public PostDto getPostById(long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found with id " + id));
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
