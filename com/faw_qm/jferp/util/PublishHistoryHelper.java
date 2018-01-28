package com.faw_qm.jferp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;

import com.faw_qm.erppartreload.ERPMiddlePart;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.uidgenservice.UIDHelper;

public class PublishHistoryHelper {
	//log�ļ��Ĵ��·��
	private static String logfilepath;
	//log�ļ��Ĵ��·��
	private static String logFileName;
	/**
	 * ��ָ����Ŀ¼������LOG�ļ���
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
			System.out.println("д��־�ļ���������");
			e.printStackTrace();
		}
	}
	public static String getPartType(ERPMiddlePart mPart){
		//�ж��Ƿ��Ǳ�׼��
		String typeString ="���";
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
					if(mPart.getManuRoute().equalsIgnoreCase("Э"))
					{
						
						typeString = "��׼��";
						
					}
				}
			}
		}
		return typeString;
	}
	public static int getxnj(ERPMiddlePart mPart){
		//�ж��Ƿ��Ǳ�׼��
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
