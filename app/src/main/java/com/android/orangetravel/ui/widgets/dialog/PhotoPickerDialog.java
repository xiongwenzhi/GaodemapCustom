package com.android.orangetravel.ui.widgets.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.orangetravel.R;
import com.android.orangetravel.base.utils.ConstantUtil;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.widgets.dialog.BaseDialog;
import com.android.orangetravel.ui.widgets.utils.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 照片选择弹出框
 *
 * @author yangfei
 * @date 2018/6/13
 */
public class PhotoPickerDialog extends BaseDialog implements View.OnClickListener {

    private Activity mActivity;

    public PhotoPickerDialog(@NonNull Activity mActivity) {
        super(mActivity);
        // 弹出框位置
        setGravity(Gravity.BOTTOM);
        // 设置动画
        setAnimation(R.style.DialogBottomAnim);

        this.mActivity = mActivity;
        initView();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_photo_picker;
    }

    // 拍照
    private TextView dialog_photo_picker_photograph;
    // 从相册选择
    private TextView dialog_photo_picker_album;
    // 取消
    private TextView dialog_photo_picker_cancel;

    public int maxSelectNum = 9;
    public int minSelectNum = 1;
    public int selectionMode = PictureConfig.MULTIPLE;
    public boolean isCamera = false;
    public boolean enableCrop = false;
    public int aspectRatioX = 1;
    public int aspectRatioY = 1;
    public boolean freeStyleCropEnabled = false;

//    private List<LocalMedia> selectionMedia;

    private void initView() {
        dialog_photo_picker_photograph = (TextView) findViewById(R.id.dialog_photo_picker_photograph);
        dialog_photo_picker_album = (TextView) findViewById(R.id.dialog_photo_picker_album);
        dialog_photo_picker_cancel = (TextView) findViewById(R.id.dialog_photo_picker_cancel);
        // 拍照
        dialog_photo_picker_photograph.setOnClickListener(this);
        // 从相册选择
        dialog_photo_picker_album.setOnClickListener(this);
        // 取消
        dialog_photo_picker_cancel.setOnClickListener(this);

//        selectionMedia = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*拍照*/
            case R.id.dialog_photo_picker_photograph:
                PhotoPickerDialog.super.dismiss();
                // -----------------------------------------------
                PictureSelector.create(mActivity)
                        .openCamera(PictureMimeType.ofImage())
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(minSelectNum)// 最小选择数量
                        .selectionMode(selectionMode)// PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .isCamera(isCamera)// 是否显示拍照按钮
//                        .enableCrop(enableCrop)// 是否裁剪
                        .withAspectRatio(aspectRatioX, aspectRatioY)// 裁剪比例 16:9 3:2 3:4 1:1
                        .freeStyleCropEnabled(freeStyleCropEnabled)// 裁剪框是否可拖拽
                        .rotateEnabled(false)// 裁剪是否可旋转图片
//                        .selectionMedia(selectionMedia)// 是否传入已选图片 List<LocalMedia> list
                        .compress(true)
//                        .setOutputCameraPath("/" + ConstantUtil.APP_NAME)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                // -----------------------------------------------
                break;
            /*从相册选择*/
            case R.id.dialog_photo_picker_album:
                PhotoPickerDialog.super.dismiss();
                // -----------------------------------------------
                PictureSelector.create(mActivity)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(minSelectNum)// 最小选择数量
                        .selectionMode(selectionMode)// PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .isCamera(isCamera).// 是否显示拍照按钮
                        loadImageEngine(GlideEngine.createGlideEngine())
//                        .enableCrop(enableCrop)// 是否裁剪
//                        .withAspectRatio(aspectRatioX, aspectRatioY)// 裁剪比例 16:9 3:2 3:4 1:1
//                        .rotateEnabled(false)// 裁剪是否可旋转图片
//                        .selectionMedia(selectionMedia)// 是否传入已选图片 List<LocalMedia> list
                        .compress(true)
//                        .setOutputCameraPath("/" + ConstantUtil.APP_NAME)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                // -----------------------------------------------
                break;
            /*取消*/
            case R.id.dialog_photo_picker_cancel:
                PhotoPickerDialog.super.dismiss();
                break;
            default:
                break;
        }
    }

    /**
     * 处理结果
     */
    public List<LocalMedia> handleResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode &&
                PictureConfig.CHOOSE_REQUEST == requestCode) {
            return PictureSelector.obtainMultipleResult(data);
        }
        return null;
    }

    @Nullable
    public List<String> handleResultSuper(int requestCode, int resultCode, Intent data) {
        List<String> result = null;

        if (Activity.RESULT_OK == resultCode &&
                PictureConfig.CHOOSE_REQUEST == requestCode) {
            List<LocalMedia> mediaList = PictureSelector.obtainMultipleResult(data);

            if (StringUtil.isListNotEmpty(mediaList)) {
                result = new ArrayList<>(mediaList.size());

                for (LocalMedia media : mediaList) {
                    String path;
                    if (media.isCompressed()) {
                        path = media.getCompressPath();
                    } else if (media.isCut()) {
                        path = media.getCutPath();
                    } else {
                        path = media.getPath();
                    }
                    result.add(path);
                }
            }
        }
        return result;
    }

    /**
     * 清除缓存文件
     * 包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统SD卡权限
     */
    public void clearCacheFile() {
//        PictureFileUtils.deleteCacheDirFile(mActivity);
//        PictureFileUtils.deleteExternalCacheDirFile(mActivity);
    }

    /**
     * 预览图片
     * 可自定长按保存路径
     * *注意*.themeStyle(themeId); 不可少，否则闪退...
     */
    public void previewImage(int themeStyle, int position, List<LocalMedia> selectList) {
        PictureSelector.create(mActivity).themeStyle(themeStyle).openExternalPreview(position, "/custom_file", selectList);
        PictureSelector.create(mActivity).themeStyle(themeStyle).openExternalPreview(position, selectList);
    }

    /**
     * 预览视频
     */
    public void previewVideo(String videoPath) {
        PictureSelector.create(mActivity).externalPictureVideo(videoPath);
    }

}
//// 进入相册 以下是例子：用不到的api可以不写
// PictureSelector.create(MainActivity.this)
//         .openGallery()//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//         .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
//         .maxSelectNum()// 最大图片选择数量 int
//         .minSelectNum()// 最小选择数量 int
//         .imageSpanCount(4)// 每行显示个数 int
//         .selectionMode()// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
//         .previewImage()// 是否可预览图片 true or false
//         .previewVideo()// 是否可预览视频 true or false
//         .enablePreviewAudio() // 是否可播放音频 true or false
//         .isCamera()// 是否显示拍照按钮 true or false
//         .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
//         .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//         .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//         .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
//         .enableCrop()// 是否裁剪 true or false
//         .compress()// 是否压缩 true or false
//         .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//         .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//         .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
//         .isGif()// 是否显示gif图片 true or false
//         .compressSavePath(getPath())//压缩图片保存地址
//         .freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
//         .circleDimmedLayer()// 是否圆形裁剪 true or false
//         .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//         .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
//         .openClickSound()// 是否开启点击声音 true or false
//         .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
//         .previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//         .cropCompressQuality()// 裁剪压缩质量 默认90 int
//         .minimumCompressSize(100)// 小于100kb的图片不压缩
//         .synOrAsy(true)//同步true或异步false 压缩 默认同步
//         .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//         .rotateEnabled() // 裁剪是否可旋转图片 true or false
//         .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
//         .videoQuality()// 视频录制质量 0 or 1 int
//         .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//         .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//         .recordVideoSecond()//视频秒数录制 默认60s int
//         .isDragFrame(false)// 是否可拖动裁剪框(固定)
//         .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code