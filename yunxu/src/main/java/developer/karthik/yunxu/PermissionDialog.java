package developer.karthik.yunxu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PermissionDialog extends DialogFragment {

    PermissionDialogButtonClickListener buttonClickListener;

    public PermissionDialog() {

    }

    public void setButtonClickListener(PermissionDialogButtonClickListener buttonClickListener){
        this.buttonClickListener = buttonClickListener;
    }

    public static PermissionDialog newInstance(String title,String desc){
        PermissionDialog permissionDialog = new PermissionDialog();

        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("desc",desc);
        permissionDialog.setArguments(bundle);
        return permissionDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.permission_dialog,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView)view.findViewById(R.id.title)).setText(getArguments().getString("title"));
        ((TextView)view.findViewById(R.id.desc)).setText(getArguments().getString("desc"));

        ((TextView)view.findViewById(R.id.grant_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickListener.onGrantButtonClicked();
            }
        });

        ((TextView)view.findViewById(R.id.deny_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickListener.onDenyButtonClicked();
            }
        });

    }
}
