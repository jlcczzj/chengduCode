package com.faw_qm.jfpublish.receive;

/**
 * <p>Title: cPDM��������</p>
 * <p>Description: ��������cPDM���ն˴������Զ�̷�����ð棩</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FAW_QM</p>
 * @author ������
 * @version 1.0
 */

import com.faw_qm.adapter.BaseCommandDelegate;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.integration.model.Script;

public class PublishPartsCmdDelegate extends BaseCommandDelegate {
    
    public Script _invoke(Script script) throws QMException {
        
        PublishLoadHelper helper=new PublishLoadHelper();
        helper.loadDataByScript(script);
        Script response = new Script();
        return response;

    }
}
