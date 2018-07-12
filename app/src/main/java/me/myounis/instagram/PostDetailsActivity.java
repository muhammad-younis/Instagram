package me.myounis.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.myounis.instagram.model.Post;

public class PostDetailsActivity extends AppCompatActivity {

    ArrayList<String> comments;
    ArrayAdapter<String> commentsAdapter;
    ListView lvComments;

    TextView tvHandle;
    TextView tvDescription;
    TextView tvTimestamp;
    ImageView ivPostPic;
    LinearLayout llComments;
    EditText etAddComment;
    Button btnComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvHandle = (TextView) findViewById(R.id.tvHandle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvTimestamp = (TextView) findViewById(R.id.tvTimestamp);
        ivPostPic = (ImageView) findViewById(R.id.ivPostPic);
        llComments = (LinearLayout) findViewById(R.id.comments);
        etAddComment = (EditText) findViewById(R.id.etAddComment);
        //btnComment = (Button) findViewById(R.id.btnComment);


        // TODO assign all of the items of a post
        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));


        // populate each of the views, with their data
        tvHandle.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());

        Date date = post.getCreatedAt();
        tvTimestamp.setText(getRelativeTimeAgo(date));


        Glide.with(this)
                .load(post.getImage().getUrl())
                .into(ivPostPic);


        /*// obtain a reference to the ListView created with the layout
        lvComments = (ListView) findViewById(R.id.lvComments);
        // initialize the items list
        comments = new ArrayList<>();
        // initialize the adapter using the items list
        commentsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, comments);
        // wire the adapter to the view
        lvComments.setAdapter(commentsAdapter);

        List<String> comments_data = post.getComments();

        if (comments_data != null) {
            for (int i = 0; i < comments_data.size(); i++) {

                comments.add(comments_data.get(i));
            }
        }*/

        TextView temp;

        List<String> comments_data = post.getComments();

        if (comments_data != null) {
            for (int i = 0; i < comments_data.size(); i++) {
                temp = new TextView(this);

                temp.setText(comments_data.get(i)); //arbitrary task

                // add the textview to the linearlayout
                llComments.addView(temp);

            }
        }

        // add some mock items to the list
        // test data
        /*comments.add("First todo item");
        comments.add("Second todo item");
        comments.add("Third todo item");
        comments.add("Fourth todo item");
        comments.add("Fifth todo item");
        comments.add("Sixth todo item");*/
    }

    public String getRelativeTimeAgo(Date date){
        Long datems = date.getTime();
        return DateUtils.getRelativeTimeSpanString(datems, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}
