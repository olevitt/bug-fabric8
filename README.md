# POC for https://github.com/fabric8io/kubernetes-client/issues/6059  

To reproduce the issue, just run the project (you may want to change `namespacename` and `podname` in `DemoApplication.java` even if the bug also happens if the pod does not exist) : `mvn spring-boot:run`.  
You will have the following non-blocking error message :  
```
java.util.concurrent.CompletionException: java.util.concurrent.RejectedExecutionException
	at java.base/java.util.concurrent.CompletableFuture.encodeThrowable(CompletableFuture.java:315) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture.completeThrowable(CompletableFuture.java:320) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture$UniCompose.tryFire(CompletableFuture.java:1159) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:510) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture.complete(CompletableFuture.java:2179) ~[na:na]
	at io.fabric8.kubernetes.client.http.StandardHttpClient.lambda$completeOrCancel$10(StandardHttpClient.java:142) ~[kubernetes-client-api-6.13.0.jar:na]
	at java.base/java.util.concurrent.CompletableFuture.uniWhenComplete(CompletableFuture.java:863) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture$UniWhenComplete.tryFire(CompletableFuture.java:841) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:510) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture.complete(CompletableFuture.java:2179) ~[na:na]
	at io.fabric8.kubernetes.client.http.ByteArrayBodyHandler.onBodyDone(ByteArrayBodyHandler.java:51) ~[kubernetes-client-api-6.13.0.jar:na]
	at java.base/java.util.concurrent.CompletableFuture.uniWhenComplete(CompletableFuture.java:863) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture$UniWhenComplete.tryFire(CompletableFuture.java:841) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:510) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture.complete(CompletableFuture.java:2179) ~[na:na]
	at io.fabric8.kubernetes.client.okhttp.OkHttpClientImpl$OkHttpAsyncBody.doConsume(OkHttpClientImpl.java:136) ~[kubernetes-httpclient-okhttp-6.13.0.jar:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:1583) ~[na:na]
Caused by: java.util.concurrent.RejectedExecutionException: null
	at io.fabric8.kubernetes.client.utils.internal.SerialExecutor.execute(SerialExecutor.java:47) ~[kubernetes-client-6.13.0.jar:na]
	at io.fabric8.kubernetes.client.informers.impl.cache.SharedProcessor.execute(SharedProcessor.java:179) ~[kubernetes-client-6.13.0.jar:na]
	at io.fabric8.kubernetes.client.informers.impl.cache.Reflector.lambda$null$4(Reflector.java:136) ~[kubernetes-client-6.13.0.jar:na]
	at io.fabric8.kubernetes.client.informers.impl.cache.ProcessorStore.retainAll(ProcessorStore.java:116) ~[kubernetes-client-6.13.0.jar:na]
	at io.fabric8.kubernetes.client.informers.impl.cache.Reflector.lambda$listSyncAndWatch$6(Reflector.java:130) ~[kubernetes-client-6.13.0.jar:na]
	at java.base/java.util.concurrent.CompletableFuture$UniCompose.tryFire(CompletableFuture.java:1150) ~[na:na]
	... 16 common frames omitted
```

Downgrading to fabric8 `6.12.0` or older version you will not have this error