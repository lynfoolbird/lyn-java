package com.lynjava.ddd.api.cluster.assembler;

import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.domain.cluster.ClusterAR;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.BeanUtils;

/**
 * 使用MapStruct进行对象转换
 *
 * @author li
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ClusterMapper {

    public abstract ClusterAR toDO(ClusterInputDto clusterInputDto);

    public ClusterOutputDto toOutputDto(ClusterAR clusterAR) {
        ClusterOutputDto clusterOutputDto = new ClusterOutputDto();
        BeanUtils.copyProperties(clusterAR, clusterOutputDto);
        return clusterOutputDto;
    }
}
