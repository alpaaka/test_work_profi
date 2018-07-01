package ru.alpaaka.testprofi.data.source.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.alpaaka.testprofi.utils.AppExecutor;

public class ImageLoader {

    private MemoryCache memoryCache;
    private AppExecutor appExecutor;
    private ExecutorService executorService;

    public static class ImageLoaderHolder {
        static final ImageLoader instance = new ImageLoader();
    }

    public static ImageLoader getInstance() {
        return ImageLoaderHolder.instance;
    }

    private ImageLoader() {
        this.memoryCache = new MemoryCache();
        this.executorService = Executors.newFixedThreadPool(7);
    }

    public void initialize(AppExecutor appExecutor) {
        this.appExecutor = appExecutor;
    }

    public void bind(ImageView imageView, String url) {
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null) {
            bindImage(imageView, bitmap);
        } else {
            PhotoLoader loader = new PhotoLoader(url, imageView);
            executorService.execute(loader);
        }
    }

    private void bindImage(final ImageView imageView, final Bitmap bitmap) {
        appExecutor.getMainThread().execute(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        });
    }

    private class PhotoLoader implements Runnable {

        private String url;
        private ImageView imageView;

        PhotoLoader(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            Bitmap bitmap = getBitmap(url);
            memoryCache.put(url, bitmap);
            bindImage(imageView, bitmap);
        }

        private Bitmap getBitmap(String url) {
            Bitmap bitmap = null;
            try {
                InputStream is = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
