package com.geoServer.modules.geoServer.service.impl;


import com.bracket.common.register.EncryptHelper;
import com.geoServer.modules.geoServer.service.GeoService;
import org.apache.log4j.Logger;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.Query;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.util.factory.GeoTools;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.spatial.DWithin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class GeoServiceImpl implements GeoService {
    private final static Logger logger = Logger.getLogger(GeoServiceImpl.class.getName());
    //WFS服务查询地址
    private static final String GET_CAPABILITIES = "http://localhost:9090/geoserver/wfs?service=WFS&version=1.0.0&request=GetCapabilities";

    @Override
    public String getGeoService(String declareCode) {
        getBufferGeo();
        return "";
    }

    /**
     * * 点周边查询
     * * @return
     */
    public static void getBufferGeo() {
        Map<String, String> connectionParameters = new HashMap<String, String>();
        connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", GET_CAPABILITIES);
        FeatureCollection<SimpleFeatureType, SimpleFeature> features = null;
        try {
            //创建连接
            DataStore data = DataStoreFinder.getDataStore(connectionParameters);
            //获取所有的图层
            String typeNames[] = data.getTypeNames();
            //指定要查询的图层（根据自己情况修改）
            String typeName = typeNames[12];
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

}

