/**
 * 生成程序 SearchResult.java    1.0    2007/10/10
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.util.Collection;


/**
* <p>Title: 。</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2007</p>
* <p>Company: 一汽启明</p>
* @author 王海军
* @version 1.0
*/
public class SearchResult
{
    private String key;
    private Collection value;
    private Object[] obj;

    public SearchResult()
    {
    }

    public SearchResult(String key0, Collection value0)
    {
        this.key = key0;
        this.value = value0;
    }

    public SearchResult(String key0, Object[] obj0)
    {
        this.key = key0;
        this.obj = obj0;
    }
    
    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public Collection getValue()
    {
        return value;
    }

    public void setValue(Collection value)
    {
        this.value = value;
    }

    public Object[] getObj()
    {
        return obj;
    }

    public void setObj(Object[] obj)
    {
        this.obj = obj;
    }
}

