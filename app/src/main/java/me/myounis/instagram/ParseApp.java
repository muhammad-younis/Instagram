package me.myounis.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import me.myounis.instagram.model.Post;

public class ParseApp extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("parstagram-id-passcode")
                .clientKey("parstagram-master-passcode")
                .server("http://myounis-instagram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
