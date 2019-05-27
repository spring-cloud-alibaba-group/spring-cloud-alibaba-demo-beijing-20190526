/*
 * Copyright (C) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.alibaba.demo;

import com.alibaba.csp.sentinel.slots.block.BlockException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.alibaba.dubbo.annotation.DubboTransported;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@SpringBootApplication
@RemoteApplicationEventScan(basePackages = "org.springframework.cloud.alibaba.demo.event")
@EnableBinding(Sink.class)
@EnableFeignClients
public class ConsumerApplication {

    @Bean
    @LoadBalanced
    @DubboTransported
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @FeignClient("dubbo-provider")
    @DubboTransported(protocol = "dubbo")
    public interface DubboFeignRestService {
        @GetMapping(value = "/param")
        String param(@RequestParam("param") String param);

        @PostMapping("/saveB")
        String saveB(@RequestParam("a") int a, @RequestParam("b") String b);
    }

    @FeignClient("dubbo-provider")
    public interface FeignRestService {
        @GetMapping(value = "/param")
        String param(@RequestParam("param") String param);

        @PostMapping("/saveB")
        String saveB(@RequestParam("a") int a, @RequestParam("b") String b);
    }

    @FeignClient(name = "sc-provider", fallbackFactory = FallbackFactory.class)
    public interface ProviderService {
        @GetMapping("/rt")
        String rt();

        @GetMapping("/error")
        String error();

        @GetMapping("/flow")
        String flow();

        @GetMapping("/saveA")
        String saveA();
    }

    class SentinelProviderServiceFallback implements ProviderService {
        @Override
        public String rt() {
            return "rt block by sentinel";
        }

        @Override
        public String error() {
            return "error block by sentinel";
        }

        @Override
        public String flow() {
            return "flow block by sentinel";
        }

        @Override
        public String saveA() {
            return "saveA block by sentinel";
        }
    }

    class DefaultProviderServiceFallback implements ProviderService {
        @Override
        public String rt() {
            return "rt block by default";
        }

        @Override
        public String error() {
            return "error block by default";
        }

        @Override
        public String flow() {
            return "flow block by default";
        }

        @Override
        public String saveA() {
            return "saveA block by default";
        }
    }

    @Bean
    public FallbackFactory fallbackFactory() {
        return new FallbackFactory();
    }

    class FallbackFactory implements feign.hystrix.FallbackFactory {

        private SentinelProviderServiceFallback sentinelProviderServiceFallback = new SentinelProviderServiceFallback();

        private DefaultProviderServiceFallback defaultProviderServiceFallback = new DefaultProviderServiceFallback();

        @Override
        public Object create(Throwable cause) {
            if(cause instanceof BlockException) {
                return sentinelProviderServiceFallback;
            } else {
                return defaultProviderServiceFallback;
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
