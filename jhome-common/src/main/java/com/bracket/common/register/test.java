package com.bracket.common.register;



//国泰安教学大数据分析平台  V1.0
//		产品编码  SD-INDT-017-SC-US-00
//		加密编码  87vmIyTHTNVFz6kKY5icmGOkSI5xaMQYBN4b6tJunR0wmFlJXsGf3+24ae6H89mr


public class test {
	private static String key = "Lnqf1s1lMeXsgV0T";

	public static void main1(String[] args) {
		String s = EncryptHelper
				.AESDecrypt(
						"FUtIjhoN2rCyj+Ag7qIIHaVkmqEz3S5fqE3O8ypJoPmni+yc8Q+gWPZT+ERvARs6rQbydHomxmmp\r\n" +
								"OsQMx82eGszKpHUweWq2BbdYs8uuuaArJivfFSxHSh1ULTWlKqSz",
						"pLxaaflxjXz87QXw");
		System.out.println(s);
	}

	public static void main(String[] args) throws Throwable {
		String productCode = "SD-INDT-017-SC-US-00";
		System.out.println(Register.GetCode(productCode));
		boolean checked = Register.Check(productCode);
		System.out.println("Check:" + checked);
		if (checked == false) {
			String registerCode = "b73be8849266308942c410b95b3a0eac";
			String licenseCode = "9gcAydG2+4Aqax18Fqi7FXjTmoXOZJRJ9Obj1C5EjSw2oy2p+Qpz7ryfDSxS9kgKMhd3hHPOSouNGq6xofm2vC63qVlNOMUhFwgKG5v59EA=";
			checked = Register.Registe(productCode, registerCode, licenseCode);
			System.out.println("Registe:" + checked);
		}
		for (int i = 0; i < 10000; i++) {
			try {
				System.out.println("checkCode: " + Register.Check(productCode));
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread.sleep(10000);
		}
	}

	public static void main3(String[] args) throws Throwable {
		String hardDiskInfo = MachineHelper.GetHardDiskNumber();
		String cpuInfo = MachineHelper.GetCPUNumber();
		System.out.println("项目路径：" + System.getProperty("user.dir"));
		System.out.println("硬盤信息：" + hardDiskInfo);
		System.out.println("cpu序列號：" + cpuInfo);
		System.out.println("MD5加密32位："
				+ EncryptHelper.MD5Encrypt32(hardDiskInfo + cpuInfo));
		System.out.println("AES加密："
				+ EncryptHelper.AESEncrypt("2017-04-18", "uLHXbfa2B0RV9hoB"));
		System.out.println("AES解密："
				+ EncryptHelper.AESDecrypt(EncryptHelper.AESEncrypt(
				"2016-11-15_2017-11-15_100", "uLHXbfa2B0RV9hoB"),
				"uLHXbfa2B0RV9hoB"));

		String productCode = "SD-INDT-017-SC-US-00";
		String applyCode = "88b732becbf6ec414318fd172156e5a0";
		String licenseCode = "5u6lvEXxitgL6ac3Fz60OP4zXhkQ4FF7jPCb6fRMxavcNGKMGC486s8KKQ98EtkAZUxSlbLtdA7pBt8ejsK/DYpy4s70cnPCAkEW5k8BvEBYLmhwHDPfubsJeG+QDSQT7rB5/vpdngAag3pg9TX6Z0Sd2CoNJ+xy7y+sqy+9lBI=";

		// win2003
		/*
		 * String productCode = "gtaqcdlzccxjylvrxtv1.1tw"; String applyCode =
		 * "NlEfAZb0spbwGxaO"; String licenseCode=
		 * "VaC9S1coNnnRqe39DccmRlhQk5h1JOKoK/ewXaQLrHFC0SJr5X4/Yf4LI5MS3ebnT0TKa8ajjZ0t8/svsaYfp6H2m0+94FKDhhUICzwu6gSGiQdmT40mjxXvSI9aS/2s"
		 * ;
		 */

		System.out.println("productCode：" + productCode);
		System.out.println("applyCode：" + applyCode);
		System.out.println("licenseCode：" + licenseCode);

		try {
			System.out.println("Registe 111: "
					+ Register.Registe(productCode, applyCode, licenseCode));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < 10000; i++) {
			try {
				System.out.println("checkCode: " + Register.Check(productCode));
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread.sleep(10000);
		}

		/*
		 * try { System.out.println("getCode : "+Register.GetCode(productCode));
		 * } catch (Throwable e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * for (int i = 0; i < 100000; i++) {
		 * System.out.println("Registe 111: "+Register.Check(productCode)); }
		 */
		System.out.println("isEnd");

		/*
		 * new Thread(new Runnable() {
		 *
		 * @Override public void run() { while (true){ String productCode =
		 * "gtaqcdlzccxjylvrxtv1.1tw"; String applyCode = "Gduj2VS96DNM2q9l";
		 * String licenseCode=
		 * "RPy8YnxV9/OtZa85R95DPVc3/Ev41b4CdbRozcnRzlq7tad/lz9ShSKf4dWV8NUA4/GW3Mv7GTw22ZaH2MQAlmtSsu/9g7RWglLZjzvAmU+1AbWMsOCO27PoqtybpFec"
		 * ;
		 *
		 * System.out.println("productCode："+productCode);
		 * System.out.println("applyCode："+applyCode);
		 * System.out.println("licenseCode："+licenseCode);
		 *
		 *
		 * try { System.out.println("checkCode: "+Register.Check(productCode));
		 * } catch (Throwable e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } try {
		 * System.out.println("getCode : "+Register.GetCode(productCode)); }
		 * catch (Throwable e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } try {
		 * System.out.println("Registe 111: "+Register.Registe(productCode,
		 * applyCode, licenseCode)); } catch (Throwable e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } try {
		 * Thread.sleep(60000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 *
		 * } }).start();
		 */

	}
}
