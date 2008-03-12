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
package rat.header;

import junit.framework.TestCase;

public class ArrayCharFilterTest extends TestCase {

    private static final char[] filtered = {'d', 'o', 'a'};
    ArrayCharFilter filter;
    
    protected void setUp() throws Exception {
        super.setUp();
        filter = new ArrayCharFilter(filtered);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testIsFilteredOut() {
        assertTrue(filter.isFilteredOut('a'));
        assertFalse(filter.isFilteredOut('b'));
        assertFalse(filter.isFilteredOut('c'));
        assertTrue(filter.isFilteredOut('d'));
        assertFalse(filter.isFilteredOut('e'));
        assertFalse(filter.isFilteredOut('f'));
    }

}
