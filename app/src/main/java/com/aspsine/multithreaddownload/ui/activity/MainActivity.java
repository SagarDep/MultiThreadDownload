package com.aspsine.multithreaddownload.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.multithreaddownload.R;
import com.aspsine.multithreaddownload.service.CallBack;
import com.aspsine.multithreaddownload.service.DownloadManager;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, CallBack {
    public static final String DOWNLOAD_URL = "http://js.soufunimg.com/industry/csis/app/CIQuestionnaire_android_-2000.apk";
    TextView tvName;
    TextView tvProgress;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnPause).setOnClickListener(this);
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
        tvName = (TextView) findViewById(R.id.tvFileName);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(100);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnStart) {
            DownloadManager.getInstance().download("CIQuestionnaire_android.apk", DOWNLOAD_URL, this);
        } else if (v.getId() == R.id.btnPause) {
            DownloadManager.getInstance().pause(DOWNLOAD_URL);
        } else if (v.getId() == R.id.btnCancel) {
            DownloadManager.getInstance().cancel(DOWNLOAD_URL);
        }
    }

    @Override
    public void onConnected(int total) {
        Log.i("MainActivity", "onConnected:" + total);
    }

    @Override
    public void onProgress(int finished, int total, int progress) {
        pb.setProgress(progress);
        tvProgress.setText(progress + "%");
    }

    @Override
    public void onComplete() {
        Log.i("MainActivity", "onComplete");
    }

    @Override
    public void onDownloadPause() {
        Log.i("MainActivity", "onDownloadPause");
    }

    @Override
    public void onDownloadCancel() {
        Log.i("MainActivity", "onDownloadCancel");
    }

    @Override
    public void onFailure(Exception e) {
        Log.i("MainActivity", "onFailure");
        e.printStackTrace();
    }
}
