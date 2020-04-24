package cn.hj.blog.service;


import cn.hj.blog.NotFoundException;
import cn.hj.blog.dao.BlogRepository;
import cn.hj.blog.po.Blog;
import cn.hj.blog.po.Type;
import cn.hj.blog.util.MarkdownUtils;
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
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        Optional<Blog> byId = blogRepository.findById(id);
        if (!byId.isPresent()) {
            return null;
        }
        return byId.get();
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = getBlog(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(BlogQuery blog, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (blog.getTitle() != null && !"".equals(blog.getTitle())) {
                    Predicate title = criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%");
                    predicates.add(title);
                }
                if (blog.getTypeId() != null) {
                    Predicate equal = criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId());
                    predicates.add(equal);
                }
                if (blog.getRecommend()) {
                    Predicate recommend = criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.getRecommend());
                    predicates.add(recommend);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = getBlog(id);
        if (blog1 == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, blog1, MyBeanUtils.getNullPropertyName(blog));//只复制blog有值的到blog1
        return blogRepository.save(blog1);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }


    @Override
    public Page<Blog> listBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlogByQuery(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }
}
