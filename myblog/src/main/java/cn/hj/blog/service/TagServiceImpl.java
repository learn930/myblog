package cn.hj.blog.service;


import cn.hj.blog.NotFoundException;
import cn.hj.blog.dao.BlogRepository;
import cn.hj.blog.dao.TagRepository;
import cn.hj.blog.po.Blog;
import cn.hj.blog.po.Tag;
import cn.hj.blog.po.Type;
import cn.hj.blog.util.MyBeanUtils;
import cn.hj.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;
    @Override
    public List<Tag> findTagsTop(Integer size) {
        Pageable pageable=PageRequest.of(0,size);
        return tagRepository.finTop(pageable);
    }

    @Override
    public List<Tag> findTagsAll() {
        return tagRepository.findAll();
    }
    private Iterable<Long> convert(String str) {
        List<Long> list=new ArrayList<>();
        if(str!=null&&!"".equals(str)){
            String[] s=str.split(",");
            for(String temp:s){
                list.add(new Long(temp));
            }
        }
        return list;
    }
    @Override
    public List<Tag> findTagsAllByIds(String string) {
        return tagRepository.findAllById(convert(string));
    }
}
