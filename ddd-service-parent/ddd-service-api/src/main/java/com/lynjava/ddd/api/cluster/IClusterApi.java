package com.lynjava.ddd.api.cluster;


import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;
import com.lynjava.ddd.api.cluster.dto.ClusterOutputDto;
import com.lynjava.ddd.api.shared.Result;

import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 接口层
 */
@Path("/clusters")
@Consumes({ MediaType.APPLICATION_JSON})
@Produces({ MediaType.APPLICATION_JSON})
@Valid
public interface IClusterApi {

    @GET
    @Path("/{id}")
    Result<ClusterOutputDto> getCluster(@PathParam("id") String id);

    @GET
    @Path("/")
    Result<List<ClusterOutputDto>> listByPage(@QueryParam("curPage") Integer curPage,
                                              @QueryParam("pageSize") Integer pageSize,
                                              @QueryParam("type") String type);
    @POST
    @Path("/")
    Result createCluster(ClusterInputDto clusterInputDto);

    @PUT
    @Path("/{id}")
    Result updateCluster(@PathParam("id") String clusterId, ClusterInputDto inputDto);

    @PATCH
    @Path("/{id}/type/{type}")
    Result patchCluster(@PathParam("id") String clusterId, @PathParam("type") String type,
                        @RequestBody String patchObject);

    @DELETE
    @Path("/{id}")
    Result deleteCluster(@PathParam("id") String clusterId);

    @DELETE
    @Path("/")
    Result batchDeleteCluster(List<ClusterInputDto> clusterInputDtoList);
}
