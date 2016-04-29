package com.vince.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * description:文件操作类（SD卡上的检查文件是否存在、创建目录、创建文件、删除文件、删除文件夹、目录移动、文件移动等操作）
 * @author:vince
 */
public class FileUtils {
    private FileUtils() {
        // 避免被外部实例化
    }

    /**
     * 在SD卡上创建文件 “aFileName 如test.xml”
     *
     * @param fileName 文件名
     * @return 文件句柄
     * @throws IOException
     */
    public static File creatSDFile(String filePath) throws IOException {
        File file = null;

        if (null != filePath) {
            file = new File(filePath);
            file.createNewFile();
        }

        return file;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     *
     * @param fileName 文件名
     * @return true 文件存在，false 文件不存在
     */
    public static boolean isFileExist(String filePath) {
        File file = null;
        boolean isExist = false;

        if (!TextUtils.isEmpty(filePath)) {
            file = new File(filePath);
            isExist = (file.isFile()) && file.exists();
        }

        return isExist;
    }

    /**
     * 删掉文件
     */
    public static boolean removeFile(String filePath) {
        if (null != filePath) {
            File mFile = new File(filePath);
            if (mFile.exists()) {
                mFile.delete();
                return true;
            }
        }

        return false;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath, String[] suffixs) {
        boolean flag = false;

        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        if (null != files) {
            for (File file : files) {
                if (file.isFile()) {
                    for (String suffix : suffixs) {
                        if (file.getAbsolutePath().endsWith(suffix)) {
                            flag = removeFile(file.getAbsolutePath());
                            break;
                        }
                    }
                    if (!flag) {
                        break;
                    }
                } else {
                    flag = deleteDirectory(file.getAbsolutePath(), suffixs);
                    if (!flag)
                        break;
                }
            }
        }

        return flag;
    }

    /**
     * 功能描述： 检查SD卡是否存在
     */
    public static boolean checkSdCardSystemIsOk() {
        return "/mnt/sdcard".equals(Environment.getExternalStorageState());
    }

    /**
     * 判断磁盘空间是否足够
     *
     * @param 是否有这样大的控件
     * @return true 有 false 无
     */
    public static boolean isAvaiableSpace(String path, int sizeMb) {
        return isSpaceEnough(path, sizeMb);
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName 目录名称
     * @return 文件句柄
     */
    public static File creatSDDir(String filePath) {
        if (null == filePath || filePath.contains(".")) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    /**
     * 新建目录
     *
     * @param folderPath String 如 c:/fqf
     * @return boolean
     */
    public static void newFolder(String folderPath) {
        try {
            String filePath = folderPath;
            java.io.File myFilePath = new java.io.File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            DebugPrint.printLogE(Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * 功能描述：获取文件大小
     */
    public static int getFileSize(String filePath) {
        long fileSize = 0;
        if (null != filePath) {
            File file = new File(filePath);
            fileSize = file.length();
        }

        return (int) fileSize;
    }

    /**
     * 写临时文件
     */
    public static void saveToTmpFile(byte[] swfBuffer, String filePath) {
        if (null == swfBuffer || TextUtils.isEmpty(filePath)) {
            return;
        }
        File file = new File(filePath);

        if (!file.exists()) {
            String parentPath = file.getParent();
            try {
                if (parentPath != null) {
                    File parentFile = new File(parentPath);
                    parentFile.mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                DebugPrint.printLogE(Arrays.toString(e.getStackTrace()));
            }
        }

        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(swfBuffer);
            fout.close();
        } catch (FileNotFoundException e) {
            System.out.println("打开文件失败!");
            DebugPrint.printLogE(Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            DebugPrint.printLogE(Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * 获取Assets文件夹下的数据信息
     */
    public static byte[] getAssetsFileBuffer(Context context, String filePath) {
        if (null == context || TextUtils.isEmpty(filePath)) {
            return null;
        }
        try {
            InputStream inputStream = context.getAssets().open(filePath);

            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            inputStream.close();

            return buffer;
        } catch (IOException e) {
            DebugPrint.printLogE(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    /**
     * @return get flashb path
     */
    public static String getFlashBPath() {
        return Environment.getExternalStorageDirectory().toString() + File.separator;
    }

    /**
     * 判断空间是否足够
     */
    private static boolean isSpaceEnough(String filePath, int sizeMb) {
        boolean hasSpace = false;
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        StatFs statFs = new StatFs(filePath);

        long blockSize = statFs.getBlockSize();
        long blocks = statFs.getAvailableBlocks();
        double availableSpare = (blocks * blockSize * 1.0) / (1024 * 1024);

        if (availableSpare > sizeMb) {
            hasSpace = true;
        }

        return hasSpace;
    }

    public static byte[] getFileContent(String filePath) {
        File file = new File(filePath);
        FileInputStream input;
        try {
            input = new FileInputStream(file);

            byte[] buffer = new byte[(int) file.length()];
            input.read(buffer);
            input.close();
            return buffer;
        } catch (FileNotFoundException e) {
            DebugPrint.printLogE(Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            DebugPrint.printLogE(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    /**
     * 移动目录
     *
     * @param srcDirName  源目录完整路径
     * @param destDirName 目的目录完整路径
     * @return 目录移动成功返回true，否则返回false
     */
    public static boolean moveDirectory(String srcDirName, String destDirName) {
        File srcDir = new File(srcDirName);
        if (!srcDir.exists() || !srcDir.isDirectory()) {
            return false;
        }

        File destDir = new File(destDirName);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        /**
         * 如果是文件则移动，否则递归移动文件夹。删除最终的空源文件夹
         * 注意移动文件夹时保持文件夹的树状结构
         */
        File[] sourceFiles = srcDir.listFiles();
        for (File sourceFile : sourceFiles) {
            if (sourceFile.isFile()) {
                moveFile(sourceFile.getAbsolutePath(), destDir.getAbsolutePath());
            } else if (sourceFile.isDirectory()) {
                moveDirectory(sourceFile.getAbsolutePath()
                        , destDir.getAbsolutePath() + File.separator + sourceFile.getName());
            }
        }

        return srcDir.delete();
    }

    /**
     * 移动文件
     *
     * @param srcFileName 源文件完整路径
     * @param destDirName 目的目录完整路径
     * @return 文件移动成功返回true，否则返回false
     */
    public static boolean moveFile(String srcFileName, String destDirName) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }

        File destDir = new File(destDirName);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        return srcFile.renameTo(new File(destDirName + File.separator + srcFile.getName()));
    }


    //获取文件名（不含后缀）
    public static String getFileNameNoEx(String allSavePath) {
        File targetFile = new File(allSavePath);
        String filename = targetFile.getName();
        Log.d("FileExtractorUtils", "getFileNameNoEx : " + filename);
        if(filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf(46);
            if(dot > -1 && dot < filename.length()) {
                return filename.substring(0, dot);
            }
        }

        return filename;
    }


    //获取文件的当前目录
    public static String getTargetPath(String savePath) {
        if(savePath != null && savePath.length() > 0) {
            int start = savePath.indexOf(47);
            int end = savePath.lastIndexOf(47);
            if(end > -1 && end < savePath.length() - 1) {
                return savePath.substring(start, end + 1);
            }
        }

        return savePath;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     * @create_date 2014-10-8 上午11:58:18
     */
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void bytesToImgSave(byte[] bytes, String imgFile)
            throws Exception {
        // UUID序列号作为保存图片的名称

        File f = new File(imgFile);

        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
            for (int i = 0; i < bytes.length; i++) {
                out.write(bytes[i]);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param @param realPath
     * @return void
     * @Title: deleteFile
     * @Description: 删除文件或文件夹
     */
    public static void deleteFile(String realPath) {
        File file = new File(realPath);
        if (file.isFile() && file.exists()) {
            file.delete();
        } else {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            file.delete();
        }
    }
}
