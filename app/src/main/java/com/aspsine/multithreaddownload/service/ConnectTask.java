package com.aspsine.multithreaddownload.service;

/**
 * Created by Aspsine on 2015/7/20.
 */

import android.util.Log;

import com.aspsine.multithreaddownload.entity.DownloadInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * init thread
 */
public class ConnectTask implements Runnable {
    private DownloadInfo mDownloadInfo;
    private OnConnectedListener mOnConnectedListener;

    interface OnConnectedListener {
        void onConnected(DownloadInfo downloadInfo);

        void onFail(DownloadException de);
    }

    public ConnectTask(DownloadInfo downloadInfo, OnConnectedListener listener) {
        mDownloadInfo = downloadInfo;
        this.mOnConnectedListener = listener;
    }

    @Override
    public void run() {
        Log.i("ThreadInfo", "InitThread = " + this.hashCode());
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL(mDownloadInfo.getUrl());
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setConnectTimeout(10 * 1000);
            int length = -1;
            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                length = httpConn.getContentLength();
            }
            if (length <= 0) {
                //TODO
                throw new DownloadException("length<0 T-T~");
            } else {
                mDownloadInfo.setLength(length);
                mOnConnectedListener.onConnected(mDownloadInfo);
            }
        } catch (IOException e) {
            mOnConnectedListener.onFail(new DownloadException(e));
        } catch (DownloadException e) {
            mOnConnectedListener.onFail(new DownloadException(e));
        } finally {
            httpConn.disconnect();
        }
    }
}
