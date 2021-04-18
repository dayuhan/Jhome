package com.geoServer.conmmon;

import com.geoServer.modules.geoServer.model.vo.ElevationDataVO;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @program: account-root
 * @description: 栅格数据帮助类
 * @author: Daxv
 * @create: 2021-01-13 14:01
 **/
public class GDALUtil {
    protected Logger logger = LoggerFactory.getLogger(GDALUtil.class);

    /**
     * 根据经纬度 读取高程数据
     *
     * @param lon
     * @param lat
     * @return
     */
    public Integer SelectAltitude(double lon, double lat, String path) {
        //海拔
        Integer altitude = 0;
        //支持所有驱动
        gdal.AllRegister();
        //要读取的文件
        String fileName_tif = "D:\\GisData\\xzz\\1.tif";
        fileName_tif = path;
        //只读方式读取数据
        Dataset hDataset = gdal.Open(fileName_tif, gdalconstConstants.GA_ReadOnly);
        //支持中文路径
        gdal.SetConfigOption("gdal_FILENAME_IS_UTF8", "YES");
        //判断是否非空
        if (hDataset == null) {
            System.err.println("GDALOpen failed - " + gdal.GetLastErrorNo());
            System.err.println(gdal.GetLastErrorMsg());
            System.exit(1);
        }
        //图像的列和行
        Driver hDriver = hDataset.GetDriver();
        int iXSize = hDataset.getRasterXSize();
        int iYSize = hDataset.getRasterYSize();
        Band band = hDataset.GetRasterBand(1);
        //图像六要素
        double[] dGeoTrans = hDataset.GetGeoTransform();
        //经纬度转行列号
        double dTemp = dGeoTrans[1] * dGeoTrans[5] - dGeoTrans[2] * dGeoTrans[4];
        int Xline = (int) ((dGeoTrans[5] * (lon - dGeoTrans[0]) - dGeoTrans[2] * (lat - dGeoTrans[3])) / dTemp + 0.5);
        int Yline = (int) ((dGeoTrans[1] * (lat - dGeoTrans[3]) - dGeoTrans[4] * (lon - dGeoTrans[0])) / dTemp + 0.5);
        //这里是DEM数据，所以声明一个int数组来存储，如果是其他数据类型，声明相应的类型即可
        int buf[] = new int[iXSize];
        //循环遍历取出像元值
        for (int i = 0; i < iYSize; i++) {
            band.ReadRaster(0, i, iXSize, 1, buf);    //读取一行数据
            // 下面是输出像元值
            for (int j = 0; j < iXSize; j++) {
                if (i == Yline && j == Xline) {
                    //System.out.println("海拔是："+buf[j]+"米");

                    altitude = buf[j];
                }
            }
        }
        hDataset.delete();
        // 可选
        gdal.GDALDestroyDriverManager();
        return altitude;
    }

    /**
     * 获取地图高程数据
     *
     * @return
     */
    public ArrayList GetMapRasterizedData(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp[] = path.split("\\\\");
        String fileName = temp[temp.length - 1];
        List<ElevationDataVO> elevationDataVOS = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        String fileName_tif = null;
        fileName_tif = path;
        gdal.AllRegister();
        Dataset hDataset = gdal.Open(fileName_tif, gdalconstConstants.GA_ReadOnly);
        if (hDataset == null) {
            logger.error("GDALOpen failed - " + gdal.GetLastErrorNo());
            logger.error(gdal.GetLastErrorMsg());
            System.exit(1);
        }
        Driver hDriver = hDataset.GetDriver();
        logger.info("Driver: " + hDriver.getShortName() + "/" + hDriver.getLongName());

        int iXSize = hDataset.getRasterXSize();
        int iYSize = hDataset.getRasterYSize();
        //图像六要素
        double[] dGeoTrans = hDataset.GetGeoTransform();
        logger.info("Size is " + iXSize + ", " + iYSize);
        Band band = hDataset.GetRasterBand(1);
        int buf[] = new int[iXSize];
        long startTime = System.currentTimeMillis();//获取开始时间
        for (int i = 0; i < iYSize; i++) {
            band.ReadRaster(0, i, iXSize, 1, buf);
            for (int j = 0; j < iXSize; j++) {
                //ElevationDataVO elevationDataVO = new ElevationDataVO();
                /*
                double Xp = dGeoTrans[0] + i * dGeoTrans[1] + j * dGeoTrans[2];
                double Yp = dGeoTrans[3] + i * dGeoTrans[4] + j * dGeoTrans[5];
                elevationDataVO.setXline(i);
                elevationDataVO.setYline(j);
                elevationDataVO.setLatitude(Xp);
                elevationDataVO.setLatitude(Yp);
                elevationDataVO.setAltitude(buf[j]);
                elevationDataVOS.add(elevationDataVO);
                */
                /*
                stringBuilder.append(i);
                stringBuilder.append("-");
                stringBuilder.append(j);
                stringBuilder.append("-");
                stringBuilder.append(buf[j]);
                stringBuilder.append(",");*/
                arrayList.add(i + "-" + "-" + j + "-" + buf[j]);
                //System.out.print("行号：" + i + " 列号：" + j + " 经度：" + Xp + " 维度：" + Yp + " 海拔：" + buf[j] + ",");
            }
        }
        long endTime = System.currentTimeMillis();//获取结束时间
        logger.info("【读取文件：" + fileName + " 成功！ 耗时" + (endTime - startTime) + "毫秒】");
        hDataset.delete();
        return arrayList;
    }

    /**
     *
     * @param lonL 左上角经度
     * @param latL 左上角纬度
     * @param lonR 右下角经度
     * @param latR 右下角纬度
     * @param path 文件路径
     * @return
     */
    public ArrayList GetMapRasterizedRangeData(double lonL, double latL, double lonR, double latR, String path) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp[] = path.split("\\\\");
        String fileName = temp[temp.length - 1];
        List<ElevationDataVO> elevationDataVOS = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        String fileName_tif = null;
        fileName_tif = path;
        gdal.AllRegister();
        Dataset hDataset = gdal.Open(fileName_tif, gdalconstConstants.GA_ReadOnly);
        if (hDataset == null) {
            logger.error("GDALOpen failed - " + gdal.GetLastErrorNo());
            logger.error(gdal.GetLastErrorMsg());
            System.exit(1);
        }
        Driver hDriver = hDataset.GetDriver();
        logger.info("Driver: " + hDriver.getShortName() + "/" + hDriver.getLongName());

        int iXSize = hDataset.getRasterXSize();
        int iYSize = hDataset.getRasterYSize();
        //图像六要素
        double[] dGeoTrans = hDataset.GetGeoTransform();
        logger.info("Size is " + iXSize + ", " + iYSize);
        //经纬度转行列号
        double dTemp = (double)(dGeoTrans[1] * dGeoTrans[5] - dGeoTrans[2] * dGeoTrans[4]);
        int xLineL = (int) ((dGeoTrans[5] * (lonL - dGeoTrans[0]) - dGeoTrans[2] * (latL - dGeoTrans[3])) / dTemp + 0.5);
        int yLineL = (int) ((dGeoTrans[1] * (latL - dGeoTrans[3]) - dGeoTrans[4] * (lonL- dGeoTrans[0])) / dTemp + 0.5);
        int xLineR = (int) ((dGeoTrans[5] * (lonR - dGeoTrans[0]) - dGeoTrans[2] * (latR - dGeoTrans[3])) / dTemp + 0.5);
        int yLineR = (int) ((dGeoTrans[1] * (latR - dGeoTrans[3]) - dGeoTrans[4] * (lonR - dGeoTrans[0])) / dTemp + 0.5);

        Band band = hDataset.GetRasterBand(1);
        int buf[] = new int[iXSize];
        long startTime = System.currentTimeMillis();//获取开始时间
        for (int i = yLineL; i < yLineR; i++) {
            band.ReadRaster(0, i, iXSize, 1, buf);
            for (int j = xLineL; j < xLineR; j++) {
                double Xp = dGeoTrans[0] + i * dGeoTrans[1] + j * dGeoTrans[2];
                double Yp = dGeoTrans[3] + i * dGeoTrans[4] + j * dGeoTrans[5];
                arrayList.add(i + "-" + "-" + j + "-" + buf[j]);
                //logger.info("行号：" + i + " 列号：" + j + " 经度：" + Xp + " 维度：" + Yp + " 海拔：" + buf[j] + ",");
                //logger.info("行号：" + i + " 列号：" + j +  " 海拔：" + buf[j] + ",");

            }
        }
        long endTime = System.currentTimeMillis();//获取结束时间
        logger.info("【读取文件：" + fileName + " 成功！ 耗时" + (endTime - startTime) + "毫秒】");
        hDataset.delete();
        return arrayList;
    }

    /**
     * 根据经纬度查询某一个范围内的园区
     * @param lng 中心的经度
     * @param lat 中心的纬度
     * @param distance 半径大小
     * @return
     */
    public void findNeighPosition(String lng,String lat,String distance){
        double longitude = Double.parseDouble(String.valueOf(108.999421));
        double latitude = Double.parseDouble(String.valueOf(34.308953));
        double dis = Double.parseDouble(String.valueOf(5.0));
        //先计算查询点的经纬度范围
        double r = 6371;//地球半径千米
        double dlng =  2*Math.asin(Math.sin(dis/(2*r))/Math.cos(latitude*Math.PI/180));
        dlng = dlng*180/Math.PI;//角度转为弧度
        double dlat = dis/r;
        dlat = dlat*180/Math.PI;
        double minlat =latitude-dlat;
        double maxlat = latitude+dlat;
        double minlon= longitude -dlng;
        double maxlon= longitude + dlng;
        System.out.println("左上角经纬度："+minlon+"  "+maxlat);
        System.out.println("右下角经纬度："+maxlon+"  "+minlat);
    }

    /**
     * 求两点之间的距离
     * @param lng1 A点经度
     * @param lat1 A点纬度
     * @param lng2 B点经度
     * @param lat2 B点纬度
     * @return 两点距离
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double EARTH_RADIUS = 6371;
        double radiansAX = Math.toRadians(lng1); // A经弧度
        double radiansAY = Math.toRadians(lat1); // A纬弧度
        double radiansBX = Math.toRadians(lng2); // B经弧度
        double radiansBY = Math.toRadians(lat2); // B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
        double acos = Math.acos(cos); // 反余弦值
//		System.out.println("-------"+EARTH_RADIUS * acos);
        return EARTH_RADIUS * acos; // 最终结果

    }

    /**
     * 获取指定范围内的高程数据
     */
        /*
        Public String GetMultifyElevation(String positions) {

        positions = "116.0,40.166667,116.25,40.0";//模拟传入的范围（left，top，right，bottom）
        double dProjX, dProjY, dProjX1, dProjY1;
        String[] arr = positions.split(",");
        dProjX = arr[0];
        dProjY = float.Parse(arr[1]);
        dProjX1 = float.Parse(arr[2]);
        dProjY1 = float.Parse(arr[3]);


        String strFilePath = "C:\\webservices\\data\\srtm\\chinaclip.tif";//读取的文件路径
        String testPath = "C:\\webservices\\data\\chinaclip.tif";//要写的文件路径
        String elvate = "";
        gdal.AllRegister();
        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8", "YES");    //支持中文
        Dataset ds = gdal.Open(strFilePath, Access.GA_ReadOnly);
        try {
            Band Band = ds.GetRasterBand(1);


            //获取图像的尺寸
            int width = Band.getXSize();
            int height = Band.getYSize();


            //获取坐标变换系数
            double[] adfGeoTransform = new double[6];
            ds.GetGeoTransform(adfGeoTransform);
            //获取行列号
            double dTemp = adfGeoTransform[1] * adfGeoTransform[5] - adfGeoTransform[2] * adfGeoTransform[4];
            double dCol = 0.0, dRow = 0.0;


            dCol = (adfGeoTransform[5] * (dProjX - adfGeoTransform[0]) -
                    adfGeoTransform[2] * (dProjY - adfGeoTransform[3])) / dTemp + 0.5;
            dRow = (adfGeoTransform[1] * (dProjY - adfGeoTransform[3]) -
                    adfGeoTransform[4] * (dProjX - adfGeoTransform[0])) / dTemp + 0.5;
            int dc = (int) dCol;
            int dr = (int) dRow;


            dCol = (adfGeoTransform[5] * (dProjX1 - adfGeoTransform[0]) -
                    adfGeoTransform[2] * (dProjY1 - adfGeoTransform[3])) / dTemp + 0.5;
            dRow = (adfGeoTransform[1] * (dProjY1 - adfGeoTransform[3]) -
                    adfGeoTransform[4] * (dProjX1 - adfGeoTransform[0])) / dTemp + 0.5;
            int dc1 = (int) dCol;
            int dr1 = (int) dRow;
            int fx = dc - dc1;
            int fy = dr - dr1;
            if (fx < 0)
                fx = -fx;
            if (fy < 0)
                fy = -fy;


            //获取DEM数值到一维数组
            float[] data = new float[fx * fy];
            //读取感觉可以去掉
            CPLErr err = Band.ReadRaster(dc, dr, fx, fy, data, fx, fy, 0, 0);
            //裁切
            int[] bandmap = {1};
            DataType DT = DataType.GDT_CFloat32;
            Dataset dataset = ds.GetDriver().Create(testPath, fx, fy, 1, DT, null);
            //写入
            dataset.WriteRaster(0, 0, fx, fy, data, fx, fy, 1, bandmap, 0, 0, 0);

            Band bd = dataset.GetRasterBand(1);
            //获取最小最大值
            double[] lim = new double[2];
            bd.ComputeRasterMinMax(lim, 1);   //最后的ApproxOK设为1，设为0效果一样
            float _Min = (float) lim[0];
            float _Max = (float) lim[1];

            bd.ReadRaster(0, 0, fx, fy, data, fx, fy, 0, 0);
            int c = 0;
            int x1 = -1, y1 = -1, x2 = -1, y2 = -1;
            //遍历获取行列值
            for (int i = 0; i < fx; i++) {
                if (x2 != -1 && y2 != -1 && x1 != -1 && y1 != -1) {
                            goto conmehere;//为了提高效率使用goto语句
                }
                for (int j = 0; j < fy; j++) {
                    if (x2 != -1 && y2 != -1 && x1 != -1 && y1 != -1) {
                                goto conmehere;
                    }
                    if (_Min == data[c++]) {
                        x1 = i;
                        y1 = j;
                    }
                    if (_Max == data[c]) {
                        x2 = i;
                        y2 = j;
                    }
                }
            }
            conmehere:
            //adfGeoTransform[0]  左上角x坐标
            //adfGeoTransform[1]  东西方向分辨率
            //adfGeoTransform[2]  旋转角度, 0表示图像 "北方朝上"
            //adfGeoTransform[3]  左上角y坐标
            //adfGeoTransform[4]  旋转角度, 0表示图像 "北方朝上"
            //adfGeoTransform[5]  南北方向分辨率
            Band.delete();
            double geox1 = dProjX + x1 * adfGeoTransform[1] + y1 * adfGeoTransform[2];
            double geoy1 = dProjY + x1 * adfGeoTransform[4] + y1 * adfGeoTransform[5];
            double geox2 = dProjX + x2 * adfGeoTransform[1] + y2 * adfGeoTransform[2];
            double geoy2 = dProjY + x2 * adfGeoTransform[4] + y2 * adfGeoTransform[5];


            elvate = geox1 + "," + geoy1 + "," + _Min + ";" + geox2 + "," + geoy2 + "," + _Max;
            return elvate;
        } catch (Exception ex) {
            return "";
        }
    }*/
}
