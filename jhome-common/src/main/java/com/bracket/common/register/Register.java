package com.bracket.common.register;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Register {
	/**
	 * 读锁
	 */
	private static String deviceId = "";
	private static String version = "v2.0.2";
	private static String timekey = "sfl38rkv989DSA63"; // 加密开机时间的密钥
	public static String _registerFileName = "Register.fly";
	private static String _licenseFileName = "license.fly";
	private static String _tempFileName = "temp.fly";
	private static String _errorFileName = "error.fly";
	private static String _hardFileName = "hard.fly";
	private static String _slaveHostFileName = "host.fly";
	private static String _baseDirectory;
	private static String productCodeKey = "Lnqf1s1lMeXsgV0T";// 加密产品编码的秘钥 gtazlbzxtv1.0
	//private static String productCodeKey = "87vmIyTHTNVFz6kKY5icmGOkSI5xaMQYBN4b6tJunR0wmFlJXsGf3+24ae6H89mr";// 加密产品编码的秘钥
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 正式发布域名 http://onlineregister.gtafe.com/
	private static String olcheckurl = "http://onlineregister.gtafe.com:5168/onlineCheck/olcheck2";// 在线验证的地址

	/**
	 * 获取云服务地址
	 */
	private static String getSlaveURL() {
		try {
			String host = FileOperate.ReadInfo(_slaveHostFileName, false);
			if (DotNetToJavaStringHelper.isNullOrWhiteSpace(host) == false) {
				String url = host + "/onlineCheck/olcheck2";
				return url;
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}

	public static boolean SetDeviceID(String DeviceID) {
		deviceId = DeviceID;
		return true;
	}

	public static boolean cacheFlag = false;

	/**
	 * 构造函数
	 */
	static {
		_baseDirectory = FileOperate.baseDirectory();
		/*
		 * if (!FileOperate.isExist(Register._registerFileName,false)) {
		 * FileOperate.WriteInfo(Register._registerFileName, EncryptHelper.AESEncrypt(
		 * dateTimeFormat.format(new Date()), timekey), false,false); }
		 */
	}

	public static boolean Check(String productCode) throws Throwable {
		productCode = getRealProductCode(productCode);
		System.out.println("校验");
		FileOperate.WriteLog(_errorFileName, " \n校验 ");
		synchronized (Register.class) {
			if (!(new java.io.File(_baseDirectory + _licenseFileName)).isFile()
					|| !(new java.io.File(_baseDirectory + _tempFileName)).isFile()) {
				// errorMsg = "找不到指定的授权文件";
				FileOperate.WriteLog(_errorFileName, "  \n找不到指定授权文件   ");
				System.out.println("找不到指定授权文件");
				return false;
			}
			String registerCode = FileOperate.ReadInfo(_tempFileName, false);
			if (registerCode.length() != 16) {
				System.out.println("注册码长度错误");
				return false;
			}
			String grantCode = FileOperate.ReadInfo(_licenseFileName, false);

			// 获取申请码

			FileOperate.WriteLog(_errorFileName, " \n产品码： " + productCode);
			FileOperate.WriteLog(_errorFileName, " \n授权码： " + grantCode);
			FileOperate.WriteLog(_errorFileName, " \n注册码： " + registerCode);
			boolean flag = onlineCheck(productCode, grantCode, registerCode);
			FileOperate.WriteLog(_errorFileName, " \n校验结果： " + flag);
			Register.cacheFlag = flag;
			return Register.cacheFlag;

		}
	}

	private static boolean onlineCheck(String productCode, String grantCode, String registerCode) throws Throwable {
		System.out.println("校验时的内部时间：" + TimeCheck.standToday);
		String applyId = Register.GetCode(productCode);
		FileOperate.WriteLog(_errorFileName, " \n申请码： " + applyId);
		System.out.println("申请码：" + applyId);
		boolean checked = false;
		// 云，在线获取校验码
		String slaveURL = getSlaveURL();
		String checkAesCode = "error";
		System.out.println("从服务器校验地址：[" + slaveURL + "]");
		if ("".equals(slaveURL) == false) {
			checkAesCode = getCheckCode(slaveURL + "?applyId=" + applyId + "&registerCode=" + registerCode);
			// 如果网络连接成功，获取成功(验证成功)
			if (!"error".equals(checkAesCode)) {
				checked = checkOnlineResult(productCode, grantCode, applyId, checkAesCode);
				System.out.println("从服务器校验结果：" + checked);
				if (checked) {
					return checked;
				}
			}
		}
		checkAesCode = getCheckCode(olcheckurl + "?applyId=" + applyId + "&registerCode=" + registerCode);
		// 主服务器，如果网络连接成功，获取成功(验证成功)
		if (!"error".equals(checkAesCode)) {
			checked = checkOnlineResult(productCode, grantCode, applyId, checkAesCode);
			System.out.println("主服务器校验结果：" + checked);
			return checked;
		}
		if (checked == false) {
			System.out.println("本地校验");
			System.out.println("Register.cacheFlag:" + Register.cacheFlag);
			if (Register.cacheFlag) {
				return true;
			}
			// 则调用本地验证
			FileOperate.WriteLog(_errorFileName, " \n本地校验 ");
			try {
				checked = Check(productCode, grantCode, registerCode);
			} catch (Exception e) {
				e.printStackTrace();
				FileOperate.WriteInfo(_errorFileName, "异常捕获： " + e.getMessage(), true, false);
				return false;
			}
		}
		return checked;
	}

	/**
	 * 校验返回的加密信息
	 *
	 * @param productCode
	 *            产品编码
	 * @param registerCode
	 *            注册码
	 * @param grantCode
	 *            授权码
	 * @param checkAesCode
	 *            在线校验返回值
	 * @return boolean
	 * @throws Throwable
	 */
	private static boolean checkOnlineResult(String productCode, String grantCode, String applyId, String checkAesCode)
			throws ParseException {
		TimeCheck.init(productCode, timekey);
		System.out.println("在线校验 ");
		FileOperate.WriteLog(_errorFileName, " \n在线校验 ");
		FileOperate.WriteLog(_errorFileName, " \n checkAesCode： " + checkAesCode);
		System.out.println("checkAesCode: " + checkAesCode);
		// 如果校验码为theGrantCodeIsNotFounds,则验证为无效
		if ("theGrantCodeIsNotFounds".equals(checkAesCode)) {
			FileOperate.WriteLog(_errorFileName, " \n theGrantCodeIsNotFounds ");
			// 返回false
			return false;
		} // 如果校验码为theGrantCodeIsExpired,则验证过期
		if ("theGrantCodeIsExpired".equals(checkAesCode)) {
			// 返回false
			FileOperate.WriteLog(_errorFileName, " \n theGrantCodeIsExpired ");
			return false;
		}
		try {

			// 解密校验码
			String checkCode = EncryptHelper.AESDecrypt(checkAesCode, applyId.substring(0, 16));

			System.out.println("checkCode: " + checkCode);
			FileOperate.WriteLog(_errorFileName, "\n checkCode：" + checkCode);
			// //解析校验码
			String[] infos = checkCode.split("_");

			System.out.println("checkCode's infos: " + Arrays.toString(infos));
			FileOperate.WriteLog(_errorFileName, "\n checkCodeSplitInfo：" + Arrays.toString(infos));
			if (infos.length != 4) {
				FileOperate.WriteLog(_errorFileName, " \n 解密后数组长度错误 ");
				// 解密后的数组长度
				return false;
			}
			FileOperate.WriteLog(_errorFileName, "\n grantCode：" + grantCode + "infosGrantCode" + infos[1]);
			// 返回的授权码与读取的授权码不同则失败
			if (!infos[1].equals(grantCode)) {
				FileOperate.WriteLog(_errorFileName, " \n 返回的授权码与读取的授权码不同 ");
				return false;
			}
			// 输出

			// 在线时间
			TimeCheck.standToday = dateTimeFormat.parse(infos[2]);
			FileOperate.WriteInfo(_slaveHostFileName, infos[3], false, false);
			System.out.println("服务器时间 :" + infos[2]);
			FileOperate.WriteLog(_errorFileName, "\n 服务器时间 :" + infos[2]);
			FileOperate.WriteLog(_errorFileName, "\n 修改的standToday时间 :" + TimeCheck.standToday);
			// 如果是第一次则启动内部计时器
			TimeCheck.startAddup();
			// 同步到文件
			TimeCheck.updateTime(0);
			return true;
		} catch (Exception e) {
			FileOperate.WriteLog(_errorFileName, "\n 解密異常:" + checkAesCode);
			// TODO: handle exception
			return false;
		}
	}

	// 关于页面输出信息
	public static Map<String, String> getInfo(String productCode) {
		if (!(new java.io.File(_baseDirectory + _licenseFileName)).isFile()
				|| !(new java.io.File(_baseDirectory + _tempFileName)).isFile()
				|| !(new java.io.File(_baseDirectory + _licenseFileName)).isFile()) {
			// errorMsg = "找不到指定的授权文件";
			return null;
		}
		productCode = getRealProductCode(productCode);
		String applyId = Register.GetCode(productCode);// 根据产品码生成申请码。
		String registerCode = FileOperate.ReadInfo(_tempFileName, false);
		String grantCode = FileOperate.ReadInfo(_licenseFileName, false);
		String[] infos = EncryptHelper.AESDecrypt(grantCode, registerCode).split("_");
		String start = infos[1];
		String end = infos[2];
		Map<String, String> minfo = new HashMap<String, String>();
		minfo.put("registerCode", registerCode);
		minfo.put("grantCode", grantCode);
		minfo.put("start", start);
		minfo.put("end", end);
		minfo.put("applyId", applyId);
		return minfo;
	}

	/**
	 * @param productCode
	 *            产品编码
	 * @param registerCode
	 *            注册码
	 * @param grantCode
	 *            授权码
	 * @return
	 * @throws Throwable
	 */

	private static boolean Check(String productCode, String grantCode, String registerCode) throws Throwable {
		TimeCheck.init(productCode, timekey);
		System.out.println("本地校验的内部时间 :" + dateTimeFormat.format(TimeCheck.standToday));
		FileOperate.WriteLog(_errorFileName, "\n 本地校验的内部时间 :" + dateFormat.format(TimeCheck.standToday));
		try {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(TimeCheck.standToday);
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);
			Date today = cal1.getTime();
			System.out.println("today:" + dateFormat.format(today));
			System.out.println("enter cal1.getTime()");

			String getCode = GetCode(productCode);
			if (getCode.equals("")) {
				return false;
			}
			String[] infos = EncryptHelper.AESDecrypt(grantCode, registerCode).split("_", -1);
			System.out.println("getCode:" + getCode);
			System.out.println("得到infos：" + Arrays.toString(infos));
			FileOperate.WriteLog(_errorFileName, "\n得到getCode：" + getCode);
			FileOperate.WriteLog(_errorFileName, "\n得到infos：" + Arrays.toString(infos));
			if (!getCode.equals(infos[0])) {
				// errorMsg = "不是指定的授权服务器";
				System.out.println("not server");
				FileOperate.WriteLog(_errorFileName, " \n not server ");
				return false;
			}
			if (!infos[1].equals("1900-01-01") && dateFormat.parse(infos[1]).getTime() > today.getTime()) {
				// errorMsg = "使用限期已到";
				System.out.println("time is over begin");
				FileOperate.WriteLog(_errorFileName, " \n time is over begin ");
				return false;
			}
			if (!infos[2].equals("1900-01-01") && (dateFormat.parse(infos[2]).getTime() < today.getTime())) {
				// errorMsg = "使用限期已到";
				System.out.println("time is over end");
				FileOperate.WriteLog(_errorFileName, " \n time is over end");
				return false;
			}
			String aescode = getRealProductCode(infos[4]);
			if (!productCode.equals(aescode)) {
				// errorMsg = "不是指定的注册产品";
				System.out.println("productCode is error");
				FileOperate.WriteLog(_errorFileName, " \n productCode is error");
				return false;
			}
			if (!infos[1].equals("1900-01-01")) {
				/*
				 * if (!TimeCheck.check(dateFormat.parse(infos[2]))) {
				 * System.out.println("hideTime is over"); return false; }
				 */
				TimeCheck.startAddup();
				System.out.println("enter init");
				// if(!TimeCheck.isStarted.get()) TimeCheck.updateTime(0);
				System.out.println("enter updatetime");
				// TimeCheck.startAddup();
			}

			return true;
		} catch (Exception e2) {
			FileOperate.WriteLog(_errorFileName, " \n exception:" + e2);
			return false;
		}
	}

	synchronized public static boolean Registe(String productCode, String registerCode, String grantCode)
			throws Throwable {
		if (Register.cacheFlag) {
			return true;
		}
		productCode = getRealProductCode(productCode);
		synchronized (Register.class) {
			TimeCheck.init(productCode, timekey);
			TimeCheck.initDateTime();
			try {
				if (DotNetToJavaStringHelper.isNullOrEmpty(productCode)
						|| DotNetToJavaStringHelper.isNullOrEmpty(registerCode)
						|| DotNetToJavaStringHelper.isNullOrEmpty(grantCode)) {
					return false;
				}
				System.out.println("注册检验前：" + Register.cacheFlag);
				Register.cacheFlag = onlineCheck(productCode, grantCode, registerCode);
				System.out.println("注册检验后：" + Register.cacheFlag);
				if (Register.cacheFlag) {
					FileOperate.WriteInfo(_tempFileName, registerCode, false, false);
					FileOperate.WriteInfo(_licenseFileName, grantCode, false, false);
					Register.cacheFlag = true;
					return true;
				} else
					return false;
			} catch (Exception e) {
				e.printStackTrace();
				FileOperate.WriteLog(_errorFileName, "注册异常：" + e.getMessage());
				FileOperate.WriteInfo(_errorFileName, "异常捕获： " + e.getMessage(), true, false);
			}
			return false;
		}
	}

	public static String GetCode(String productCode) {
		productCode = getRealProductCode(productCode);

		String hardCode = "";
		String cpuId = "";
		String hardId = "";
		try {
			cpuId = MachineHelper.GetCPUNumber();
		} catch (Exception e) {
			FileOperate.WriteInfo(_errorFileName, "获取cpuId错误：deviceId: " + deviceId, true, false);
			return "";
		}
		try {
			hardId = MachineHelper.GetHardDiskNumber();
		} catch (Exception e2) {
			FileOperate.WriteInfo(_errorFileName, "获取HardDisk错误：deviceId: " + deviceId, true, false);
			return "";
		}
		if (cpuId.equals("") && hardId.equals("")) {
			FileOperate.WriteInfo(_errorFileName,
					"获取硬件信息错误：deviceId: " + deviceId + ",cpuId:" + cpuId + ",hardId:" + hardId, true, false);
			return "";
		}
		if ((new java.io.File(_baseDirectory + _hardFileName)).isFile()) {
			hardCode = FileOperate.ReadInfo(_hardFileName, false);
			String[] hardArr = hardCode.split("_", -1);
			if (hardArr.length != 2) {
				FileOperate.WriteInfo(_errorFileName, "array not equal 2 ", true, false);
				hardCode = "";
			} else {
				if (!cpuId.equals(hardArr[0])) {
					FileOperate.WriteInfo(_errorFileName, "cpu not matching ", true, false);
					hardCode = "";
				}
				if (!hardId.equals(hardArr[1])) {
					FileOperate.WriteInfo(_errorFileName, "harddisk not matching ", true, false);
					hardCode = "";
				}
			}
		} else {
			hardCode = "";
			FileOperate.WriteInfo(_errorFileName, "not exist hardfile", true, false);
		}
		if (hardCode.equals("")) {
			hardCode = cpuId + "_" + hardId;
			FileOperate.WriteInfo(_hardFileName, hardCode, false, false);
		}
		String c = EncryptHelper.MD5Encrypt32(hardCode + "_" + MachineHelper.GetMac() + "_" + productCode);
		return c;
	}

	/**
	 * 在线验证，传参获取数据
	 *
	 * @param getUrl
	 *            验证地址
	 * @return 接口返回的校验码checkCode
	 */
	private static String getCheckCode(String getUrl) {
		System.out.println("发送请求：" + getUrl);
		BufferedReader in = null;
		StringBuffer result = null;
		try {
			URI uri = new URI(getUrl);
			URL url = uri.toURL();
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Charset", "utf-8");
			connection.connect();
			result = new StringBuffer();
			// 读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			System.out.println("无法连接到：" + getUrl.toString());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return "error";
	}

	/**
	 * 从加密信息中获取大版本的产品编号
	 *
	 * @param getUrl
	 *            验证地址
	 * @return 接口返回的校验码checkCode
	 */
	private static String getRealProductCode(String aesProductCode) {
		String productCode = aesProductCode;
		try {
			String decode = EncryptHelper.AESDecrypt(aesProductCode, productCodeKey);
			productCode = decode.split("_")[1];
		} catch (Exception e) {
			FileOperate.WriteInfo(_errorFileName, aesProductCode+":获取产品编号错误:" + e.getMessage(), true, false);
		}
		return productCode;
	}
}