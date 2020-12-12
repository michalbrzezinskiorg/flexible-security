package org.michalbrzezinski.securitate.database.articles;

import lombok.RequiredArgsConstructor;
import org.michalbrzezinski.securitate.feature.articles.objects.Article;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class ArticleCommandService {
    private final ArticleRepository articleRepository;
    private final CustomArticleMapper mapper;

    public void save(Article article) {
        articleRepository.save(mapper.map(article));
    }
}
