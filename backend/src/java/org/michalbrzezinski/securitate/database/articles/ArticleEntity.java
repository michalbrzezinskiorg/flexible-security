package org.michalbrzezinski.securitate.database.articles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "article")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ArticleEntity {

    @Id
    private String url;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private AuthorEntity author;
    private String subject;
    private String body;
}
