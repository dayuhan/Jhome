package com.bracket.common.ToolKit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONUtils {
	/**
	 * Bean对象转JSON
	 *
	 * @param object
	 * @param dataFormatString
	 * @return
	 */
	public static String beanToJson(Object object, String dataFormatString) {
		if (object != null) {
			if (StringUtils.isEmpty(dataFormatString)) {
				return JSONObject.toJSONString(object);
			}
			return JSON.toJSONStringWithDateFormat(object, dataFormatString);
		} else {
			return null;
		}
	}

	/**
	 * Bean对象转JSON
	 *
	 * @param object
	 * @return
	 */
	public static String beanToJson(Object object) {
		if (object != null) {
			return JSON.toJSONString(object);
		} else {
			return null;
		}
	}

	/**
	 * String转JSON字符串
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static String stringToJsonByFastjson(String key, String value) {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		return beanToJson(map, null);
	}

	/**
	 * 将json字符串转换成对象
	 *
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object jsonToBean(String json, Object clazz) {
		if (StringUtils.isEmpty(json) || clazz == null) {
			return null;
		}
		return JSON.parseObject(json, clazz.getClass());
	}

	/**
	 * json字符串转map
	 *
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		return JSON.parseObject(json, Map.class);
	}



	//一个静态类，将json对象，转化为java
	public static Object jsonToObject(JSONObject jsonObject, Class c) throws IllegalAccessException, InstantiationException {
		Object obj=c.newInstance();
		Iterator it=jsonObject.keySet().iterator();
		Field[] fields=c.getDeclaredFields();
		while(it.hasNext()) {
			String key = (String) it.next();
			//判断字段
			for (Field field : fields) {
				if (field.getName().equals(key)) {
					// System.out.println("获取到的所有字段"+key);
					useSetter(c, field, jsonObject,obj);
					break;
				}
			}
		}
		return obj;
	}


	//调用该类的setter方法
	private static void useSetter(Class c, Field field,JSONObject jsonObject,Object obj){
		//获取字段类型以及字段名称
		Class fieldType=field.getType();
		String fieldName=field.getName();
		String methodName=toMethodName(fieldName);
		//System.out.println(methodName+"类型"+fieldType);
		//调用方法
		try {
			Method method=c.getMethod(methodName,fieldType);
			//  System.out.println(method);
			method.invoke(obj,jsonObject.get(fieldName));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//将fieldName转化为方法名称
	private static String toMethodName(String name){
		String methodName="set"+name.substring(0,1).toUpperCase()+name.substring(1);
		return methodName;
	}

}