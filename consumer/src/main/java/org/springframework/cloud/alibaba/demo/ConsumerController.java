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

import com.alibaba.fescar.spring.annotation.GlobalTransactional;

import org.springframework.cloud.alibaba.demo.ConsumerApplication.FeignRestService;
import org.springframework.cloud.alibaba.demo.ConsumerApplication.ProviderService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@RestController
public class ConsumerController {

    private final ProviderService providerService;

    private final FeignRestService feignRestService;

    public ConsumerController(
        ProviderService providerService,
        FeignRestService feignRestService) {
        this.providerService = providerService;
        this.feignRestService = feignRestService;
    }

    @GetMapping("/rt")
    public String rt() {
        return providerService.rt();
    }

    @GetMapping("/error")
    public String error() {
        return providerService.error();
    }

    @GetMapping("/flow")
    public String flow() {
        return providerService.flow();
    }

    @GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
    @GetMapping("/seata_success")
    public String seata_success() {
        providerService.saveA();
        feignRestService.saveB(1, "test");
        throw new RuntimeException("custom exception for seata");
    }

    @Transactional
    @GetMapping("/seata_fail")
    public String seata_fail() {
        providerService.saveA();
        feignRestService.saveB(1, "test");
        throw new RuntimeException("custom exception for seata");
    }

}
