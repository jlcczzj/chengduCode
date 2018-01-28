package com.faw_qm.jfpublish.receive;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.faw_qm.adapter.BaseCommandDelegate;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.integration.model.Script;
import com.faw_qm.integration.model.object.InteMultipartOutputStream;
import com.faw_qm.integration.util.QMProperties;
import com.faw_qm.session.ejb.service.SessionService;

public class BolbPublishResultDelegate extends BaseCommandDelegate {

	private static String HTML_PATH;
	static {
		try {
			HTML_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.html.path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Script _invoke(Script script) throws QMException {
		System.out.println("BolbPublishResultDelegate._invoke start！");
		//CCBegin by liunan 2010-03-18 修改密码，由11111111改成wcsendjf
			SessionService sservice = (SessionService) PublishHelper.getEJBService(
				"SessionService", "rdc", "wcsendjf");
		//CCEnd by liunan 2010-03-18
		try {
			//CCBegin by liunan 2008-09-03
			//为输出语句添加开关。
			if (PublishHelper.VERBOSE)
			//CCEnd by liunan 2008-09-03
			System.out.println("BolbPublishResultDelegate._invoke start！");
			PublishHelper.setStateBusy();
			sservice.setAccessEnforced(false);
			//liunan 2009-09-04 
			System.out.println("xml log begain!");
			/*if (PublishHelper.VERBOSE)
				System.out.println("xml log begain!");
			String noteNumber = script.getCommand().paramValue("NOTENUMBER")
					.trim();
			SimpleDateFormat simple = new SimpleDateFormat(
					"yyyy年MM月dd日HH时mm分ss秒"); // 发布时间
			String publishTime = simple.format(new Date());*/
			BolbPublishResultGenerator generator=new BolbPublishResultGenerator(script);
			generator.generateBolbPublishResult();
			PublishHelper.setStateFree();
			sservice.setAccessEnforced(true);
			Script response = new Script();
			//liunan 2009-09-04 if (PublishHelper.VERBOSE)
				System.out
						.println("BolbPublishResultDelegate._invoke end！");
			return response;
		} catch (Exception e) {
			//CCBegin by liunan 2009-07-16
			e.printStackTrace();
			//CCEnd by liunan 2009-07-16
			PublishHelper.setStateFree();
			sservice.setAccessEnforced(true);
			if (e instanceof QMException) {
				throw (QMException) e;
			} else {
				e.printStackTrace();
				Script response = new Script();
				return response;
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
