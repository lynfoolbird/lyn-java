package com.lynjava.ddd.api.cluster;


import com.lynjava.ddd.api.cluster.dto.ClusterInputDto;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/clusters")
@Consumes({ MediaType.APPLICATION_JSON})
@Produces({ MediaType.APPLICATION_JSON})
@Valid
public interface IClusterApi {

    @GET
    @Path("/{id}")
    Object getCluster(@PathParam("id") String id, @QueryParam("type") String type);

    @POST
    @Path("")
    Object createCluster(ClusterInputDto clusterInputDto);

    @PATCH
    @Path("/{id}")
    Object updateCluster(@PathParam("id") String clusterId, ClusterInputDto clusterInputDto);

    @PUT
    @Path("/{id}")
    Object patchCluster(@PathParam("id") String clusterId, ClusterInputDto clusterInputDto);

    @DELETE
    @Path("/{id}")
    Object deleteCluster(@PathParam("id") String clusterId);
}
