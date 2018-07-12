package me.myounis.instagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

import me.myounis.instagram.model.Post;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private List<Post> mPosts;

    Context context;


    // array of posts
    public PostAdapter(List<Post> posts)
    {
        mPosts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Post post = mPosts.get(position);

        // populate each of the views, with their data
        holder.tvHandle.setText(post.getUser().getUsername());
        holder.tvDescription.setText(post.getDescription());

        Date date = post.getCreatedAt();
        holder.tvTimestamp.setText(getRelativeTimeAgo(date));


        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.ivPostPic);

    }



    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }





    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView ivProfilePic;
        public TextView tvHandle;
        public TextView tvDescription;
        public ImageView ivPostPic;
        public TextView tvTimestamp;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ivProfilePic = (ImageView) itemView.findViewById(R.id.ivProfilePic);
            tvHandle = (TextView) itemView.findViewById(R.id.tvHandle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            ivPostPic = (ImageView) itemView.findViewById(R.id.ivPostPic);

            tvTimestamp = (TextView)itemView.findViewById(R.id.tvTimestamp);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Post post = mPosts.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, PostDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra("post", Parcels.wrap(post));
                // show the activity
                context.startActivity(intent);
            }
        }

    }

    public String getRelativeTimeAgo(Date date){
        Long datems = date.getTime();
        return DateUtils.getRelativeTimeSpanString(datems, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    }

    @Override
    public int getItemCount()
    {
        return mPosts.size();
    }

}
