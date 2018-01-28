/**
 * ���ɳ���NoticeClientUtil.java    1.0    2014-6-3
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ������������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.gybomNotice.client.util;

import java.util.StringTokenizer;
import com.faw_qm.bomNotice.util.NoticeHelper;
import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.users.model.UserIfc;

/**
 * <p>Title:Ϊ�ͻ����ṩ���� ��</p>
 * <p>Description:�����ṩ��һϵ�о�̬����,����ʹ�� </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ����
 * @version 1.0
 */
public class GYNoticeClientUtil 
{
   
    /**
     * ��õ�ǰ�û�
     * @return String ��ǰ�û�
     */
    public static UserIfc getCurrentUser() throws QMException
    {

        UserIfc sysUser = null;
        sysUser = (UserIfc)NoticeHelper.requestServer("SessionService", "getCurUserInfo", null, null);
        return sysUser;
    }
    
    /**
     * ���ݳ־û���Ϣˢ��ҵ����� ���ظ�������ֵ����
     * @param baseValueIfc BaseValueIfc
     * @return BaseValueInfo
     * @throws QMRemoteException
     */
    public static BaseValueInfo refresh(String bsoID) throws QMException
    {
        BaseValueInfo obj=null;
        if(bsoID!=null &&!bsoID.equals(""))
        {
            Class[] paraClass = {String.class};
            //����ֵ�ļ���
            Object[] objs = {bsoID};
            obj = (BaseValueInfo)NoticeHelper.requestServer("PersistService", "refreshInfo", paraClass, objs);
        }
        return obj;
    }
    
    /**
     * ͨ�������ļ����÷�����Ϣ
     * @param serverInform
     * @throws QMRemoteException 
     */
    public static void setServer(String serverInform) throws QMRemoteException
    {
        String ip = "";
        String sort = "";
        String userName = "";
        String password = "";
        if(serverInform != null && !serverInform.equals(""))
        {
            StringTokenizer token = new StringTokenizer(serverInform, ",");
            if(token.countTokens() == 4)
            {
                ip = token.nextToken();
                sort = token.nextToken();
                userName = token.nextToken();
                password = token.nextToken();
            }
        }
        String session = RequestServer.getSessionID(ip, sort, userName, password);
        GYNoticeClientRequestServer server = new GYNoticeClientRequestServer(ip, sort, session);
        RequestServerFactory.setRequestServer(server);
    }
    
   
}
