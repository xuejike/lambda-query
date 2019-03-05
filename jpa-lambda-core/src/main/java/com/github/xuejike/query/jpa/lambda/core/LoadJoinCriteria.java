package com.github.xuejike.query.jpa.lambda.core;

import org.hibernate.FetchMode;

public interface LoadJoinCriteria<P,F> {
    P loadJoin(F field);
}
