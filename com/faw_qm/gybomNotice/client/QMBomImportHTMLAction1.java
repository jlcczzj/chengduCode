/** ����QMVirtualPartImportHTMLAction.java	1.0  
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.gybomNotice.client;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import com.faw_qm.content.exception.ContentException;
import com.faw_qm.content.util.StreamRequestHandler;
import com.faw_qm.framework.controller.web.action.HTMLActionSupport; 
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.framework.exceptions.QMException; 
import com.faw_qm.gybomNotice.ejb.service.GYBomNoticeService;
import com.faw_qm.util.EJBServiceHelper;


/**
 * ��QMVirtualPartImportHTMLAction������������� ���ࣺHTMLActionSupport
 * @see QMVirtualPartImportHTMLAction
 * @version 1.0
 * @author ����
 */
public final class QMBomImportHTMLAction1 extends HTMLActionSupport {


	// request�����������
	private HashMap hashMap = null;



	/**
	 * �÷���ִ�е������������
	 * @param HttpServletRequest request
	 * @return Event �����¼�
	 */
	public Event perform(HttpServletRequest request) throws QMException {

		// action����
		String actionType = "";
		StreamRequestHandler handler = new StreamRequestHandler(request);
		try {

			hashMap = handler.handle();
			actionType = (String) hashMap.get("action");
			// ��ù���BOM����
			GYBomNoticeService service = (GYBomNoticeService) EJBServiceHelper.getService("GYBomNoticeService");
			// ���Ϊ�����������������
			if (actionType.equals("importBom1")) {
				Vector logVec = new Vector();
				StringBuffer logBuffer = new StringBuffer();
				Set set = hashMap.keySet();
				Object file[];
				for(Iterator ite = set.iterator();ite.hasNext();){
					Object objs = hashMap.get(ite.next());
					if(!(objs instanceof String)){
						file = (Object[]) objs;
						if(file!=null){
							String byteStr = new String((byte[]) file[1]);
							String returnStr = service.importBom1(byteStr);
							logBuffer.append(returnStr);
/*							
							Vector vec = service.importBom(byteStr);
							if(vec!=null&&vec.size()>0){
								for(Iterator ites = vec.iterator();ites.hasNext();){
									String[] tem = (String[])ites.next();
									//logVec.add(tem);
									String logs = tem[0]+tem[1]+"<br>";
									logBuffer.append(logs);
								}
							}
*/
						}
							
					}
			
				}
			
				
				request.setAttribute("importBomtLog1", logBuffer.toString());
	
			}
		} catch (ContentException ex) {
			System.out.println("����������������ݳ���" + ex.getMessage());
		}


		return null;
	}

	
	
	


	/**
	 * ����������ĺ���
	 * 
	 * @param request
	 * @param eventResponse
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
	

//		if (eventResponse instanceof QMCastEventResponse) {
//			QMCastEventResponse response = (QMCastEventResponse) eventResponse;
//			request.setAttribute("operationCastBsoID", response
//					.getOperationCastBsoID());
//			request.setAttribute("operationName", response.getOperationName());
//		}

	}

	
	
 
}
