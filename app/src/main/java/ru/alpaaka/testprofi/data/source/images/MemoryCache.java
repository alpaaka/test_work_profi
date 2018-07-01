package ru.alpaaka.testprofi.data.source.images;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MemoryCache {

    private Map<String, SoftReference<Bitmap>> memoryCache = Collections
            .synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());

    public Bitmap get(String url) {
        if (memoryCache.get(url) == null) {
            return null;
        }
        return memoryCache.get(url).get();
    }

    public void put(String url, Bitmap bitmap) {
        memoryCache.put(url, new SoftReference<>(bitmap));
    }
}
