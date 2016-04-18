package com.vince.utils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * decription ：
 * author ： vince
 * Created at 2016/4/18.
 */
public class BitmapUtils {

    AssetManager mAssetManager;

    //从Assert中获取bitmap
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

    //从Assert中获取Drawable
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


    //保存bitmap
    public boolean saveBitmap( String filePath, Bitmap bitmap, Bitmap.CompressFormat format, boolean needPrompt ){
        long time = System.currentTimeMillis();
        boolean isSaveSuccess = false;
        File file = new File( filePath );
        if( null == bitmap ){
            file.delete();
            return true;
        }

        DataOutputStream cout = null;

        try{
            cout = new DataOutputStream( new BufferedOutputStream( new FileOutputStream( file ) ) );
            isSaveSuccess = bitmap.compress( format, 100, cout );
            cout.flush();
        } catch( IOException e ){
            DebugPrint.printLogE(Arrays.toString(e.getStackTrace()));
        } finally {
            if( cout != null ){
                try{
                    cout.close();
                } catch( IOException e ){
                    DebugPrint.printLogE(Arrays.toString(e.getStackTrace()));
                }
            }
        }

        time = System.currentTimeMillis() - time;

        return isSaveSuccess;
    }
}
