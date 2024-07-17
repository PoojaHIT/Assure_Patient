package com.hit.assure.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hit.assure.CustomWebChromeClient;
import com.hit.assure.R;
import com.hit.assure.WebViewUtils;

import java.util.ArrayList;
import java.util.List;

public class VideoCallingActivity extends AppCompatActivity {

    //  public String roomUrlString = "https://assureclinic.whereby.com/demo-0139e62f-559a-45c8-bd1f-4ddfd5770c99"; // Replace by your own
    public String roomUrlString = ""; // Replace by your own
    //   public String roomUrlString = "https://harish.whereby.com/example-prefix0bda0a5d-28d3-4d16-a07f-e1305b59a2d6?roomKey=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZWV0aW5nSWQiOiI1NDI4ODQ4NyIsInJvb21SZWZlcmVuY2UiOnsicm9vbU5hbWUiOiIvZXhhbXBsZS1wcmVmaXgwYmRhMGE1ZC0yOGQzLTRkMTYtYTA3Zi1lMTMwNWI1OWEyZDYiLCJvcmdhbml6YXRpb25JZCI6IjE1ODI4MyJ9LCJpc3MiOiJodHRwczovL2FjY291bnRzLnNydi53aGVyZWJ5LmNvbSIsImlhdCI6MTY1MDI2MTk3NSwicm9vbUtleVR5cGUiOiJtZWV0aW5nSG9zdCJ9.j2YRJZ0ewS1cGvz03mKrgbW9sgnvOnuzj5V9qNAxTfs"; // Replace by your own
    private String roomParameters = "?skipMediaPermissionPrompt";

    private static final int PERMISSION_REQUEST_CODE = 1234;
    private String[] requiredDangerousPermissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO
    };

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_calling);
        this.webView = findViewById(R.id.webView);
        WebViewUtils.configureWebView(this.webView);
        this.webView.setWebChromeClient(new CustomWebChromeClient(this));
        this.webView.setWebViewClient(new WebViewClient());

        if (getIntent() != null){
            roomUrlString = getIntent().getStringExtra("roomUrl");
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.webView.getUrl() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.isPendingPermissions()) {
                // This explicitly requests the camera and audio permissions.
                // It's fine for a demo app but should probably be called earlier in the flow,
                // on a user interaction instead of onResume.
                this.requestCameraAndAudioPermissions();
            } else {
                this.loadEmbeddedRoomUrl();
            }
        }
    }

    private void loadEmbeddedRoomUrl() {
        this.webView.loadUrl(roomUrlString + roomParameters);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (this.grantResultsContainsDenials(grantResults)) {
                    // Show some permissions required dialog.
                } else {
                    // All necessary permissions granted, continue loading.
                    this.loadEmbeddedRoomUrl();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraAndAudioPermissions() {
        this.requestPermissions(this.getPendingPermissions(), PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private String[] getPendingPermissions() {
        List<String> pendingPermissions = new ArrayList<>();
        for (String permission : this.requiredDangerousPermissions) {
            if (this.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                pendingPermissions.add(permission);
            }
        }
        return pendingPermissions.toArray(new String[pendingPermissions.size()]);
    }

    private boolean isPendingPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        return this.getPendingPermissions().length > 0;
    }

    private boolean grantResultsContainsDenials(int[] grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return true;
            }
        }
        return false;
    }



}