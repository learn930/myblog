package cn.hj.blog.service;

import cn.hj.blog.po.Blog;
import cn.hj.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TypeService {
    Type saveType(Type type);
    Type getType(Long id);
    Page<Type> listType(Pageable pageable);
    Type updateType(Long id,Type type);
    void deleteType(Long id);
    Type findByNameIs(String name);
    List<Type> listType();
    List<Type> listTypeByStr(String str);
    List<Type> listTop(Integer size);

}
