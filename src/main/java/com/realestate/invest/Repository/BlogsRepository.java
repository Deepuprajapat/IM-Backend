package com.realestate.invest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.Blogs;

public interface BlogsRepository extends JpaRepository<Blogs, Long> 
{
    Blogs findByBlogUrl(String url);
}
