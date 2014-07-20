package org.asuki.concurrency;

import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.ejb.LockType.READ;

import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

@Singleton
@Lock(READ)
public class CustomManagedExecutorService {

    @Inject
    private Logger log;

    @Resource
    private ManagedExecutorService executor;

    @Resource
    private ManagedScheduledExecutorService scheduledExecutor;

    private ScheduledFuture<?> future;

    public void executeTasks() throws InterruptedException, ExecutionException {

        List<Callable<String>> tasks = new ArrayList<>();
        tasks.add(new CallableTaskA(1));
        tasks.add(new CallableTaskB(2));

        List<Future<String>> taskResults = executor.invokeAll(tasks);

        List<String> results = new ArrayList<>();
        for (Future<String> taskResult : taskResults) {
            results.add(taskResult.get());
        }

        log.info(results.toString());
    }

    public void scheduleTasks() {

        if (future == null || future.isCancelled()) {
            future = scheduledExecutor.scheduleAtFixedRate(() -> {
                log.info("Date: " + new Date());
            }, 0, 3, SECONDS);

            return;
        }

        future.cancel(true);
    }
}
