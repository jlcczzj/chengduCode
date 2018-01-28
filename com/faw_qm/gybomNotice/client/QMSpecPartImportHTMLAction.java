/** 程序QMVirtualPartImportHTMLAction.java	1.0  
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * 类QMSpecPartImportHTMLAction处理导入专用件。 父类：HTMLActionSupport
 * @see QMSpecPartImportHTMLAction
 * @version 1.0
 * @author liuyang
 */
public final class QMSpecPartImportHTMLAction extends HTMLActionSupport {


	// request解析后的数据
	private HashMap hashMap = null;



	/**
	 * 该方法执行导入虚拟件请求。
	 * @param HttpServletRequest request
	 * @return Event 返回事件
	 */
	public Event perform(HttpServletRequest request) throws QMException {

		// action动作
		String actionType = "";
		StreamRequestHandler handler = new StreamRequestHandler(request);
		try {

			hashMap = handler.handle();
			actionType = (String) hashMap.get("action");
			// 获得工艺BOM服务
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
							 partsBuffer.append("<br>系统中不存在的零部件有：</br>");
							 if(parts!=null && parts.size()>0)
							 for(int i = 0;i < parts.size(); i++){

								 partsBuffer.append("<br>"+parts.get(i)+"</br>");
								 index1++;
							 }
							 routesBuffer.append("<br>下列零部件在系统中没有成都工艺路线：</br>");
							 if(routes!=null && routes.size()>0)
							 for(int i = 0;i < routes.size(); i++){
								 routesBuffer.append("<br>"+routes.get(i)+"</br>");
								 index2++;
							 }
							 
							 if(noVersion!=null && noVersion.size()>0){
								 routesBuffer.append("<br>下列零部件没有版本，系统无法对应，请填写版本后再进行检查：</br>");
								 for(int i = 0;i < noVersion.size(); i++){
									 routesBuffer.append("<br>"+noVersion.get(i)+"</br>");
									 index3++;
								 }
							 }
							 partsBuffer.append("<br></br><br></br>"+routesBuffer);
						 }else{
							 partsBuffer.append("所有零部件在系统均存在，并全部有成都工艺路线。");
						 }
					 }

				 }
			 }
			 request.setAttribute("CheckPartFromExcellog", partsBuffer.toString());
		 }
			
		} catch (ContentException ex) {
			System.out.println("解析导入数据出错：" + ex.getMessage());
		}


		return null;
	}

	
	
	


	/**
	 * 导入专用件的后处理。
	 * 
	 * @param request
	 * @param eventResponse
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
	

	}

	
	
 
}
