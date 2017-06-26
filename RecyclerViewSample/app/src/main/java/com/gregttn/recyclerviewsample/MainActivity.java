package com.gregttn.recyclerviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gregttn.recyclerviewsample.model.Article;
import com.gregttn.recyclerviewsample.model.ArticleRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final ArticleRepository articleRepository = new ArticleRepository();

    private RecyclerView articleRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articleRecyclerView = (RecyclerView) findViewById(R.id.article_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        articleRecyclerView.setLayoutManager(linearLayoutManager);

        ArticlesAdapter adapter = new ArticlesAdapter(articleRepository.getArticles());
        articleRecyclerView.setAdapter(adapter);
    }

    public static class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

        private List<Article> articles;

        public ArticlesAdapter(List<Article> articles) {
            this.articles = articles;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView titleView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.title_view, parent, false);

            return new ViewHolder(titleView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String title = articles.get(position).getTitle();
            holder.articleTitle.setText(title);
        }

        @Override
        public int getItemCount() {
            return articles.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            private TextView articleTitle;

            public ViewHolder(TextView articleTitle) {
                super(articleTitle);
                this.articleTitle = articleTitle;
            }
        }
    }
}
