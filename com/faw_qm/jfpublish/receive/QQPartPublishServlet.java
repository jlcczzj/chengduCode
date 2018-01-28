package com.faw_qm.jfpublish.receive;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * <p>Title: </p>
 * <p>Description: 每次服务器重启后都要将青汽发布的中间表的正在接收标记都设置成false，防止
 * 由于重启服务或死机引起的标记没打上的情况。</p>
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
			System.out.println("重启服务后，初始化青汽发布中间表>........");
			QQPartPublish qq = new QQPartPublish();
			qq.publishInit();
			System.out.println("重启服务后，初始化青汽发布中间表结束!");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
