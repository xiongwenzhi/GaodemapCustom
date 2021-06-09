package com.android.orangetravel.base.glide;

import android.content.Context;

import com.android.orangetravel.base.utils.log.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;

/**
 * AppGlideModule配置
 *
 * @author yangfei
 */
@GlideModule
public class CustomGlideModule extends AppGlideModule {

    /**
     * 通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     */
    @Override
    public void applyOptions(Context mContext, GlideBuilder builder) {
        LogUtil.eSuper("AppGlideModule配置");

        /**
         * 让你的APP比默认多20%的缓存
         */
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(mContext).build();// 内存大小的计算器
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();// 获得内存缓存大小
        // 3072000 -- 16588800
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();// 获取Bitmap池大小
        // 6144000 -- 33177600
        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        // 3686400 -- 19906560
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        // 7372800 -- 39813120
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        /**
         * 设置解码格式
         */
        RequestOptions mOptions = new RequestOptions();
        builder.setDefaultRequestOptions(mOptions.apply((new RequestOptions()).format(DecodeFormat.PREFER_RGB_565)));

        /**
         * 自定义磁盘缓存
         */
        builder.setDiskCache(new InternalCacheDiskCacheFactory(mContext, GlideCacheConfig.IMAGE_CATCH_DIR, GlideCacheConfig.GLIDE_CATCH_SIZE));

        /*// 会设置磁盘缓存到APP的内部路径
        builder.setDiskCache(new InternalCacheDiskCacheFactory(mContext, GlideCacheConfig.GLIDE_CATCH_SIZE));
        // 会设置磁盘缓存到外部路径
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(mContext, GlideCacheConfig.GLIDE_CATCH_SIZE));*/

        /*String downloadDirectoryPath = Environment.getDownloadCacheDirectory().getPath();
        // 直接设置一个路径
        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, GlideCacheConfig.GLIDE_CATCH_SIZE));
        // 创建并使用一个在你传递路径里的路径
        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, "glidecache", GlideCacheConfig.GLIDE_CATCH_SIZE));*/
    }

    /**
     * 为App注册一个自定义的String类型的BaseGlideUrlLoader
     */
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.append(String.class, InputStream.class, new CustomBaseGlideUrlLoader.Factory());
        super.registerComponents(context, glide, registry);
        // register ModelLoaders here.
        // glide.register(MyDataModel.class, InputStream.class, new MyUrlLoader.Factory());
    }

    /**
     * 清单解析的开启
     * <p>
     * 这里不开启,避免添加相同的Module两次
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

}