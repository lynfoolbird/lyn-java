package com.lynjava.ddd.api.cluster.assembler;

import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.domain.cluster.ClusterAR;
import org.springframework.beans.BeanUtils;

import javax.inject.Named;

/**
 * 功能：inputDto、outputDto与DO相互转换
 * 用MapStruct工具，深拷贝or浅拷贝
 */
@Named
public class ClusterAssembler {

    public ClusterAR toDO(ClusterInputDto clusterInputDto) {
        ClusterAR clusterAR = ClusterAR.builder().build();
        BeanUtils.copyProperties(clusterInputDto, clusterAR);
        return clusterAR;

    }

    public ClusterOutputDto toOutputDto(ClusterAR clusterAR) {
        ClusterOutputDto clusterOutputDto = new ClusterOutputDto();
        BeanUtils.copyProperties(clusterAR, clusterOutputDto);
        return clusterOutputDto;
    }
}
