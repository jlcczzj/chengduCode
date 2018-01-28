package com.faw_qm.jfpublish.receive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import com.faw_qm.adapter.BaseCommandDelegate;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.integration.model.Command;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.model.Param;
import com.faw_qm.integration.model.Script;
import com.faw_qm.integration.util.QMProperties;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.jfpublish.receive.RenWuDanCreateDelegate;
import com.faw_qm.jfpublish.receive.PublishHelper;
import com.faw_qm.jfpublish.receive.PublishLoadHelper;
import com.faw_qm.jfpublish.receive.PublishPartsLog;
import com.faw_qm.jfpublish.receive.ResultReport;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.GroupInfo;
import com.faw_qm.users.model.UserInfo;

public class RenWuDanCommandDelegate extends BaseCommandDelegate {

	protected static String ERROR_PATH;

	protected static String LOG_PATH;

	public static Properties mapPro = null;
	
	protected static String MAPP_PATH;

	static {
		try {
			ERROR_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.error.path");
			LOG_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.log.path");
			MAPP_PATH = QMProperties.getLocalProperties().getProperty(
	                "publish.mappingreceive.path");
			mapPro = new Properties();
			Properties old = new Properties();
			old.load(new FileInputStream(
      new File(MAPP_PATH)));																
			Enumeration e = old.keys();
			if (PublishHelper.VERBOSE)
				System.out.println("======>old pro amount: " + old.size());
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				if (PublishHelper.VERBOSE)
					System.out.println("key: "
							+ PublishHelper.strDecoding(key, "ISO8859-1")
							+ ",value: "
							+ PublishHelper.strDecoding(old.getProperty(key),
									"ISO8859-1"));
				mapPro.setProperty(PublishHelper.strDecoding(key, "ISO8859-1"),
						PublishHelper.strDecoding(old.getProperty(key),
								"ISO8859-1"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Script _invoke(Script script) throws QMException {
		SessionService sservice = null;
		UsersService userservice = null;
		UserInfo curUser = null;
		boolean administratorFlag = false;// ������ʶ��ǰ�û��Ƿ����ڹ���Ա�飬�������������Ҫ��ʱ�������Ա��
		GroupInfo groupinfo = null;
		try {
			System.out.println("****** ����  ���������ĵ�  �߳�");
			PublishHelper.setStateBusy();
			if (PublishHelper.VERBOSE)
				System.out.println("****** ����ҵ�����߳�" + "the script is :"
						+ script != null);
			String users = "";
			String pass = "";
			try {
				users = QMProperties.getLocalProperties().getProperty("com.faw_qm.publish.user");
				pass = QMProperties.getLocalProperties().getProperty("com.faw_qm.publish.password");
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				 sservice = (SessionService) PublishHelper.getEJBService(
					"SessionService", users, pass);
			//CCEnd by liunan 2010-03-18
			userservice = (UsersService) PublishHelper
					.getEJBService("UsersService");
			Command cmd = script.getCommand();
			String cmdName = cmd.getName();
			UserInfo user = userservice.getUserValueInfo("rdc");
			curUser = (UserInfo) sservice.getCurUserInfo();
			sservice.setAdministrator();
			groupinfo = (GroupInfo) userservice.getActorValueInfo(
					"Administrators", false);
			if (!userservice.isMember(curUser, groupinfo)) {
				userservice.groupAddUser(groupinfo, curUser);
			} else {
				administratorFlag = true;
			}
			sservice.freeAdministrator();
			log("��ʼ���������ĵ���");
			PersistService ps = (PersistService) PublishHelper
					.getEJBService("PersistService");
			// ��������������Ϣ

			Group changeOrders = script.getGroupIn("RENWUDAN");
			// ���������ĵ��ĵ�
			log("���������ĵ�");
			if (changeOrders != null && changeOrders.getElementCount() != 0) {
				System.out.println("****** �� �����ĵ��ĵ�");
				RenWuDanCreateDelegate dele = new RenWuDanCreateDelegate(
						changeOrders, new PublishLoadHelper());
				ResultReport res = dele.process();
				log("���������ĵ��ĵ� RESULT ***************");
				log(res);
				System.out.println("****** �����ĵ��ĵ�  �������");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			PublishHelper.setStateFree();
			try {
				sservice.setAdministrator();
				// �����ǰ�û��Ĺ���ԱȨ��
				if (!administratorFlag) {
					userservice.groupDeleteUser(groupinfo, curUser);
				}
				sservice.freeAdministrator();
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Script response = new Script();
			return response;
		}
	}

	protected void log(Object msg) {
		PublishPartsLog.log(msg);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
