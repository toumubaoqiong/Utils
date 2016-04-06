package com.vince.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 *description:sdCard工具类
 *author:vince
 */
public class SDCardTool {
	// 允许保存文件操作的最小空间值
	public static final long DEFAULT = 5L;
	
	private SDCardTool(){
		
	}
	
	/**
	 * 功能描述：判断SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean existSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 功能描述：获得SD卡的可用空间大小
	 * 
	 * @return
	 */
	public static long getSDFreeSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		// 返回SD卡空闲大小
		// 单位MB
		return (freeBlocks * blockSize) / 1024 / 1024; 
	}

	/**
	 * 功能描述：获得SD卡的总容量
	 * 
	 * @return
	 */
	public static long getSDAllSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 获取所有数据块数
		long allBlocks = sf.getBlockCount();
		// 返回SD卡大小
		// 单位MB
		return (allBlocks * blockSize) / 1024 / 1024; 
	}

	/**
	 * 功能描述：判断是否可以写数据到SDCard
	 * 
	 * @return
	 */
	public static boolean canWriteDataToSDcard() {
		if (existSDCard()) {
			if (getSDFreeSize() >= DEFAULT) {
				return true;
			}
		}
		// 逻辑处理
		return false;
	}
}
