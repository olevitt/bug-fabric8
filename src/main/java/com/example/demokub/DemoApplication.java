package com.example.demokub;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
        try (KubernetesClient kubernetesClient = new KubernetesClientBuilder().build()) {
            kubernetesClient.pods()
                    .inNamespace(
                            "namespacename")
                    .withName("podname")
                    .getLog();
        }
	}

}
