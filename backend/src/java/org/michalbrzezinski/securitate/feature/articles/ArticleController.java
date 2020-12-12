package org.michalbrzezinski.securitate.feature.articles;

import lombok.RequiredArgsConstructor;
import org.michalbrzezinski.securitate.feature.articles.objects.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleManagementService articleManagementService;

    @PostMapping
    public void createArticle(@RequestBody Article article) {
        articleManagementService.createArticle(article);
    }

    @PostMapping("{url}")
    public void editArticle(@PathVariable String url, @RequestBody Article article) {
        articleManagementService.editArticle(url, article);
    }

    @GetMapping("{url}")
    public Article getArticle(@PathVariable String url) {
        return articleManagementService.findByUrl(url);
    }

    @GetMapping
    public Page<Article> getAllArticles(@RequestParam Pageable pageable) {
        return articleManagementService.findAll(pageable);
    }

}
