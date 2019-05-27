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

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.demo.ConsumerApplication.DubboFeignRestService;
import org.springframework.cloud.alibaba.demo.ConsumerApplication.FeignRestService;
import org.springframework.cloud.alibaba.demo.service.EchoService;
import org.springframework.cloud.alibaba.demo.service.RestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@RestController
@RequestMapping("/dubbo")
public class DubboController {

    @Reference(version = "1.0.0", protocol = "dubbo")
    private EchoService echoService;

    @Reference(version = "1.0.0", protocol = "dubbo")
    private RestService restService;

    @Autowired
    private FeignRestService feignRestService;

    @Autowired
    private DubboFeignRestService dubboFeignRestService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/echo")
    public String echo(@RequestParam String param) {
        return echoService.echo(param);
    }

    @GetMapping("/rest")
    public String rest(@RequestParam String param) {
        return restService.param(param);
    }

    @GetMapping("/feign")
    public String feign(@RequestParam String param) {
        return feignRestService.param(param);
    }

    @GetMapping("dubbofeign")
    public String dubbofeign(@RequestParam String param) {
        return dubboFeignRestService.param(param);
    }

    @GetMapping("/dubboresttemplate")
    public String dubboresttemplate(@RequestParam String param) {
        return restTemplate.getForObject("http://dubbo-provider/param?param=" + param, String.class);
    }

}
