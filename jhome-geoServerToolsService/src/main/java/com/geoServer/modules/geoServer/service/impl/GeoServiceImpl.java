package com.geoServer.modules.geoServer.service.impl;


import com.bracket.common.register.EncryptHelper;
import com.geoServer.modules.geoServer.service.GeoService;
import org.apache.log4j.Logger;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.Query;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.util.factory.GeoTools;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.spatial.DWithin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.geotools.data.postgis.PostgisNGDataStoreFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class GeoServiceImpl implements GeoService {
    private final static Logger logger = Logger.getLogger(GeoServiceImpl.class.getName());
    //WFS服务查询地址
    private static final String GET_CAPABILITIES = "http://localhost:9090/geoserver/wfs?service=WFS&version=1.0.0&request=GetCapabilities";
    //private static final String GET_CAPABILITIES = "http://localhost:9090/geoserver/kqgeodx/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetFeatureInfo&FORMAT=image/jpeg&TRANSPARENT=true&QUERY_LAYERS=kqgeodx:LTX&STYLES&LAYERS=kqgeodx:LTX&INFO_FORMAT=application/json&FEATURE_COUNT=50&X=50&Y=50&SRS=EPSG:4326&WIDTH=101&HEIGHT=101&BBOX=109.36178684409242,34.04373764904449,109.37045574362855,34.05240654858062";
    //private static final String GET_CAPABILITIES = "http://localhost:9090/geoserver/kqgeodx/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetFeatureInfo&FORMAT=image/jpeg&TRANSPARENT=true&QUERY_LAYERS=kqgeodx:LTX&STYLES&LAYERS=kqgeodx:LTX&INFO_FORMAT=application/json&FEATURE_COUNT=50&X=50&Y=50&SRS=EPSG:4326&WIDTH=101&HEIGHT=101&BBOX=109.36178684409242,34.04373764904449,109.37045574362855,34.05240654858062";
    //private static final String GET_CAPABILITIES = "http://localhost:9090/geoserver/kqgeodx/wms?SERVICE=WMS&VERSION=1.1.1&REQUEST=GetFeatureInfo";

    private static final String dbtype="postgis";//请求类型
    private static final String host="192.168.0.111";//请求地址
    private static final String port="5432";//端口号
    private static final String dataBaseName="gis";//需要连接的数据库
    private static final String ataBaseSchemas="public";//架构
    private static final String userName="postgres";//数据库用户名
    private static final String password="postgres1232";//数据库密码
    private static final String tableName="lantianxian";//表名称

    @Override
    public String getGeoService(String declareCode) {
        //getDbPostGis();
        getBufferGeo();
        return "";
    }

    public   void getDbPostGis() {
        Map<String, Object> params = new HashMap<String, Object>();
        DataStore pgDatastore=null;
        params.put(PostgisNGDataStoreFactory.DBTYPE.key, dbtype); //需要连接何种数据库，postgis or mysql
        params.put(PostgisNGDataStoreFactory.HOST.key, host);//ip地址
        params.put(PostgisNGDataStoreFactory.PORT.key, new Integer(port));//端口号
        params.put(PostgisNGDataStoreFactory.DATABASE.key, dataBaseName);//需要连接的数据库
        params.put(PostgisNGDataStoreFactory.SCHEMA.key, ataBaseSchemas);//架构
        params.put(PostgisNGDataStoreFactory.USER.key, userName);//需要连接数据库的名称
        params.put(PostgisNGDataStoreFactory.PASSWD.key, password);//数据库的密码

        SimpleFeatureCollection fcollection=null;
        try {
            //获取存储空间
            pgDatastore = DataStoreFinder.getDataStore(params);
            //根据表名获取source
            SimpleFeatureSource fSource=pgDatastore.getFeatureSource(tableName);
            if (pgDatastore != null) {
                System.out.println("系统连接到位于：" + host + "的空间数据库" + dataBaseName
                        + "成功！");
                fcollection=fSource.getFeatures();
            } else {
                System.out.println("系统连接到位于：" + host + "的空间数据库" + dataBaseName
                        + "失败！请检查相关参数");
            }
            //查询结果集
            FeatureIterator<SimpleFeature> featureIterator = fcollection.features();
            System.out.println("==========数据条数：" + fcollection.size());
            while (featureIterator.hasNext()) {
                SimpleFeature feature = featureIterator.next();
                System.out.println("==========输出结果：" + feature.getProperties());

//                System.out.println(feature.getID());
//                System.out.println(feature.getValue());
                //System.out.println(feature.getAttribute("rid").toString()+feature.getAttribute("rast").toString()+feature.getAttribute("tt"));
                //System.out.println(feature.getAttribute("rid").toString());
            }


//            FilterFactory2 filterFactory = CommonFactoryFinder.getFilterFactory2(null);
//            Filter filter = filterFactory.equals(filterFactory.literal("natural"), filterFactory.literal("coastline"));
//            SimpleFeatureCollection features2 = fSource.getFeatures(filter);
//            System.out.println("Features found after filtering: " + !features2.isEmpty()); //SEEMS TO BE ALWAYS EMPTY
//
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("系统连接到位于：" + host + "的空间数据库" + dataBaseName
                    + "失败！请检查相关参数");
        }

    }
//    public static void main(String[] args) {
//        //调用方法
//        SimpleFeatureCollection  featureColls=postgis.connAndgetCollection("postgis", "localhost", "5432", "sqlView", "postgres", "postgres","roa_4m");
//        SimpleFeatureIterator itertor = featureColls.features();
//        //循环读取feature，itertor.hasNext()表示游标下一个是否有数据，有返回ture,否则为false
//        while (itertor.hasNext())
//        {
//            //获取每一个要素
//            SimpleFeature feature = itertor.next();
//            System.out.println(feature.getAttribute("roa_4m_id"));
//        }
//
//    }


    /**
     * * 点周边查询
     * * @return
     */
    public static void getBufferGeo() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", GET_CAPABILITIES);
//        params.put(PostgisNGDataStoreFactory.DBTYPE.key, dbtype); //需要连接何种数据库，postgis or mysql
//        params.put(PostgisNGDataStoreFactory.HOST.key, host);//ip地址
//        params.put(PostgisNGDataStoreFactory.PORT.key, new Integer(port));//端口号
//        params.put(PostgisNGDataStoreFactory.DATABASE.key, database);//需要连接的数据库
//        params.put(PostgisNGDataStoreFactory.SCHEMA.key, "public");//架构
//        params.put(PostgisNGDataStoreFactory.USER.key, "");//需要连接数据库的名称
//        params.put(PostgisNGDataStoreFactory.PASSWD.key, "geoserver");//数据库的密码
        FeatureCollection<SimpleFeatureType, SimpleFeature> features = null;
        try {
            //创建连接
            DataStore data = DataStoreFinder.getDataStore(params);
            //获取所有的图层
            String typeNames[] = data.getTypeNames();
            //指定要查询的图层（根据自己情况修改）
            String typeName = typeNames[7];
            SimpleFeatureType schema = data.getSchema(typeName);
            FeatureSource<SimpleFeatureType, SimpleFeature> source = data.getFeatureSource(typeName);
            //执行查询
            String geomName = schema.getGeometryDescriptor().getLocalName();
            FilterFactory2 filterFactory = CommonFactoryFinder.getFilterFactory2(GeoTools.getDefaultHints());
            GeometryFactory gf = new GeometryFactory();
            //缓冲查询标记的点位
            Point ls = gf.createPoint(new Coordinate(119.9436, 31.8056));
            //缓冲区查询( 1km 为1*0.01 )
            DWithin filter = filterFactory.dwithin(filterFactory.property(geomName), filterFactory.literal(ls), 0.05, "km");
            //输出全部字段
            Query query = new Query(typeName, filter);
            features = source.getFeatures(query);
            //查询结果集
            FeatureIterator<SimpleFeature> featureIterator = features.features();
            System.out.println("==========数据条数：" + features.size());
            while (featureIterator.hasNext()) {
                SimpleFeature feature = featureIterator.next();
                System.out.println("==========输出结果：" + feature.getProperties());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (features != null) {
                features.features().close();
            }
        }
    }

    public void geturl() {
       /* // 请求wms服务查询要素(点查询)
        String wmsUrl = "http://192.168.0.1:8080/gisserver/zg/wms";
        HashMap<String, String> paramsMap = new HashMap<>();
        // 查询服务的类型
        paramsMap.put("SERVICE", "WMS");
        // 查询服务的版本号
        paramsMap.put("VERSION", "1.1.0");
        // 请求的方法名称 -- 当前为获取要素信息
        paramsMap.put("REQUEST", "GetFeatureInfo");
        // 查询结果显示数量，默认为1
        paramsMap.put("FEATURE_COUNT", "50");
        // 图层的渲染样式，默认为""
        paramsMap.put("STYLES", "");
        // 待查询的图层名称集合 用逗号分隔
        paramsMap.put("QUERY_LAYERS", "zgyd");
        // 地图上的可视图层名称集合 用逗号分隔
        paramsMap.put("LAYERS", "zgyd");
        // 查询结果输出格式 -- 当前指定为json格式
        paramsMap.put("INFO_FORMAT", "application/json");
        // 地图的坐标系参考
        paramsMap.put("SRS", "EPSG:2383");
        // 查询的点坐标 -- 值为点击的屏幕像素值 差异范围 用于生成下面的边界范围
        paramsMap.put("X", "50");
        paramsMap.put("Y", "50");
        // 地图范围的边界 格式为坐标参考坐标 minx ， miny ，maxx ，maxy 用逗号分隔
        // 计算屏幕坐标 范围
        Point minPoint = new Point(point.getX() - 50, point.getY() + 50);
        Point maxPoint = new Point(point.getX() + 50, point.getY() - 50);

        Point minEPoint = mMapView.toMapPoint(minPoint);
        Point maxEPoint = mMapView.toMapPoint(maxPoint);
        paramsMap.put("BBOX", minEPoint.getX() + "," + minEPoint.getY() + "," + maxEPoint.getX() + "," + maxEPoint.getY());
        // openlayers中底层代码中的常量 -- 这里模拟-写死了（表示不清楚为啥是101）
        paramsMap.put("WIDTH", "101");
        paramsMap.put("HEIGHT", "101");

    */
    }



}

