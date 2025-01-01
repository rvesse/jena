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

package org.apache.jena.tdb1.graph;

import org.apache.jena.graph.Graph ;
import org.apache.jena.graph.Node ;
import org.apache.jena.sparql.core.AbstractTestGraphOverDatasetGraph ;
import org.apache.jena.sparql.core.DatasetGraph ;
import org.apache.jena.tdb1.TDB1Factory;

/** This is the view-graph test suite run over a TDB DatasetGraph to check compatibility */
public class TestGraphOverDatasetTDB1 extends AbstractTestGraphOverDatasetGraph
{
    @Override
    protected DatasetGraph createBaseDSG() { return TDB1Factory.createDatasetGraph() ; }
    
    @Override
    protected Graph makeNamedGraph(DatasetGraph dsg, Node gn)
    {
        return dsg.getGraph(gn) ;
    }

    @Override
    protected Graph makeDefaultGraph(DatasetGraph dsg)
    {
        return  dsg.getDefaultGraph() ;
    }

}

