package cn.hj.blog.service;

import cn.hj.blog.po.Blog;
import cn.hj.blog.po.Tag;
import cn.hj.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    List<Tag> findTagsTop(Integer size);
    List<Tag> findTagsAll();
    List<Tag> findTagsAllByIds(String string);
}
