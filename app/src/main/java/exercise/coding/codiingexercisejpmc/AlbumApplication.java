package exercise.coding.codiingexercisejpmc;

import android.app.Application;

import io.realm.Realm;

public class AlbumApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
