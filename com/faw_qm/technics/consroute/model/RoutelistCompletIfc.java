/**
 * ���ɳ���RoutelistCompletIfc	1.9  2003/8/10
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.enterprise.model.ManagedIfc;
import com.faw_qm.lock.model.LockIfc;
import com.faw_qm.content.model.FormatContentHolderIfc;


/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public interface RoutelistCompletIfc extends ManagedIfc,
        LockIfc, FormatContentHolderIfc
{
    public String getIdentity();

    public String getCompletNum();


    public void setCompletNum(String num);


    public String getCompletName();


    public void setCompletName(String name);


    public String getCompletType();


    public void setCompletType(String type);


    public String getCompletNote();


    public void setCompletNote(String note);


    public String getCompletState();

    public void setCompletState(String state);

    public String getStandardIcon();

}
