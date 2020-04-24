package cn.hj.blog.dao;

import cn.hj.blog.po.Tag;
import cn.hj.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long>,JpaSpecificationExecutor<Type>{
    @Query("select t from Tag t ")
    List<Tag> finTop(Pageable pageable);
}
