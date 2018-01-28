package com.faw_qm.gybomNotice.client.util;
import com.faw_qm.framework.remote.RequestServer;

/**
 * <p>Title:客户端请求服务封装类 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author xucy
 * @version 1.0
 */

public class GYNoticeClientRequestServer extends RequestServer
{

    public GYNoticeClientRequestServer(String host, String port, String session)
    {
        super(host, port, session);
    }

}