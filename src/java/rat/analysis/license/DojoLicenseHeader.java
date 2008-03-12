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
package rat.analysis.license;

import rat.analysis.Claims;
import rat.analysis.IHeaderMatcher;
import rat.analysis.RatHeaderAnalysisException;
import rat.license.ModifiedBSDLicenseFamily;
import rat.report.claim.IClaimReporter;


public class DojoLicenseHeader extends BaseLicense implements IHeaderMatcher {
    
    private static final String LICENSE_URL 
    = "http://dojotoolkit.org/community/licensing.shtml";
    
    //  Copyright (c) 2004-2006, The Dojo Foundation
    // All Rights Reserved.
    //
    // Licensed under the Academic Free License version 2.1 or above OR the
    // modified BSD license. For more information on Dojo licensing, see:
    //
    //    http://dojotoolkit.org/community/licensing.shtml

    public DojoLicenseHeader() {
        // TODO: support for dual licensing
        // TODO: support for or higher clauses
        super(Claims.DOJO, ModifiedBSDLicenseFamily.MODIFIED_BSD_LICENSE_NAME, "Dual license AFL/BSD");
    }


    public void reset() {

    }

    public boolean match(String subject, String line, IClaimReporter reporter) throws RatHeaderAnalysisException {
        final boolean result = matches(line);
        if (result) {
            reportOnLicense(subject, reporter);
        }
        return result;
    }

    boolean matches(String line) {
        boolean result = (line != null && line.indexOf(LICENSE_URL) != -1);
        return result;
    }
    
}
