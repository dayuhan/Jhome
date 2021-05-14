    //组织集合
    List<orgs> orgs=new Array();
    //Feign跨网络访问返回对象
    JSONObject object=XXXX.XXX();
    //第一、取出返回结果对象中 data中的数据，转换成jsonArray对象数组
    JSONArray arrays=object.getJSONArray("data");//data里面是集合的情况
    JSONObject objects=object.getJSONObject("data");//data里面单纯是对象的情况
    String str=object.getObject("data",String.class);//data里面单纯是字符串
    //第二、将arrays 对象数组转换成JSON字符串
    String jsonArrays=JSON.toJSONString(arrays);
    //第三、把字符串映射成对应的对象集合
    orgs=JSONObject.ParseArray(jsonArrays,Org.class);
    
 ##### 1.List转JSONArray    
    List<T> list = new ArrayList<T>();
    JSONArray array= JSONArray.parseArray(JSON.toJSONString(list))；
 
#####2.JSONArray转List    
    JSONArray array = new JSONArray();
    List<EventColAttr> list = JSONObject.parseArray(array.toJSONString(), EventColAttr.class);
  
#####3.String转JSONArray    
    String st = "[{name:Tim,age:25,sex:male},{name:Tom,age:28,sex:male},{name:Lily,age:15,sex:female}]";
    JSONArray tableData = JSONArray.parseArray(st); 