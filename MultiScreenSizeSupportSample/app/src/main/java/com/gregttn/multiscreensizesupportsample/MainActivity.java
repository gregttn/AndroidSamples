package com.gregttn.multiscreensizesupportsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gregttn.multiscreensizesupportsample.model.ArticleRepository;

public class MainActivity extends AppCompatActivity {

    private ArticleRepository articleRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArticleListFragment articleListFragment = (ArticleListFragment) getFragmentManager().findFragmentById(R.id.article_list_fragment);
        articleRepository = new ArticleRepository();
        articleListFragment.updateArticles(articleRepository.getArticles());
    }
}
