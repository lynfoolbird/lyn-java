package com.lynjava.ddd.external.iam;

import com.lynjava.ddd.common.annotation.RedProfile;
import com.lynjava.ddd.domain.external.iam.IamExternalService;

import javax.inject.Named;

@Named
@RedProfile
public class RedIamExternalServiceImpl implements IamExternalService {
    @Override
    public void printIam() {
        System.out.println("print red iam");
    }
}
