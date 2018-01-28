package com.faw_qm.technics.consroute.test;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RequestServer;

/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2003</p> <p>Company: QM</p>
 * @author ’‘¡¢±Ú
 * @version 1.0
 */

public class TestRequestServer extends RequestServer
{

    public TestRequestServer(String host, String port, String sessionid) throws QMException
    {
        super(host, port, sessionid);
    }
}
