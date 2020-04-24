package cn.hj.blog.web.admin;

import cn.hj.blog.po.Blog;
import cn.hj.blog.po.Tag;
import cn.hj.blog.po.Type;
import cn.hj.blog.po.User;
import cn.hj.blog.service.BlogService;
import cn.hj.blog.service.TagService;
import cn.hj.blog.service.TypeService;
import cn.hj.blog.vo.BlogQuery;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String BLOG="admin/blogs";
    private Integer pageNumber=0;
    private Integer totalElments=0;
    @Autowired
    BlogService blogService;
    @Autowired
    TagService tagService;
    @Autowired
    TypeService typeService;

    @GetMapping("/blogs")
    public String lists(@PageableDefault(size = 4, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable
            , Model model, @RequestParam(value = "page",required = false)Integer page) {
        List<Type> types = typeService.listType();
        Page<Blog> page_blog = blogService.listBlogs( pageable);
        model.addAttribute("types", types);
        model.addAttribute("page", page_blog);
        return BLOG;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 4, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model) {
        List<Type> types = typeService.listType();
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(blog, pageable));
        return "/admin/blogs :: blogList";
    }

    @DeleteMapping("/blog/{id}")
    public String deleteBlog(@PathVariable("id") Long id) {
        blogService.deleteBlog(id);
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs_input")
    public String addInput(Model model,@RequestParam("id")Integer id,@RequestParam("total")Integer total) {
        pageNumber=id;
        totalElments=total;
        List<Type> types = typeService.listType();
        List<Tag> tags = tagService.findTagsAll();
        model.addAttribute("tags",tags);
        model.addAttribute("types", types);
        model.addAttribute("blog",new Blog());
        return "/admin/blogs-input";
    }
    @GetMapping("/blog/update/{id}")
    public String toupdatePage(@PathVariable("id")Long id,Model model){
        List<Type> types = typeService.listType();
        Blog blog=blogService.getBlog(id);
        blog.init();
        blog.setTagIds(blog.getTagIds());
        model.addAttribute("tags",tagService.findTagsAll());
        model.addAttribute("blogs", blog);
        model.addAttribute("types", types);
        return "/admin/blogs-update";
    }
    @PostMapping("/blogs")
    public String addBlog(@Valid Blog blog, BindingResult result, HttpServletRequest request, RedirectAttributes attributes) {
        if(result.hasErrors()){
            return "admin/blogs-input";
        }
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        List<Tag> tags = tagService.findTagsAllByIds(blog.getTagIds());
        blog.setUser((User)request.getSession().getAttribute("user"));
        //blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tags);
        Blog blog1 = blogService.saveBlog(blog);
        if(blog1==null){
            attributes.addFlashAttribute("msg","操作失败");
        }
        attributes.addFlashAttribute("msg","操作成功");
        return "redirect:/admin/blogs?page="+0;
    }
    @PutMapping("/blog/{id}")
    public String updateBlog(Blog blog,@PathVariable("id")Long id){
        blog.setUpdateTime(new Date());
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.findTagsAllByIds(blog.getTagIds()));
        System.out.println(blog);
        blogService.updateBlog(id,blog);
        return "redirect:/admin/blogs";
    }
}
