package com.realestate.invest.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.realestate.invest.DTOModel.BlogsDTO;
import com.realestate.invest.Model.Blogs;

@Component
public interface BlogsService 
{
    Blogs saveNewBlogs(BlogsDTO blogsDTO) throws Exception;

    Blogs getById(Long Id) throws Exception;

    Blogs updateById(Long Id, BlogsDTO blogsDTO) throws Exception;

    String deleteBlogsById(Long Id) throws Exception;

    Blogs setPriorityById(Long Id, Boolean isPriority) throws Exception;

    Blogs getByUrl(String url) throws Exception;

    Page<Blogs> getAll(Boolean isPriority, Long startDate, Long endDate, Pageable pageable) throws Exception;

}
