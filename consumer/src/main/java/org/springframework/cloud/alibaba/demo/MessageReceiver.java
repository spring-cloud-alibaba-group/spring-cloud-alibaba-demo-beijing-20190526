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
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@Component
public class MessageReceiver {

    private static final Logger log = LoggerFactory
        .getLogger(MessageReceiver.class);

    @StreamListener(value = Sink.INPUT, condition = "headers['type']=='user'")
    public void receiveUser(@Payload User user) {
        log.info("receiveUser: {}", user);
    }

    @StreamListener(value = Sink.INPUT, condition = "headers['type']=='string'")
    public void receiveString(String str) {
        log.info("receiveString: {}", str);
    }

    @StreamListener(value = Sink.INPUT)
    public void receiveAll(Message message,
                           @Header(value = "type", required = false) String type,
                           @Header(value = "test", required = false) String test) {
        log.info("receiveAll msgs, payload: {}, type header: {}, test header: {}",
            message.getPayload(), type, test);
    }

}
