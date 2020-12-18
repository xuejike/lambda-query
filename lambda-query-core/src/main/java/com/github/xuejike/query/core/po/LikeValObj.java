package com.github.xuejike.query.core.po;

import com.github.xuejike.query.core.enums.StringMatchMode;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class LikeValObj {
    private String val;
    private StringMatchMode matchMode;

    public LikeValObj() {
    }

    public LikeValObj(String val, StringMatchMode matchMode) {
        this.val = val;
        this.matchMode = matchMode;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public StringMatchMode getMatchMode() {
        return matchMode;
    }

    public void setMatchMode(StringMatchMode matchMode) {
        this.matchMode = matchMode;
    }
}
