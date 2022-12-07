package com.highload.personservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.internal.JaegerTracer;

@EnableEurekaClient
@SpringBootApplication
public class PersonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonServiceApplication.class, args);

	}
	public static JaegerTracer initTracer(String service) {
		SamplerConfiguration samplerConfig = SamplerConfiguration.fromEnv().withType("const").withParam(1);
		ReporterConfiguration reporterConfig = ReporterConfiguration.fromEnv().withLogSpans(true);
		Configuration config = new Configuration(service).withSampler(samplerConfig).withReporter(reporterConfig);
		return config.getTracer();
	}
}
