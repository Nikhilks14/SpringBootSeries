package com.product.warehouse.controller;

import com.product.warehouse.entity.ProductEntity;
import com.product.warehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    private final int PAGE_SIZE = 5;

    @GetMapping
    public Page<ProductEntity> getAllProducts(
            @RequestParam(defaultValue = "") String title
            ,@RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") Integer pageNumber) {






//        return productRepository.findAll();
//        return productRepository.findBy(Sort.by(Sort.Direction.DESC, sortBy ,"price"));

//     return productRepository.findBy(Sort.by(
//             Sort.Order.desc(sortBy),
//             Sort.Order.asc("price")
//     ));


//        Pageable pageable = PageRequest.of(pageNumber, Size, Sort.by("lastName").ascending());

//        Pageable pageable = PageRequest.of(
//                pageNumber,
//                PAGE_SIZE,
//                Sort.by(sortBy));
//        return productRepository.findAll(pageable);


        return productRepository.findByTitleContainingIgnoreCase(
                title,
                PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(sortBy))
        );

    }

}
