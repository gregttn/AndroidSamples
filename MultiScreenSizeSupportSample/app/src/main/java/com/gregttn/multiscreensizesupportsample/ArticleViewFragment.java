package com.gregttn.multiscreensizesupportsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gregttn.multiscreensizesupportsample.model.Article;


public class ArticleViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_view, container, false);
    }

    public void displayArticle(Article article) {
        TextView titleView = (TextView) getView().findViewById(R.id.article_title_text_view);
        titleView.setText(article.getTitle());

        TextView contentView = (TextView) getView().findViewById(R.id.article_content_text_view);
        contentView.setText(article.getContent());
    }
}
