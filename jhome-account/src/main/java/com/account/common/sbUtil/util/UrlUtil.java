package com.account.common.sbUtil.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * 对URL所指向的内容的進行加载操作
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 *
 */
public class UrlUtil {

    /**
     * 获取url网址返回的数据内容
     * @param urlStr
     * @return
     */
    public static String loadURL(String urlStr){
        try{
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            String responseStr = ConvertToString(inputStream);
            return responseStr;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    private static String ConvertToString(InputStream inputStream){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder result = new StringBuilder();
        String line = null;
        try {
            while((line = bufferedReader.readLine()) != null){
                result.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                inputStreamReader.close();
                inputStream.close();
                bufferedReader.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}
