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
 * 类QMVirtualPartImportHTMLAction处理导入虚拟件。 父类：HTMLActionSupport
 * @see QMVirtualPartImportHTMLAction
 * @version 1.0
 * @author 文柳
 */
public final class QMBomImportHTMLAction1 extends HTMLActionSupport {


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
			// 如果为“导入虚拟件”动作
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
			System.out.println("解析导入虚拟件数据出错：" + ex.getMessage());
		}


		return null;
	}

	
	
	


	/**
	 * 导入虚拟件的后处理。
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
