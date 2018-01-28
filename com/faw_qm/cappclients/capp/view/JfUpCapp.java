package com.faw_qm.cappclients.capp.view;

import javax.swing.UIManager;

import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;

/**���ϵͳ����Ϊv4r3,�������ݿ�ͻ��˵���
 * @author liuzc
 *
 */
public class JfUpCapp {

	/**����main�����������ݿ�
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
			System.setProperty("swing.useSystemFontSettings", "0");
			System.setProperty("swing.handleTopLevelPaint", "false");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (Exception e) {
			e.printStackTrace();
		}
		try 
		{
			String host = "10.28.68.210";
			String port = "7001";
			String username = "Administrator";
			String userpassword = "administrator";
			String sid = null;
			try 
			{
				sid = RequestServer.getSessionID(host, port, username,
						userpassword);
			} catch (QMRemoteException ex1) {
				ex1.printStackTrace();
			}
			CappClientRequestServer server = new CappClientRequestServer(host,
					port, sid);
			RequestServerFactory.setRequestServer(server);
			// CCBegin by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3,�������ݿ⡣
			long l1 = System.currentTimeMillis();
			System.out.println("---------------------------begin---------------------------");
			TechnicsAction.useServiceMethod("StandardCappService",
					"updateQMProcedureDrawingLinkTable", null, null);
			System.out.println("---updateQMProcedureDrawingLinkTable����---");
			TechnicsAction.useServiceMethod("StandardCappService",
					"saveSplitDrawing", null, null);
			System.out.println("---------------------------end---------------------------");
			long l2 = System.currentTimeMillis();
			System.out.println("-----������ʱ��:" + (l2 - l1) / 1000+"-----");
			// CCEnd by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3���������ݿ⡣
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
