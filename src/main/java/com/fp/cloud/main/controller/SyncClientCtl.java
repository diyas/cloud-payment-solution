package com.fp.cloud.main.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sync")
@Api(value = "/sync", tags = "Syncronize")
public class SyncClientCtl {
}
