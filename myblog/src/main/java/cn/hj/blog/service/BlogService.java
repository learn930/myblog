package cn.hj.blog.service;

import cn.hj.blog.po.Blog;
import cn.hj.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(BlogQuery blog, Pageable pageable);

    Page<Blog> listBlogs(Pageable pageable);

    Page<Blog> listBlogByQuery(String query, Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    List<Blog> listRecommendBlogTop(Integer size);

    void deleteBlog(Long id);
}
