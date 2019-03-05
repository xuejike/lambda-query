package com.github.xuejike.query.jpa.lambda.core;

public interface SelectCriteria<R,F> {
    R select(F field,F alias);
    default R select(F field){
        return select(field,null);
    }
    R distinct(F field,F alias);
    default R distinct(F field){
        return distinct(field,null);
    }
    R count(F field,F alias);
    default R count(F field){
        return count(field,null);
    }
    R max(F field,F alias);
    default R max(F field){return max(field,null);}

    R min(F field,F alias);
    default R min(F field){return min(field,null);}

    R avg(F field,F alias);
    default R avg(F field){return avg(field,null);}

    R sum(F field,F alias);
    default R sum(F field){return sum(field,null);}

    R countDistinct(F field,F alias);
    default R countDistinct(F field){return countDistinct(field,null);}
}
