package cn.hj.blog.web;

import cn.hj.blog.po.Blog;
import cn.hj.blog.po.Type;
import cn.hj.blog.service.BlogService;
import cn.hj.blog.service.TagService;
import cn.hj.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping(value = {"/index","/","/index.html"})
    public String index(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",blogService.listBlogs(pageable));
        List<Type> types = typeService.listTop(4);
        model.addAttribute("types",types);
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(4));
        model.addAttribute("tags",tagService.findTagsTop(4));
        return "index";
      }
    @GetMapping("/search")
    public String search(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable
            , Model model,@RequestParam("query") String query){
        model.addAttribute("page",blogService.listBlogByQuery("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }
    @GetMapping("/blog/{id}")
    public String toBlogPage(@PathVariable("id")Long id,Model model){
        Blog blog = blogService.getAndConvert(id);
        System.out.println(blog);
        model.addAttribute("blog",blog);
        return "blog";
    }
}
