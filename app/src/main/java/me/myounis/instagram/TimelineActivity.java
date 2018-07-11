package me.myounis.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import me.myounis.instagram.model.Post;

public class TimelineActivity extends AppCompatActivity {

    Button createButton;

    private PostAdapter postAdapter;
    private ArrayList<Post> posts;
    private RecyclerView rvPosts;


    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // first we need to find the RecyclerView
        rvPosts = (RecyclerView) findViewById(R.id.rvPosts);
        // initialize the lit of tweets (the data source for the recycler view)
        posts = new ArrayList<>();
        // construct the adapter
        postAdapter = new PostAdapter(posts);
        // set the contents of the adapter
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(postAdapter);

        // try to grab data from parse
        grabTopPosts();

        createButton = (Button) findViewById(R.id.btnCreate);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(TimelineActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                posts.clear();
                postAdapter.clear();
                grabTopPosts();

                swipeContainer.setRefreshing(false);

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void grabTopPosts()
    {
        final Post.Query postsQuery = new Post.Query();

        postsQuery.getTop().withUser();
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null)
                {
                    for (int i = 0; i < objects.size(); i ++){
                        Post post = objects.get(i);
                        posts.add(post);
                        postAdapter.notifyItemInserted(posts.size() - 1);
                    }

                }
                else{
                    e.printStackTrace();
                }
            }
        });
    }
}
