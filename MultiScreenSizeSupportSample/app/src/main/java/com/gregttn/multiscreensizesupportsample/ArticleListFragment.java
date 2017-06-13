package com.gregttn.multiscreensizesupportsample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.gregttn.multiscreensizesupportsample.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleListFragment extends ListFragment {
    private OnArticleSelectedListener listener;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.articleSelected(position);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnArticleSelectedListener) {
            listener = (OnArticleSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    interface OnArticleSelectedListener {
        void articleSelected(int articleId);
    }
}
