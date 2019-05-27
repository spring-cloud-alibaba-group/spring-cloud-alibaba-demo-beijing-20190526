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

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.alibaba.demo.event.EchoEvent;
import org.springframework.cloud.alibaba.demo.event.EchoRemoteEvent;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@RestController
public class MsgController {

    private final ApplicationContext applicationContext;

    private final Source source;

    @Value("${spring.cloud.bus.id}")
    private String originService;

    @Value("${default.message:hello world!}")
    private String defaultMsg;

    public MsgController(ApplicationContext applicationContext, Source source) {
        this.applicationContext = applicationContext;
        this.source = source;
    }

    @GetMapping("/publishRemoteEvent")
    public String publishRemote(@RequestParam(value = "msg", required = false) String msg) {
        if (StringUtils.isEmpty(msg)) {
            msg = defaultMsg;
        }
        EchoRemoteEvent event = new EchoRemoteEvent(this, originService, msg);
        this.applicationContext.publishEvent(event);
        return "publish EchoRemoteEvent success";
    }

    @GetMapping("/publishLocalEvent")
    public String publishLocal(@RequestParam(value = "msg", required = false) String msg) {
        if (StringUtils.isEmpty(msg)) {
            msg = defaultMsg;
        }
        EchoEvent event = new EchoEvent(this, msg);
        this.applicationContext.publishEvent(event);
        return "publish EchoEvent success";
    }

    @GetMapping("/sendObj")
    public String sendObj(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        User user = new User(1L, name, age);
        MessageHeaders headers = new MessageHeaders(Collections.<String, Object>singletonMap("type", "user"));
        source.output().send(MessageBuilder.createMessage(user, headers));
        return "send User payload message success";
    }

    @GetMapping("/sendString")
    public String sendString(@RequestParam(value = "msg", required = false) String msg) {
        if (StringUtils.isEmpty(msg)) {
            msg = defaultMsg;
        }
        MessageHeaders headers = new MessageHeaders(Collections.<String, Object>singletonMap("type", "string"));
        source.output().send(MessageBuilder.createMessage(msg, headers));
        return "send String payload message success";
    }

}
