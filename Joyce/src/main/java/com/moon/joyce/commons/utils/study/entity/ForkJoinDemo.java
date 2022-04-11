package com.moon.joyce.commons.utils.study.entity;

import java.util.concurrent.RecursiveTask;

/**
 * @Author: XingDaoRong
 * @Date: 2022/2/8
 */
public class ForkJoinDemo extends RecursiveTask<Long> {
    private static final long serialVersionUID = -4098337666896582884L;
    private Long start;
    private Long end;
    //临界值
    private Long temp =1000l;

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0l;
        if ((end-start)<temp){
            for (Long i = start; i <= end; i++) {
                sum+=i;
            }
        }else {
            long mid = start+(end-start)/2;
            ForkJoinDemo f1 = new ForkJoinDemo(start, mid);
            f1.fork();
            ForkJoinDemo f2 = new ForkJoinDemo(mid+1, end);
            f2.fork();
            sum = f1.join()+f2.join();
        }
        return sum;
    }
}
