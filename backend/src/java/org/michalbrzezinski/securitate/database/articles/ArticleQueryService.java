package org.michalbrzezinski.securitate.database.articles;

import lombok.RequiredArgsConstructor;
import org.michalbrzezinski.securitate.feature.articles.ArticleDatabase;
import org.michalbrzezinski.securitate.feature.articles.objects.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleQueryService implements ArticleDatabase {

    private final ArticleRepository articleRepository;
    private final CustomArticleMapper mapper;

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public Optional<Article> findByUrl(String url) {
        return Optional.empty();
    }
}
