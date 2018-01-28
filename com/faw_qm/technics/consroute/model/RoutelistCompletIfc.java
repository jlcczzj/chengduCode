/**
 * 生成程序RoutelistCompletIfc	1.9  2003/8/10
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
