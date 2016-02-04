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
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.ovirt.engine.api.resource.DiskProfileResource;
import org.ovirt.engine.api.v3.V3Server;
import org.ovirt.engine.api.v3.types.V3DiskProfile;

@Produces({"application/xml", "application/json"})
public class V3DiskProfileServer extends V3Server<DiskProfileResource> {
    public V3DiskProfileServer(DiskProfileResource delegate) {
        super(delegate);
    }

    @GET
    public V3DiskProfile get() {
        return adaptGet(delegate::get);
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public V3DiskProfile update(V3DiskProfile profile) {
        return adaptUpdate(delegate::update, profile);
    }

    @DELETE
    public Response remove() {
        return adaptRemove(delegate::remove);
    }

    @Path("permissions")
    public V3AssignedPermissionsServer getPermissionsResource() {
        return new V3AssignedPermissionsServer(delegate.getPermissionsResource());
    }
}