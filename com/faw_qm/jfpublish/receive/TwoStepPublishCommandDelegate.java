package com.faw_qm.jfpublish.receive;

import java.io.FileNotFoundException;
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

public class TwoStepPublishCommandDelegate extends BaseCommandDelegate {

	// private static boolean publishingFlag = false;

	private static String XML_PATH;

	static {
		try {
			XML_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.xml.path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Script _invoke(Script script) throws QMException {
		//CCBegin by liunan 2010-03-18 修改密码，由11111111改成wcsendjf
		SessionService sservice = (SessionService) PublishHelper.getEJBService(
				"SessionService", "rdc", "wcsendjf");
		//CCEnd by liunan 2010-03-18
		try {
		  //CCBegin by liunan 2008-09-03
		  //为输出语句添加开关。
		  if (PublishHelper.VERBOSE)
		  //CCEnd by liunan 2008-09-03
			System.out.println("TwoStepPublishCommandDelegate._invoke start！");
			PublishHelper.setStateBusy();
			sservice.setAccessEnforced(false);
			// sservice.setAdministrator();
			if (PublishHelper.VERBOSE)
				System.out.println("xml log begain!");
			String noteNumber = script.getCommand().paramValue("NOTENUMBER")
					.trim();
			SimpleDateFormat simple = new SimpleDateFormat(
					"yyyy年MM月dd日HH时mm分ss秒"); // 发布时间
			String publishTime = simple.format(new Date());
			String outFileName = XML_PATH + noteNumber + ".xml";
			FileOutputStream out = new FileOutputStream(outFileName);
			InteMultipartOutputStream ieOut = new InteMultipartOutputStream(out);
			ieOut.writeRequest(script.getRequest());//保存xml文件
			if (out != null) {
				out.close();
			}
			if (ieOut != null) {
				ieOut.close();
			}
			if (PublishHelper.VERBOSE)
				System.out.println("xml log end!");
			// 生成“数据发布接收确认通知书”
			ConfirmDocumentGenerator gene = new ConfirmDocumentGenerator(script);
			gene.generateConfirmDocument();
			//保存基本数据
			PublishLoadHelper helper = new PublishLoadHelper();
			helper.loadDataByScript(script);
			sservice.setAccessEnforced(true);
		} catch (Exception e) {
			if (e instanceof QMException) {
				throw (QMException) e;
			} else {
				e.printStackTrace();
			}
		} finally {
			PublishHelper.setStateFree();
			// sservice.freeAdministrator();
			Script response = new Script();
			if (PublishHelper.VERBOSE)
				System.out
						.println("TwoStepPublishCommandDelegate._invoke end！");
			return response;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
