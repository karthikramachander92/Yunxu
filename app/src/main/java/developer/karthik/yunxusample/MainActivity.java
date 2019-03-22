package developer.karthik.yunxusample;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import developer.karthik.yunxu.Yunxu;

public class MainActivity extends AppCompatActivity {

    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);

        Yunxu.withActivity(this)
                .permissions(permissions)
                .setPermissionTitle("Grant Permissions")
                .setPermissionDescription("Grant permissions to acccess your storage for storing logs,offline data etc, and to access your camera to scan QR Code.")
                .executePermission();

    }
}
