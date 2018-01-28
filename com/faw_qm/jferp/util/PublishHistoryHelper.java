package com.faw_qm.jferp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;

import com.faw_qm.erppartreload.ERPMiddlePart;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.uidgenservice.UIDHelper;

public class PublishHistoryHelper {
	//log文件的存放路径
	private static String logfilepath;
	//log文件的存放路径
	private static String logFileName;
	/**
	 * 在指定的目录下生成LOG文件。
	 * @author houhf
	 */
	public static void writeLog(String message)
	{
		FileWriter fw;
		try
		{
			if(logfilepath == null ||logfilepath.length() == 0)
			{
				String path = System.getProperty("java.class.path");
				SimpleDateFormat df = 
								new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				path = path.substring(0,path.indexOf(";")) + 
					   "\\" + logFileName + df.format(new Date()) + ".log";
				logfilepath = path;
			}
			fw = new FileWriter(logfilepath, false);
			fw.write(message.toString());
			fw.flush();
		}
		catch (IOException e)
		{
			System.out.println("写日志文件出错！！！");
			e.printStackTrace();
		}
	}
	public static String getPartType(ERPMiddlePart mPart){
		//判断是否是标准件
		String typeString ="零件";
		if(mPart.getPartType()!=null && mPart.getPartType().trim().length()>0)
		{
			if(mPart.getPartType().startsWith("C") ||
					mPart.getPartType().startsWith("CQ") ||
						mPart.getPartType().startsWith("Q")||
							mPart.getPartType().startsWith("T"))
			{
				if(mPart.getManuRoute() != null && 
						mPart.getManuRoute().trim().length()>0)
				{
					if(mPart.getManuRoute().equalsIgnoreCase("协"))
					{
						
						typeString = "标准件";
						
					}
				}
			}
		}
		return typeString;
	}
	public static int getxnj(ERPMiddlePart mPart){
		//判断是否是标准件
		int typeString =0;
		if(mPart.getDummyPart() != null && 
				mPart.getDummyPart().trim().length()>0)
		{
			if(mPart.getDummyPart().equalsIgnoreCase("50") ||
					mPart.getDummyPart().equalsIgnoreCase("51"))
			{
				typeString=1;
			}
		}
		return typeString;
	}

}
