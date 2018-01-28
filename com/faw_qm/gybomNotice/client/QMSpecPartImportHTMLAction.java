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
 * ��QMSpecPartImportHTMLAction������ר�ü��� ���ࣺHTMLActionSupport
 * @see QMSpecPartImportHTMLAction
 * @version 1.0
 * @author liuyang
 */
public final class QMSpecPartImportHTMLAction extends HTMLActionSupport {


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
		
		 if(actionType.equals("importSpecPart")){
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
								Vector vec = service.importSpecPart(byteStr);
								if(vec!=null&&vec.size()>0){
									for(Iterator ites = vec.iterator();ites.hasNext();){
										String[] tem = (String[])ites.next();
										String logs = tem[0]+tem[1]+"<br>";
										logBuffer.append(logs);
									}
								}
							}
								
						}
				}
					request.setAttribute("importSpecPartLog", logBuffer.toString());
		 }else if(actionType.equals("checkPartFromExcel")){
			 System.out.println("-----------------------checkPartFromExcel---------------------");
			 int index1 = 1;
			 int index2 = 1;
			 int index3 = 1;
			 StringBuffer partsBuffer = new StringBuffer();
			 StringBuffer routesBuffer = new StringBuffer();
			 Set set = hashMap.keySet();
			 Object file[];
			 for(Iterator ite = set.iterator();ite.hasNext();){
				 Object objs = hashMap.get(ite.next());
				 if(!(objs instanceof String)){
					 file = (Object[]) objs;
//					 if(file[1] == null){
//						 
//					 }else
					 String byteStr1 = new String((byte[]) file[1]);
					 if(file!=null && file[1]!=null){
						 String byteStr = new String((byte[]) file[1]);
						 HashMap results = service.checkPartFromExcel(byteStr);
						 if(results!=null&&results.size()>0){
							 Vector parts = (Vector)results.get("parts");
							 Vector routes = (Vector)results.get("routes");
							 Vector noVersion = (Vector)results.get("noversion");
							 partsBuffer.append("<br>ϵͳ�в����ڵ��㲿���У�</br>");
							 if(parts!=null && parts.size()>0)
							 for(int i = 0;i < parts.size(); i++){

								 partsBuffer.append("<br>"+parts.get(i)+"</br>");
								 index1++;
							 }
							 routesBuffer.append("<br>�����㲿����ϵͳ��û�гɶ�����·�ߣ�</br>");
							 if(routes!=null && routes.size()>0)
							 for(int i = 0;i < routes.size(); i++){
								 routesBuffer.append("<br>"+routes.get(i)+"</br>");
								 index2++;
							 }
							 
							 if(noVersion!=null && noVersion.size()>0){
								 routesBuffer.append("<br>�����㲿��û�а汾��ϵͳ�޷���Ӧ������д�汾���ٽ��м�飺</br>");
								 for(int i = 0;i < noVersion.size(); i++){
									 routesBuffer.append("<br>"+noVersion.get(i)+"</br>");
									 index3++;
								 }
							 }
							 partsBuffer.append("<br></br><br></br>"+routesBuffer);
						 }else{
							 partsBuffer.append("�����㲿����ϵͳ�����ڣ���ȫ���гɶ�����·�ߡ�");
						 }
					 }

				 }
			 }
			 request.setAttribute("CheckPartFromExcellog", partsBuffer.toString());
		 }
			
		} catch (ContentException ex) {
			System.out.println("�����������ݳ���" + ex.getMessage());
		}


		return null;
	}

	
	
	


	/**
	 * ����ר�ü��ĺ���
	 * 
	 * @param request
	 * @param eventResponse
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
	

	}

	
	
 
}
