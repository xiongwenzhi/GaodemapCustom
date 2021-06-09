package com.android.orangetravel.base.widgets.photopicker.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.android.orangetravel.base.utils.FileUtil;
import com.android.orangetravel.base.utils.StringUtil;
import com.android.orangetravel.base.utils.SystemUtil;
import com.android.orangetravel.base.utils.ToastUitl;
import com.android.orangetravel.base.widgets.GridViewScroll;
import com.android.orangetravel.base.widgets.photopicker.GridViewAdapter;
import com.android.orangetravel.base.widgets.photopicker.PhotoPickerActivity;
import com.android.orangetravel.base.widgets.photopicker.ShowLargerActivity;
import com.yang.base.R;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Class
 *
 * @author yangfei
 */
public class PhotoPickerView extends LinearLayout {

    public PhotoPickerView(Context mContext) {
        super(mContext);
        init(mContext);
    }

    public PhotoPickerView(Context mContext, @Nullable AttributeSet attrs) {
        super(mContext, attrs);
        init(mContext);
    }

    public PhotoPickerView(Context mContext, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
        init(mContext);
    }

    // 上下文
    private Context mContext;
    private Activity mActivity;
    // GridView
    private GridViewScroll view_photo_picker_gv;
    // 适配器
    private GridViewAdapter mGridAdapter;
    // 数据源
    private ArrayList<String> mGirdLists;
    // 临时保存图片的File
    private File tempFile;
    // 保存截图的图片路径,方便删除
    // private List<String> mTempPathName;

    private final int CHOOSE_IMAGES = 801;// 选择图片
    private final int CUT_OUT_PICTURES = 802;// 裁剪图片

    private void init(Context mContext) {
        this.mContext = mContext;
        this.mActivity = (Activity) mContext;
        LayoutInflater.from(mContext).inflate(R.layout.photo_view_picker, this);
        view_photo_picker_gv = (GridViewScroll) findViewById(R.id.view_photo_picker_gv);
        // 初始化GridView
        initGridView();
    }

    /**
     * 返回结果
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_IMAGES: {/*选择图片*/
                    ArrayList<String> results = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                    if (StringUtil.isListNotEmpty(results)) {
                        /*--多图--不裁剪--*/
                        if (PhotoPickerView.MULTIPLE_NOCROP.equals(mode)) {
                            for (int i = 0, count = results.size(); i < count; i++) {
                                if (!mGirdLists.contains(results.get(i)) && mGirdLists.size() < maxNum) {
                                    mGirdLists.add(results.get(i));
                                }
                            }
                            mGridAdapter.notifyDataSetChanged();
                        /*--单图--不裁剪--*/
                        } else if (PhotoPickerView.SINGLE_NOCROP.equals(mode)) {
                            mGirdLists.clear();
                            int count = mGirdLists.size();
                            if (!mGirdLists.contains(results.get(0)) && count < maxNum) {
                                mGirdLists.add(results.get(0));
                            }
                            mGridAdapter.notifyDataSetChanged();
                        /*--多图--要裁剪--*/
                        /*--单图--要裁剪--*/
                        } else if (PhotoPickerView.MULTIPLE_CROP.equals(mode) || SINGLE_CROP.equals(mode)) {
                            Uri uri = SystemUtil.getFileUri(getContext(), results.get(0));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                // uri = FileProvider.getUriForFile(getContext(), ConstantUtil.FILE_PROVIDER_AUTHORITIES, new File(results.get(0)));
                            } else {
                                // uri = Uri.fromFile(new File(results.get(0)));
                            }
                            // 裁剪图片
                            cutOutPictures(uri);
                        }
                    }
                    break;
                }
                case CUT_OUT_PICTURES: {/*裁剪图片*/
                    String pathName = tempFile.getAbsolutePath();
                    // mTempPathName.add(pathName);
                    /*--多图--要裁剪--*/
                    if (MULTIPLE_CROP.equals(mode)) {
                    /*--单图--要裁剪--*/
                    } else if (SINGLE_CROP.equals(mode)) {
                        mGirdLists.clear();
                    }
                    if (mGirdLists.size() < maxNum) {
                        mGirdLists.add(pathName);
                        mGridAdapter.notifyDataSetChanged();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }

    /**
     * 初始化GridView
     */
    private void initGridView() {
        mGirdLists = new ArrayList<>();
        // mTempPathName = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(mContext, mGirdLists);
        view_photo_picker_gv.setAdapter(mGridAdapter);
        view_photo_picker_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mGirdLists.size()) {
                    if (mGirdLists.size() == maxNum) {
                        showToast("图片数量不超过" + maxNum + "张");
                        return;
                    }
                    // 选择图片
                    choosePicture();
                } else {
                    ShowLargerActivity.start(mContext, mGirdLists, position);
                }
            }
        });
        mGridAdapter.setOnDeleteListener(new GridViewAdapter.OnDeleteListener() {
            @Override
            public void onDelete(int position) {
                mGirdLists.remove(position);
                mGridAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 选择图片
     */
    private void choosePicture() {
        Intent intent = new Intent(mContext, PhotoPickerActivity.class);
        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
        /*--多图--不裁剪--*/
        if (PhotoPickerView.MULTIPLE_NOCROP.equals(mode)) {
            if (mGirdLists.size() == maxNum) {
                showToast("图片数量不超过" + maxNum + "张");
                return;
            }
            intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
        /*--多图--要裁剪--*/
        } else if (PhotoPickerView.MULTIPLE_CROP.equals(mode)) {
            if (mGirdLists.size() == maxNum) {
                showToast("图片数量不超过" + maxNum + "张");
                return;
            }
            intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
        /*--单图--不裁剪--*/
        /*--单图--要裁剪--*/
        } else if (PhotoPickerView.SINGLE_NOCROP.equals(mode) || SINGLE_CROP.equals(mode)) {
            intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
            intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
        }
        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, maxNum - mGirdLists.size());
        mActivity.startActivityForResult(intent, CHOOSE_IMAGES);
//        /*拍照*/
//        Intent intent = new Intent(mContext, PhotoPickerActivity.class);
//        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, false);
//        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
//        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);
//        intent.putExtra(PhotoPickerActivity.TAKING_PICTURES, true);
//        startActivityForResult(intent, REQUESTCODE);
//        /*从相册选择*/
//        Intent intent = new Intent(mContext, PhotoPickerActivity.class);
//        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, false);
//        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
//        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);
//        Activity activity = (Activity) mContext;
//        activity.startActivityForResult(intent, CHOOSE_IMAGES);
    }

    /**
     * 裁剪图片
     */
    private void cutOutPictures(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // intent.putExtra("outputX", 1024);// 输出宽度
        // intent.putExtra("outputY", 1024);// 输出高度
        intent.putExtra("scale", true);// 是否保存比例
        intent.putExtra("return-data", false);// 剪裁是否返回Bitmap
        tempFile = FileUtil.getPictureFile(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        mActivity.startActivityForResult(intent, CUT_OUT_PICTURES);
    }

    public static final String MULTIPLE_NOCROP = "multiple_nocrop";//   --多图--不裁剪--
    public static final String MULTIPLE_CROP = "multiple_crop";//       --多图--要裁剪--
    public static final String SINGLE_NOCROP = "single_nocrop";//       --单图--不裁剪--
    public static final String SINGLE_CROP = "single_crop";//           --单图--要裁剪--
    // 模式
    private String mode = PhotoPickerView.MULTIPLE_NOCROP;
    // 最大选择数量
    private int maxNum = 9;
    // 裁剪比例
    private int aspectX = 1;
    private int aspectY = 1;

    /**
     * 设置模式
     */
    public PhotoPickerView setMode(String mode) {
        this.mode = mode;
        mGirdLists.clear();
        mGridAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 设置最大选择数量
     */
    public PhotoPickerView setMaxNum(int maxNum) {
        this.maxNum = maxNum;
        return this;
    }

    /**
     * 设置裁剪比例
     */
    public PhotoPickerView setCropScale(int aspectX, int aspectY) {
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        return this;
    }

    /**
     * 获取选择的图片
     */
    public ArrayList<String> getGirdLists() {
        return mGirdLists;
    }

    /**
     * 删除本地裁剪的图片
     */
    /*public void deleteLocalCropImages() {
        if (StringUtil.isListNotEmpty(mTempPathName)) {
            for (int i = 0; i < mTempPathName.size(); i++) {
                new File(mTempPathName.get(i)).delete();
            }
        }
    }*/

    /**
     * 显示Toast
     */
    private void showToast(String msg) {
        ToastUitl.showShort(mContext, msg);
    }

}