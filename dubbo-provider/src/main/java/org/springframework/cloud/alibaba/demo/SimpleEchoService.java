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

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.alibaba.demo.service.EchoService;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@Service(version = "1.0.0", protocol = "dubbo")
public class SimpleEchoService implements EchoService {

    private static final Logger log = LoggerFactory
        .getLogger(SimpleEchoService.class);

    @Override
    public String echo(String param) {
        log.info("echo: " + param);
        return "echo: " + param;
    }
}
