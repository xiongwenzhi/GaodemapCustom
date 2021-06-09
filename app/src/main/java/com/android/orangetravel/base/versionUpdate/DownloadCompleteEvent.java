package com.android.orangetravel.base.versionUpdate;

/**
 * 下载完成事件
 *
 * @author yangfei
 * @date 2018/9/11 11:20
 */
public class DownloadCompleteEvent {

    private String path;

    public DownloadCompleteEvent(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}