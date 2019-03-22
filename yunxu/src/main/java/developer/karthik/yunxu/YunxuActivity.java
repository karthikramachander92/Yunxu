package developer.karthik.yunxu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class YunxuActivity extends Activity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    private String TAG = getClass().getSimpleName();

    Dialog permissionDialog;
    List<String> permissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Intent i = getIntent();

        permissions = (ArrayList<String>) i.getSerializableExtra("permissions");
        final ArrayList<String> permissionsNotGranted = new ArrayList<>();

        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNotGranted.add(permission);
            }
        }


        if (permissionsNotGranted.size() > 0) {

            String dialogTitle = i.getStringExtra("title");
            String dialogDesc = i.getStringExtra("desc");

            permissionDialog = new Dialog(this);
            permissionDialog.setTitle(dialogTitle);
            permissionDialog.setContentView(R.layout.permission_dialog);

            ((TextView)permissionDialog.findViewById(R.id.title)).setText(dialogTitle);
            ((TextView)permissionDialog.findViewById(R.id.desc)).setText(dialogDesc);

            ((TextView)permissionDialog.findViewById(R.id.grant_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGrantButtonClicked(permissionsNotGranted);
                }
            });

            ((TextView)permissionDialog.findViewById(R.id.deny_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDenyButtonClicked();
                }
            });

            permissionDialog.show();

        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        List<String> permissionsToShowDetail = new ArrayList<>();
        List<String> permissionsToGotoSetting = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {
            //Tutkia.Log(TAG, permissions[i] + " --> " + grantResults[i]);

            if (grantResults[i] == -1 && ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                //Tutkia.Log(TAG,"Show Details about permission");
                permissionsToShowDetail.add(permissions[i]);
            } else if (grantResults[i] == -1 && !ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                // Tutkia.Log(TAG,"Go to setting page");
                permissionsToGotoSetting.add(permissions[i]);
            } else {
                Log.e(TAG, "Permissions all granted");
            }

        }

        if (permissionsToGotoSetting.size() > 0) {
            Log.e(TAG, "go to settings");
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
            intent.setData(uri);
            this.startActivity(intent);
        }

    }

    public void onDenyButtonClicked() {

        if(permissionDialog != null && permissionDialog.isShowing()){
            permissionDialog.dismiss();
        }

    }

    public void onGrantButtonClicked(List<String> permissionsNotGranted) {

        if(permissionDialog != null && permissionDialog.isShowing()){
            permissionDialog.dismiss();
        }

        String[] a = new String[permissionsNotGranted.size()];
        permissionsNotGranted.toArray(a);

        ActivityCompat.requestPermissions(this, a, 100);

    }
}
