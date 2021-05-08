package com.elvesyuki.study.netty04_heartbeat_retry.client;

/**
 * @author ：luohuan
 * @date ：Created in 2021/5/8 下午2:41
 * @description：
 * @modified By：
 */
public interface RetryPolicy {

    /**
     * Called when an operation has failed for some reason. This method should return
     * true to make another attempt.
     *
     * @param retryCount the number of times retried so far (0 the first time)
     * @return true/false
     */
    boolean allowRetry(int retryCount);

    /**
     * get sleep time in ms of current retry count.
     *
     * @param retryCount current retry count
     * @return the time to sleep
     */
    long getSleepTimeMs(int retryCount);

}
