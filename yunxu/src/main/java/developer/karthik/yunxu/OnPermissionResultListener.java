package developer.karthik.yunxu;

public interface OnPermissionResultListener {

    void onPermissionGranted();
    void onPermissionDenied();
    void onShowPermissionDialog();

}
