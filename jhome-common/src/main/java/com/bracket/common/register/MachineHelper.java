package com.bracket.common.register;

import java.io.IOException;
import java.util.Scanner;

class MachineHelper {
	// static Dictionary<string, ManagementObjectCollection> WmiDict = new
	// Dictionary<string, ManagementObjectCollection>();

	private static String hardDiskNumber = "";
	private static String CPUNumber = "";

	private static String execCommon(String common) {
		String str = "";
		try {
			Process process = Runtime.getRuntime().exec(common);
			process.getOutputStream().close();
			Scanner sc = new Scanner(process.getInputStream());
			sc.next();
			while (sc.hasNext()) {
				str += sc.next();
			}
			sc.close();
			process.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FileOperate.WriteInfo("error.fly", "cmd:" + e.getMessage()
					+ " error", true, false);
		}
		return str;
	}

	/**
	 * ��ȡӲ�̺���
	 * 
	 * @return
	 */
	public static String GetHardDiskNumber() {
		if (hardDiskNumber != "")
			return hardDiskNumber;

		String line = "";
		String HdSerial = "";// ��¼Ӳ�����к�
		HdSerial = execCommon("wmic diskdrive where index=0 get Caption");
		line = execCommon("cmd.exe /c ver");//

		if (line.contains("4.0.") || line.contains("4.10.")
				|| line.contains("4.90.") || line.contains("3.51.")
				|| line.contains("5.0.") || line.contains("5.1.")
				|| line.contains("5.2.") || line.contains("6.0."))

		{
			line = execCommon("wmic diskdrive get signature");
		} else {
			line = execCommon("wmic diskdrive where index=0 get SerialNumber");
		}

		if (HdSerial != "" && line != "")
			hardDiskNumber = HdSerial + " " + line;

		return HdSerial + " " + line;
	}

	public static String GetMac() {
		String strMac = "lshflaFDLSYTh322ahg23dslmzqpg85wz";

		return strMac;
	}

	/**
	 * ��ȡcpu���к�
	 * 
	 * @return
	 */
	public static String GetCPUNumber() {
		if (CPUNumber != "")
			return CPUNumber;
		String serial = "";
		serial = execCommon("wmic CPU get ProcessorID");
		if (serial != "")
			CPUNumber = serial;

		return serial;
	}

}