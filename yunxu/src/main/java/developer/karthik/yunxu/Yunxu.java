package developer.karthik.yunxu;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;
import java.util.List;

public final class Yunxu {


    private String TAG = getClass().getSimpleName();
    private Activity parentActivity;
    private String permissionDetailTitle;
    private String getPermissionDetailDesc;

    private List<String> permissions;

    private Yunxu(Activity activity) {
        this.parentActivity = activity;
    }

    public static Yunxu withActivity(Activity activity) {
        return new Yunxu(activity);
    }

    public Yunxu permissions(List<String> permissionList) {
        this.permissions = permissionList;
        return this;
    }

    public void executePermission() {
        Intent intent = new Intent(parentActivity, YunxuActivity.class);
        intent.putExtra("permissions",(Serializable) permissions);
        intent.putExtra("title",permissionDetailTitle);
        intent.putExtra("desc",getPermissionDetailDesc);

        parentActivity.startActivity(intent);
    }

    public Yunxu setPermissionTitle(String title){
        this.permissionDetailTitle = title;
        return this;
    }

    public Yunxu setPermissionDescription(String desc){
        this.getPermissionDetailDesc = desc;
        return this;
    }




}
