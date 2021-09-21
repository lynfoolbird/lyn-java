package com.lynjava.ddd.app.cluster.assmbler;

import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.domain.cluster.ClusterAR;
import org.springframework.beans.BeanUtils;

import javax.inject.Named;

/**
 * 功能：inputDto、outputDto与DO相互转换
 */
@Named
public class ClusterAssmbler {

    public ClusterAR toDO(ClusterInputDto clusterInputDto) {
        ClusterAR ClusterAR = new ClusterAR();
        BeanUtils.copyProperties(clusterInputDto, ClusterAR);
        return ClusterAR;

    }

    public ClusterOutputDto toOutputDto(ClusterAR clusterAR) {
        ClusterOutputDto clusterOutputDto = new ClusterOutputDto();
        BeanUtils.copyProperties(clusterAR, clusterOutputDto);
        return clusterOutputDto;
    }
}
