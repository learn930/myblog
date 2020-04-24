package cn.hj.blog.web.admin;

import cn.hj.blog.po.Type;
import cn.hj.blog.service.TypeService;
import groovyjarjarpicocli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    TypeService typeService;
    private Integer pageNumber=0;
    private Integer totalElments=0;
    @GetMapping("/types")
    public String list(@PageableDefault(size = 6, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable
            , Model model) {
        System.out.println(pageable.getPageNumber());
        model.addAttribute("page", typeService.listType(pageable));
        return "/admin/types";
    }

    @GetMapping("/types_input")
    public String addInput(Model model,@RequestParam("id")Integer id,@RequestParam("total")Integer total) {
        pageNumber=id;
        totalElments=total;
        model.addAttribute("type", new Type());
        return "/admin/types-input";
    }

    @PostMapping("/type")
    public String addType(@Valid Type type, BindingResult result, Model model, RedirectAttributes attributes) {
        if (typeService.findByNameIs(type.getName()) != null) {
            result.rejectValue("name","nameError","不能新增已存在的type");
            return "/admin/types-input";
        }
        if (result.hasErrors()) {
            return "/admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        if(totalElments%6==0&&totalElments!=0){
            return "redirect:/admin/types?page="+pageNumber;
        }
        return "redirect:/admin/types?page="+(pageNumber-1);
    }

    @DeleteMapping("/type/{id}")
    public String deleteType(@PathVariable("id") Integer id) {
        typeService.deleteType(id.longValue());
        return "redirect:/admin/types";
    }

    @GetMapping("/type/update/{id}")
    public String updatePage(@PathVariable("id") Integer id,Model model) {
        Type type = typeService.getType(id.longValue());
        model.addAttribute("type",type);
        return "/admin/types-update";
    }

    @PutMapping("/type/{id}")
    public String updateType(@Valid Type type,BindingResult result,RedirectAttributes attributes){
        if(result.hasErrors()){
            return "/admin/types-input";
        }
        if (typeService.findByNameIs(type.getName()) != null) {
            result.rejectValue("name","nameError","不能新增已存在的type");
            return "/admin/types-input";
        }
        Type type1 = typeService.updateType(type.getId(), type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }


}
