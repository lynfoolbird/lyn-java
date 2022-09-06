package com.lynjava.ddd.api.router.impl;

import com.lynjava.ddd.api.router.IRouterApi;
import com.lynjava.ddd.app.router.appservice.RouterAppService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RouterApiImpl implements IRouterApi {

    @Inject
    private RouterAppService routerAppService;
}
