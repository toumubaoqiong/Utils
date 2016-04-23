package com.vince.utils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

/**
 * decription ：
 * author ： vince
 */
public class AssertUtils {

    AssetManager mAssetManager;
    private Bitmap getDrawableBitmap(String name) {
        Bitmap bm = null;
        if(mAssetManager != null) {
            InputStream assetFile = null;

            try {
                assetFile = mAssetManager.open(name);
                bm = BitmapFactory.decodeStream(assetFile);
                assetFile.close();
            } catch (IOException var5) {
                var5.printStackTrace();
            }
        }

        return bm;
    }


    private Drawable getDrawable(String name) {
        BitmapDrawable da = null;
        if(mAssetManager != null) {
            InputStream assetFile = null;

            try {
                assetFile = mAssetManager.open(name);
                Bitmap e = BitmapFactory.decodeStream(assetFile);
                da = new BitmapDrawable(e);
                assetFile.close();
            } catch (IOException var5) {
                var5.printStackTrace();
            }
        }

        return da;
    }
}
