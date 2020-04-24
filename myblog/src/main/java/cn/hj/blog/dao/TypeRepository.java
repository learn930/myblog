package cn.hj.blog.dao;

import cn.hj.blog.po.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long>,JpaSpecificationExecutor<Type>{
    Type findByNameIs(String name);
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

}
