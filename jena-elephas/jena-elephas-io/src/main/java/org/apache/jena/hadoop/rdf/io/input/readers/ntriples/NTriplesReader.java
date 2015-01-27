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

package org.apache.jena.hadoop.rdf.io.input.readers.ntriples;

import java.util.Iterator;

import org.apache.jena.hadoop.rdf.io.input.readers.AbstractLineBasedTripleReader;
import org.apache.jena.riot.lang.LangNTriples;
import org.apache.jena.riot.system.ParserProfile;
import org.apache.jena.riot.tokens.Tokenizer;
import org.apache.jena.riot.tokens.TokenizerFactory;

import com.hp.hpl.jena.graph.Triple;

/**
 * A record reader for NTriples
 * 
 * 
 * 
 */
public class NTriplesReader extends AbstractLineBasedTripleReader {

    @Override
    protected Iterator<Triple> getTriplesIterator(Tokenizer tokenizer, ParserProfile profile) {
        return new LangNTriples(tokenizer, profile, null);
    }

    @Override
    protected Tokenizer getTokenizer(String line) {
        return TokenizerFactory.makeTokenizerString(line);
    }
}
