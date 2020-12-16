package com.github.xuejike.query.jpa.lambda.tool;

import com.github.xuejike.query.core.enums.StringMatchMode;
import org.hibernate.criterion.MatchMode;

/**
 * @author xuejike
 * @date 2020/12/16
 */
public class MatchModeTool {
    public static MatchMode getMatchMode(StringMatchMode matchMode){
        switch (matchMode){
            case END:
                return MatchMode.END;
            case START:
                return MatchMode.START;
            case ANYWHERE:
                return MatchMode.ANYWHERE;
            case EXACT:
            default:
                return MatchMode.EXACT;
        }
    }
}
