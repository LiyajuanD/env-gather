package com.briup.smart.env.util;

/**
 * Backup接口是物联网数据中心项目备份模块的规范
 */
public interface Backup{
	/**
	 * 在保存数据时,在原来文件的基础上进行追加
	 */
	public static final boolean STORE_APPEND = true;
	
	/**
	 * 在保存数据时,覆盖原来的文件
	 */
	public static final boolean STORE_OVERRIDE = false;
	
	/**
	 * 在读取数据同时,删除备份文件
	 */
	public static final boolean LOAD_REMOVE = true;
	
	/**
	 * 在读取数据同时,不删除备份文件
	 */
	public static final boolean LOAD_UNREMOVE = false;
	
	
	/**
	 * 读取备份文件,返回集合对象
	 * 读取store方法记录的文件，在处理数据时，跳过已经处理过的数据
	 */
	public Object load(String fileName,boolean del) throws Exception;
	/**
	 * 将需要备份的集合对象写入到备份文件
	 *  记录读取的新文件，在处理数据时，跳过已经处理过的数据
	 *  记录已经处理好的数据字节数，并且记录下来
	 */
	//				   新文件的位置     读取到文件的字节   是否追加（默认false）
	public void store(String fileName,Object obj,boolean append)throws Exception;
}

