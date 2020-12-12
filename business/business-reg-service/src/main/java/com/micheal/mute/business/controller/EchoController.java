package com.micheal.mute.business.controller;

import com.micheal.mute.provider.api.EchoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/19 16:41
 * @Description
 */
@RestController
@RequestMapping(value = "echo")
public class EchoController {

    @Reference(version = "1.0.0")
    private EchoService echoService;

    @GetMapping(value = "{string}")
    public String echo(@PathVariable String string) {
        return echoService.echo(string);
    }
}
