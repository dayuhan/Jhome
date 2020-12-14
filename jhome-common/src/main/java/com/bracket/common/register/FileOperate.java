package com.bracket.common.register;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class FileOperate {
    private static String _baseDirectory;
    private static ReadWriteLock _CacheDataRwl;

    static {
        _CacheDataRwl = new ReentrantReadWriteLock(false);
        _CacheDataRwl.readLock();
        _CacheDataRwl.writeLock();
        try {
//			_baseDirectory = URLDecoder.decode(FileOperate.class.getCAlassLoader().getResource("").toURI().getPath(),
//					"UTF-8") + "license/";
            String path = FileOperate.class.getClassLoader().getResource("").toURI().getPath();
            if (path == null || path == "") {
                path = FileOperate.class.getClassLoader().getResource("").getPath();
            }
            int pos = path.indexOf("file:");
            if (pos > -1) {
                path = path.substring(pos + 5);
            }
            _baseDirectory = URLDecoder.decode(path, "UTF-8") + "license/";
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String baseDirectory() {
        return _baseDirectory;
    }

    public static boolean isExist(String fileName, boolean isFullPath) {
        return new java.io.File(isFullPath ? fileName : (_baseDirectory + fileName)).isFile();
    }

    /**
     * 读取文件内容
     *
     * @param
     * @return
     */
    public static String ReadInfo(String fileName, boolean isFullPath) {
        String str = "";

        // AcquireReaderLock(-1);

        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(isFullPath ? fileName : (_baseDirectory + fileName)));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                result.append(s);
            }
            str = result.toString();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // _readLock.unlock();
        }
        return str;
    }

    /**
     * 读取文件内容
     *
     * @param fileName 不带路径的文件名
     * @return
     */
    public static void WriteInfo(String fileName, String contents, boolean isNewLine, boolean isFullPath) {
        try {
            String licensePath = isFullPath ? fileName : (_baseDirectory + fileName);
            if ((new java.io.File(_baseDirectory)).isDirectory() == false) {
                (new java.io.File(_baseDirectory)).mkdirs();
            }
            if (!isNewLine && (new java.io.File(licensePath)).isFile()) {
                (new java.io.File(licensePath)).delete();
            }
            FileOutputStream fs = new FileOutputStream(licensePath, isNewLine);
            byte[] data = contents.getBytes();
            // 获得字节数组
            fs.write(data, 0, data.length);
            // 开始写入
            fs.flush();
            fs.close();
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    public static void WriteLog(String fileName, String contents) {

        try {
            String licensePath = (_baseDirectory + fileName);
            if ((new java.io.File(_baseDirectory)).isDirectory() == false) {
                (new java.io.File(_baseDirectory)).mkdirs();
            }

            FileOutputStream fs = new FileOutputStream(licensePath, true);
            byte[] data = contents.getBytes();
            // 获得字节数组
            fs.write(data, 0, data.length);
            // 开始写入
            fs.flush();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
