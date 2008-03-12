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

import java.io.BufferedReader;
import java.io.File;
import java.io.Reader;

import junit.framework.TestCase;
import rat.document.UnreadableArchiveException;

public class SingularFileDocumentTest extends TestCase {

    MonolithicFileDocument document;
    File file;
    
    protected void setUp() throws Exception {
        super.setUp();
        file = new File("src/test/elements/Source.java");
        document = new MonolithicFileDocument(file);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testReadArchive() throws Exception {
        try {
            document.readArchive();
            fail("Source is not archive: Exception should have been thrown");
        } catch (UnreadableArchiveException e) {
            // expected
        }
    }

    public void testReader() throws Exception {
        Reader reader = document.reader();
        assertNotNull("Reader should be returned", reader);
        assertEquals("First file line expected", "package elements;", 
                 new BufferedReader(reader).readLine());
    }

    public void testGetName() {
        final String name = document.getName();
        assertNotNull("Name is set", name);
        assertEquals("Name is filename", "src/test/elements/Source.java", name);
    }

}
