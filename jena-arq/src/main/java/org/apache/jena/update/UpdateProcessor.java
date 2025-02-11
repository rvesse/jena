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

package org.apache.jena.update;

import org.apache.jena.sparql.util.Context;

/**
 * An instance of a execution of an UpdateRequest.
 * Applies to UpdateExec (GPI) and UpdateExecution (API).
 */
public interface UpdateProcessor
{
    /** Execute */
    public void execute() ;

    /** Attempt to asynchronously abort an update execution. */
    public default void abort() { }

    /** Returns the processor's context. Null if there is none. */
    public default Context getContext() { return null ; }
}
