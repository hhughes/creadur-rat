package org.apache.rat.analysis;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.io.IOUtils;
import org.apache.rat.analysis.IHeaderMatcher.State;
import org.apache.rat.api.Document;
import org.apache.rat.api.MetaData;
import org.apache.rat.license.ILicense;

/**
 * <p>Reads from a stream to check license.</p>
 * <p><strong>Note</strong> that this class is not thread safe.</p> 
 */
class HeaderCheckWorker {

    public static final int DEFAULT_NUMBER_OF_RETAINED_HEADER_LINES = 50;

    private final int numberOfRetainedHeaderLines;
    private final BufferedReader reader;
    private final ILicense license;
    private final Document document;

    private int headerLinesToRead;
    private boolean finished = false;

    /**
     * Convenience constructor wraps given <code>Reader</code>
     * in a <code>BufferedReader</code>.
     * @param reader a <code>Reader</code> for the content, not null
     * @param name the name of the checked content, possibly null
     */
    public HeaderCheckWorker(Reader reader, final ILicense license, final Document name) {
        this(new BufferedReader(reader), license, name);
    }

    public HeaderCheckWorker(BufferedReader reader, final ILicense license,
            final Document name) {
        this(reader, DEFAULT_NUMBER_OF_RETAINED_HEADER_LINES, license, name);
    }

    public HeaderCheckWorker(BufferedReader reader, int numberOfRetainedHeaderLine, final ILicense license,
            final Document name) {
        this.reader = reader;
        this.numberOfRetainedHeaderLines = numberOfRetainedHeaderLine;
        this.license = license;
        this.document = name;
    }

    public boolean isFinished() {
        return finished;
    }

    public void read() throws RatHeaderAnalysisException {
        if (!finished) {
            final StringBuilder headers = new StringBuilder();
            headerLinesToRead = numberOfRetainedHeaderLines;
            try {
                while(readLine(headers)) {
                    // do nothing
                }
                if (!license.finalizeState().asBoolean()) {
                    final String notes = headers.toString();
                    final MetaData metaData = document.getMetaData();
                    metaData.set(new MetaData.Datum(MetaData.RAT_URL_HEADER_SAMPLE, notes));
                    metaData.set(new MetaData.Datum(MetaData.RAT_URL_HEADER_CATEGORY, MetaData.RAT_LICENSE_FAMILY_CATEGORY_VALUE_UNKNOWN));
                    metaData.set(MetaData.RAT_LICENSE_FAMILY_NAME_DATUM_UNKNOWN);
                }
            } catch (IOException e) {
                throw new RatHeaderAnalysisException("Cannot read header for " + document, e);
            }
            IOUtils.closeQuietly(reader);
            license.reset();
        }
        finished = true;
    }

    boolean readLine(StringBuilder headers) throws IOException, RatHeaderAnalysisException {
        String line = reader.readLine();
        boolean result = line != null;
        if (result) {
            if (headerLinesToRead-- > 0) {
                headers.append(line);
                headers.append('\n');
            }
            switch (license.matches(line)) {
            case t:
                document.getMetaData().reportOnLicense(license);
                result = false;
                break;
            case f:
            case i:
                result = true;
                break;
            }
        }
        return result;
    }
}
