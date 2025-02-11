/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jena.fuseki.server;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletContext;

import org.apache.jena.atlas.lib.Registry;
import org.apache.jena.atlas.logging.Log;
import org.apache.jena.fuseki.Fuseki;
import org.apache.jena.fuseki.FusekiException;
import org.apache.jena.fuseki.servlets.HttpAction;

/**
 * Registry of (dataset name, {@link DataAccessPoint}).
 * In addition, registered {@link DataAccessPoint} are added to the metrics.
 */
public class DataAccessPointRegistry extends Registry<String, DataAccessPoint>
{
    public DataAccessPointRegistry() {}

    public DataAccessPointRegistry(DataAccessPointRegistry other) {
        other.forEach((_name, accessPoint)->register(accessPoint));
    }

    // Preferred way to register. Other method for legacy.
    public void register(DataAccessPoint accessPt) {
        String name = accessPt.getName();
        if ( isRegistered(name) )
            throw new FusekiException("Already registered: "+name);
        super.put(name, accessPt);
    }

    /**
     * Collection of the {@link DataAccessPoint DataAccessPoints}. This is a new list
     * generated from the registry contents and not still connected to the registry.
     * Registry changes will not interfere with iteration over the list.
     * {@link DataAccessPoint DataAccessPoints} can not be registered twice under
     * different names (the same dataset can be via different
     * {@link DataAccessPoint DataAccessPoints} so the list has no duplicates.
     * There is no defined order to the list.
     */
    public List<DataAccessPoint> accessPoints() {
        List<DataAccessPoint> accessPoints = new ArrayList<>(size());
        // Copy for safety.
        forEach((_name, accessPoint) -> accessPoints.add(accessPoint));
        return accessPoints;
    }

    @Override
    public DataAccessPoint get(String key) {
        return super.get(key);
    }

    // Debugging
    public void print() {
        print(null);
    }

    // Debugging
    public void print(String string) {
        System.out.flush();
        if ( string == null )
            string = "DataAccessPointRegistry";
        System.out.println("== "+string);
        this.forEach((k,ref)->{
            System.out.printf("  (key=%s, ref=%s)\n", k, ref.getName());
            ref.getDataService().getOperations().forEach((op)->{
                ref.getDataService().getEndpoints(op).forEach(ep->{
                    System.out.printf("    %-10s @ \"%s\"\n", op, ep.getName());
                });
            });
        });
    }

    /** The server DataAccessPointRegistry is held in the ServletContext.
     * <p>
     * Reload may change this object for another one. Therefore, code should obtain the
     * DataAccessPointRegistry once per operation.
     * <p>
     * Each request, has a stable {@link HttpAction#getDataAccessPointRegistry()}.
     * <p>Getting the {@link DataAccessPointRegistry} is atomic.
     */
    public static DataAccessPointRegistry get(ServletContext cxt) {
        DataAccessPointRegistry registry = (DataAccessPointRegistry)cxt.getAttribute(Fuseki.attrNameRegistry);
        if ( registry == null )
            Log.warn(DataAccessPointRegistry.class, "No data access point registry for ServletContext");
        return registry;
    }

    /**
     * Set or change the {@link DataAccessPointRegistry}.
     * This is atomic. (In Jetty, it is backed by a ConcurrentHashMap).
     */
    public static void set(ServletContext cxt, DataAccessPointRegistry registry) {
        cxt.setAttribute(Fuseki.attrNameRegistry, registry);
    }
}
