package com.faw_qm.jfpublish.receive;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * <p>Title: </p>
 * <p>Description: ÿ�η�����������Ҫ�������������м������ڽ��ձ�Ƕ����ó�false����ֹ
 * ���������������������ı��û���ϵ������</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class QQPartPublishServlet extends HttpServlet
{

	/**
	 */
	public void init() throws ServletException
	{
		super.init();
		try 
		{
			System.out.println("��������󣬳�ʼ�����������м��>........");
			QQPartPublish qq = new QQPartPublish();
			qq.publishInit();
			System.out.println("��������󣬳�ʼ�����������м�����!");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
