package com.gregttn.multiscreensizesupportsample;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gregttn.multiscreensizesupportsample.model.ArticleRepository;

public class MainActivity extends AppCompatActivity implements ArticleListFragment.OnArticleSelectedListener {

    private ArticleRepository articleRepository;
    private ArticleViewFragment articleViewFragment;

    private boolean hasTwoPanes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArticleListFragment articleListFragment = (ArticleListFragment) getSupportFragmentManager().findFragmentById(R.id.article_list_fragment);
        articleRepository = new ArticleRepository();
        articleListFragment.updateArticles(articleRepository.getArticles());

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.article_view_fragment);
        if(fragment != null) {
            hasTwoPanes = true;
            articleViewFragment = (ArticleViewFragment) fragment;
        }
    }

    @Override
    public void articleSelected(int articleId) {
        showArticleView(articleId);
    }

    private void showArticleView(int articleId) {
        if(hasTwoPanes) {
            articleViewFragment.displayArticle(articleRepository.getArticleAt(articleId));
        } else {
            Intent intent = new Intent(this, ArticleViewActivity.class);
            intent.putExtra("articleId", articleId);

            startActivity(intent);
        }
    }
}
