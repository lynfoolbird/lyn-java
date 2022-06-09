package com.lynjava.ddd.external.iam;

import com.lynjava.ddd.common.annotation.BlueProfile;
import com.lynjava.ddd.domain.external.iam.IamExternalService;

import javax.inject.Named;

@Named
@BlueProfile
public class BlueIamExternalServiceImpl implements IamExternalService {
    @Override
    public void printIam() {
        System.out.println("print blue iam");
    }
}
