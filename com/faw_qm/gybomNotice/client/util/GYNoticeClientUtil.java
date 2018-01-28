/**
 * 生成程序NoticeClientUtil.java    1.0    2014-6-3
 * 版权归启明信息技术股份有限公司所有
 * 本程序属本公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
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
 * <p>Title:为客户端提供服务 。</p>
 * <p>Description:本类提供了一系列静态方法,方便使用 </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 文柳
 * @version 1.0
 */
public class GYNoticeClientUtil 
{
   
    /**
     * 获得当前用户
     * @return String 当前用户
     */
    public static UserIfc getCurrentUser() throws QMException
    {

        UserIfc sysUser = null;
        sysUser = (UserIfc)NoticeHelper.requestServer("SessionService", "getCurUserInfo", null, null);
        return sysUser;
    }
    
    /**
     * 根据持久化信息刷新业务对象 返回更新完后的值对象。
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
            //参数值的集合
            Object[] objs = {bsoID};
            obj = (BaseValueInfo)NoticeHelper.requestServer("PersistService", "refreshInfo", paraClass, objs);
        }
        return obj;
    }
    
    /**
     * 通过配置文件设置服务信息
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
