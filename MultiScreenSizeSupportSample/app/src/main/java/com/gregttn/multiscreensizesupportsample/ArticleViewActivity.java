package com.gregttn.multiscreensizesupportsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.gregttn.multiscreensizesupportsample.model.Article;
import com.gregttn.multiscreensizesupportsample.model.ArticleRepository;

public class ArticleViewActivity extends AppCompatActivity {
    private ArticleRepository articleRepository = new ArticleRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        Intent intent = getIntent();
        int articleId = intent.getIntExtra("articleId", 0);

        ArticleViewFragment articleViewFragment = (ArticleViewFragment) getSupportFragmentManager().findFragmentById(R.id.article_view_fragment);
        articleViewFragment.displayArticle(articleRepository.getArticleAt(articleId));
    }
}
