package cn.hj.blog.service;

import cn.hj.blog.NotFoundException;
import cn.hj.blog.dao.TypeRepository;
import cn.hj.blog.po.Type;
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

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type one = typeRepository.getOne(id);
        if (one == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, one);
        return typeRepository.save(one);

    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public Type findByNameIs(String name) {
        return typeRepository.findByNameIs(name);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeByStr(String str) {
        return typeRepository.findAllById(covert(str));
    }

    @Override
    public List<Type> listTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    private Iterable<Long> covert(String str) {
        List<Long> list=new ArrayList<>();
        if(str!=null&&!"".equals(str)){
        String[] s=str.split(",");
        for(String temp:s){
            list.add(new Long(temp));
        }
        }
        return list;
    }



}
