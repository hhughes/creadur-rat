/*
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 */ 
package rat.document.impl;

import java.io.IOException;
import java.io.Reader;

import rat.document.CompositeDocumentException;
import rat.document.IDocument;

/**
 * Composed from a set of archived documents.
 *
 */
public abstract class CompositeDocument implements IDocument {

    private final String name;
    
    public CompositeDocument(final String name) {
        super();
        this.name = name;
    }

    public Reader reader() throws IOException {
        throw new CompositeDocumentException();
    }

    public String getName() {
        return name;
    }

}
