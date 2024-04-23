package com.example.myboard.controller;

import com.example.myboard.dto.ArticleDto;
import com.example.myboard.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class articleController {

    private final ArticleService articleService;

    public articleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("insert")
    public String insertView(Model model){
        model.addAttribute("articleDto", new ArticleDto());
//        model.addAttribute("dto", new ArticleDto());
        return "/articles/new";
    }
    @PostMapping("insert")
    public String insertPost(@ModelAttribute("articleDto") ArticleDto articleDto){
//        public String insertPost(@ModelAttribute("dto") ArticleDto articleDto){
        articleService.insertBoard(articleDto);
//        articleService.insertArticle(dto);
        return "redirect:/";
    }

    @GetMapping("update")
    public String updateView(@RequestParam("updateId") Long id,
                             Model model){
        ArticleDto articleDto = articleService.updateId(id);
        model.addAttribute("articleDto", articleDto);
        return "/articles/update";
    }

    @GetMapping("show/{id}")
    public String viewOne(@PathVariable("id") Long id, Model model){
        ArticleDto dto = articleService.findById(id);
        model.addAttribute("dto", dto);
        return "/articles/show";
    }

//    @PostMapping("update")
//    public String update(@ModelAttribute("dto") ArticleDto dto){
////        public String insertPost(@ModelAttribute("dto") ArticleDto articleDto){
//        articleService.insertBoard(dto);
////        articleService.insertArticle(dto);
//        return "redirect:/";
//    }

    @GetMapping("delete/{id}")
    public String update(@PathVariable("id") Long id){
        articleService.deleteById(id);
        return "redirect:/";
    }


    @PostMapping("/update")
    public String updatePost(@ModelAttribute("articleDto") ArticleDto articleDto){
        articleService.updateBoard(articleDto);
        return "redirect:/";
    }
//    @PostMapping("/delete")
//    public String delete(@ModelAttribute("deleteId") Long id ){
//        articleService.deleteBoard(id);
//        return "redirect:/";
//    }
}
