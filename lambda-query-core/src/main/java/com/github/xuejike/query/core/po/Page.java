package com.github.xuejike.query.core.po;

import com.github.xuejike.query.core.criteria.IPage;
import lombok.Data;

import java.util.List;

/**
 * @author xuejike
 * @date 2020/12/21
 */
@Data
public class Page<T> implements IPage<T> {
     private long total;
     private List<T> data;
     private int pageSize;
     private int pageNo;
     private boolean haveTotal;


}
