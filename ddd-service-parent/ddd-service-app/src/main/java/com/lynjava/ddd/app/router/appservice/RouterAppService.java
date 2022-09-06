package com.lynjava.ddd.app.router.appservice;

import com.lynjava.ddd.app.router.assembler.RouterAssmbler;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RouterAppService {

    @Inject
    private RouterAssmbler routerAssmbler;
}

