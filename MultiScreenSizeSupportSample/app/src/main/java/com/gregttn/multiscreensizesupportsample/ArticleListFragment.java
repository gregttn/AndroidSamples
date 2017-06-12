package com.gregttn.multiscreensizesupportsample;


import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.gregttn.multiscreensizesupportsample.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleListFragment extends ListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
    }

    public void updateArticles(List<Article> articles) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        List<String> titles = new ArrayList<>();

        for(Article article : articles) {
            titles.add(article.getTitle());
        }

        adapter.clear();
        adapter.addAll(titles);
        adapter.notifyDataSetChanged();
    }
}
