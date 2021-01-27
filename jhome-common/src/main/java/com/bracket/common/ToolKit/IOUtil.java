package com.bracket.common.ToolKit;

import java.io.*;
import java.util.ArrayList;

public class IOUtil {

    public static void loadFileByReader(String path) {

        try {
            FileReader fr = new FileReader(path);
            // 创建BufferedReader对象，参数为要求Reader类型，我们传入它的一个子类FilReader对象
            BufferedReader br = new BufferedReader(fr);
            // BufferedReader的read方法是读取一行，且返回的是一个字符串，当流为空的时候返回null
            String tmp = "";
            while ((tmp = br.readLine()) != null) {
                System.out.println(tmp);
            }
            // 关闭流，装饰着模式，只要关闭最外层的流即可
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFileByStream(String path) {
        try {
            // 创建文件字节输入流
            FileInputStream fis = new FileInputStream(path);
            // 使用转换流将字节流转换为字符流
            InputStreamReader isr = new InputStreamReader(fis);

            // 创建BufferedReader对象，参数为要求Reader类型，我们传入它的一个子类InputStreamReader对象(InputStreamReader也是Reader子类)
            BufferedReader br = new BufferedReader(isr);
            // BufferedReader的read方法是读取一行，且返回的是一个字符串，当流为空的时候返回null
            String tmp = "";
            while ((tmp = br.readLine()) != null) {
                System.out.println(tmp);
            }
            // 关闭流，装饰着模式，只要关闭最外层的流即可
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取某个文件夹下的所有文件
     *
     * @param fileNameList 存放文件名称的list
     * @param path         文件夹的路径
     * @return
     */
    public static void getAllFileName(String path, ArrayList<String> fileNameList) {
        //ArrayList<String> files = new ArrayList<String>();
        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//              System.out.println("文     件：" + tempList[i]);
                //fileNameList.add(tempList[i].toString());
                fileNameList.add(tempList[i].getName());
            }
            if (tempList[i].isDirectory()) {
//              System.out.println("文件夹：" + tempList[i]);
                getAllFileName(tempList[i].getAbsolutePath(), fileNameList);
            }
        }
        return;
    }

    public static void loadFileByBufferedWriter(String path) {
        try {
            // 创建输入缓冲流对象，需要传入（输入）Reader对象，我们将使用转换流将字节流转为字符流
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(path)));
            // 创建输入缓冲流对象，需要传入（输出）Writer对象，我们将使用转换流将字符流转为字节流
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path)));
            // 零时字符串
            String tmp = "";
            // BufferedReader提供一行一行的读入，当有空行的时候，返回null
            while ((tmp = br.readLine()) != null) {
                bw.write(tmp);
                bw.newLine();// BufferedWriter提供的方法，新创一行
            }
            // 刷新流缓冲，确保将流写完到硬盘中
            bw.flush();
            // 关闭流
            br.close();
            bw.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
