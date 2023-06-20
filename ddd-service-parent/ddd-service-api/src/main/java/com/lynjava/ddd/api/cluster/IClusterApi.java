package com.lynjava.ddd.api.cluster;


import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.api.shared.Result;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 接口层
 */
@Path("/clusters")
@Consumes({ MediaType.APPLICATION_JSON})
@Produces({ MediaType.APPLICATION_JSON})
@Validated
public interface IClusterApi {

    /**
     * 查询单个集群详情
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    Result<ClusterOutputDto> getCluster(@PathParam("id") Integer id);

    /**
     * 根据条件分页查询列表
     * @param curPage
     * @param pageSize
     * @param type
     * @return
     */
    @GET
    @Path("/")
    Result listByPage(@QueryParam("curPage") Integer curPage,
                      @Max(value = 500, message = "pageSize must less than 500")
                      @QueryParam("pageSize") Integer pageSize,
                                              @QueryParam("") ClusterInputDto queryParam);

    /**
     * 新增集群
     * @param clusterInputDto
     * @return
     */
    @POST
    @Path("/")
    Result createCluster(@Valid ClusterInputDto clusterInputDto);

    /**
     * 修改集群
     * @param clusterId
     * @param inputDto
     * @return
     */
    @PUT
    @Path("/{id}")
    Result updateCluster(@PathParam("id") Integer clusterId, ClusterInputDto inputDto);

    /**
     * 多tab修改集群patch
     *
     * @param clusterId
     * @param type
     * @param patchObject
     * @return
     */
    @PATCH
    @Path("/{id}/type/{type}")
    Result patchCluster(@PathParam("id") Integer clusterId, @PathParam("type") String type,
                        @RequestBody String patchObject);

    /**
     * 删除单个集群
     * @param clusterId
     * @return
     */
    @DELETE
    @Path("/{id}")
    Result deleteCluster(@PathParam("id") Integer clusterId);

    /**
     * 批量删除集群
     * @param clusterInputDtoList
     * @return
     */
    @DELETE
    @Path("/")
    Result batchDeleteCluster(List<ClusterInputDto> clusterInputDtoList);
}
