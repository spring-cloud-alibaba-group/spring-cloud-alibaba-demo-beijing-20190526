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

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@RestController
public class ProviderController {

    private final JdbcTemplate jdbcTemplate;

    public ProviderController(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @GetMapping("/flow")
    public String flow() {
        return "flow";
    }

    @GetMapping("/error")
    public String error() {
        throw new RuntimeException("custom");
    }

    @GetMapping("/rt")
    public String rt() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            // ignore
        }
        return "sleep 1s";
    }

    @GetMapping("/saveA")
    public String saveA() {
        int result = jdbcTemplate.update("insert into tbl_a(tag) values ('testA') ");
        if(result == 1) {
            return "saveA success";
        } else {
            return "saveA fail";
        }
    }

}
