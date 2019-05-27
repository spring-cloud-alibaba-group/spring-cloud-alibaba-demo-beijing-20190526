Sentinel Dashboard: https://github.com/alibaba/Sentinel/releases/download/1.6.0/sentinel-dashboard-1.6.0.jar

Nacos Server: https://github.com/alibaba/nacos/releases/download/1.0.0/nacos-server-1.0.0.zip

RocketMQ: https://www.apache.org/dyn/closer.cgi?path=rocketmq/4.5.1/rocketmq-all-4.5.1-bin-release.zip

Seata: https://github.com/seata/seata/releases/download/v0.4.2/fescar-server-0.4.2.zip

Nacos Configuration:

datId: sc-provider.properties, group: DEFAULT_GROUP

```properties
custom.location=beijing
custom.framework.name=spring-cloud-alibaba
default.message=hello beijing!
```

datId: sentinel-degrade-rule, group: DEFAULT_GROUP
```json
[
  {
    "resource": "GET:http://sc-provider/rt",
    "count": 20.0,
    "grade": 0,
    "timeWindow": 30
  },
  {
    "resource": "GET:http://sc-provider/error",
    "count": 0.5,
    "grade": 1,
    "timeWindow": 30
  }
]
```

datId: sentinel-flow-rule, group: DEFAULT_GROUP

```json
[
  {
    "resource": "/flow",
    "controlBehavior": 0,
    "count": 10,
    "grade": 1,
    "limitApp": "default",
    "strategy": 0
  }
]
```