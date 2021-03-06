/*
Copyright (c) 2016 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.ovirt.engine.api.v3.servers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.ovirt.engine.api.resource.InstanceTypeResource;
import org.ovirt.engine.api.v3.V3Server;
import org.ovirt.engine.api.v3.types.V3InstanceType;

@Produces({"application/xml", "application/json"})
public class V3InstanceTypeServer extends V3Server<InstanceTypeResource> {
    public V3InstanceTypeServer(InstanceTypeResource delegate) {
        super(delegate);
    }

    @GET
    public V3InstanceType get() {
        return adaptGet(getDelegate()::get);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public V3InstanceType update(V3InstanceType instanceType) {
        return adaptUpdate(getDelegate()::update, instanceType);
    }

    @DELETE
    public Response remove() {
        return adaptRemove(getDelegate()::remove);
    }

    @Path("nics")
    public V3InstanceTypeNicsServer getNicsResource() {
        return new V3InstanceTypeNicsServer(getDelegate().getNicsResource());
    }

    @Path("watchdogs")
    public V3InstanceTypeWatchdogsServer getWatchdogsResource() {
        return new V3InstanceTypeWatchdogsServer(getDelegate().getWatchdogsResource());
    }

    @Path("graphicsconsoles")
    public V3GraphicsConsolesServer getGraphicsConsolesResource() {
        return new V3GraphicsConsolesServer(getDelegate().getGraphicsConsolesResource());
    }

    @Path("creation_status/{oid}")
    public V3CreationServer getCreationResource(@PathParam("oid") String oid) {
        return new V3CreationServer(getDelegate().getCreationResource(oid));
    }
}
