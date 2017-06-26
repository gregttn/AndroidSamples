package com.gregttn.recyclerviewsample.model;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
    private final List<Article> articles;

    public ArticleRepository() {
        articles = new ArrayList<>();
        articles.add(new Article("Article 1", "Maecenas pharetra ultricies velit, ut porttitor nisi interdum eu. Nullam dignissim quis ligula in aliquet. Integer eleifend leo nec imperdiet egestas"));
        articles.add(new Article("Article 2", "Praesent quis dolor id quam vehicula faucibus ut sit amet est. Nam nec magna sed massa eleifend iaculis a non lorem"));
        articles.add(new Article("Article 3", "Maecenas in malesuada mi. Suspendisse potenti. Curabitur imperdiet neque at felis vestibulum, sit amet aliquet mi sollicitudin"));
    }

    public List<Article> getArticles() {
        return new ArrayList<>(articles);
    }

    public Article getArticleAt(int position) {
        return articles.get(position);
    }
}