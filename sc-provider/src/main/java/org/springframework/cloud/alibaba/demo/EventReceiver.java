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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.alibaba.demo.event.EchoEvent;
import org.springframework.cloud.alibaba.demo.event.EchoRemoteEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@Component
public class EventReceiver {

    private static final Logger log = LoggerFactory
        .getLogger(EventReceiver.class);

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private Long serverPort;

    @EventListener(classes = EchoRemoteEvent.class)
    public void remoteEvent(EchoRemoteEvent echoRemoteEvent) {
        log.info("{}-{} get remoteEvent: {}", applicationName, serverPort, echoRemoteEvent.getEchoMessage());
    }

    @EventListener(classes = EchoEvent.class)
    public void receiveEvent(EchoEvent echoEvent) {
        log.info("{}-{} get event: {}", applicationName, serverPort, echoEvent.getEchoMessage());
    }

}
