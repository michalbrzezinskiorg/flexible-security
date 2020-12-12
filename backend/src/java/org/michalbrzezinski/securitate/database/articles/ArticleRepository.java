package org.michalbrzezinski.securitate.database.articles;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface ArticleRepository extends JpaRepository<ArticleEntity, String> {
    Page<ArticleEntity> findAll(Pageable pageable);

}
