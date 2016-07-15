/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.authorization.resource;

import org.apache.nifi.authorization.Resource;

/**
 * Authorizable for policies of an Authorizable.
 */
public class AccessPolicyAuthorizable implements Authorizable {
    final Authorizable authorizable;

    public AccessPolicyAuthorizable(Authorizable authorizable) {
        this.authorizable = authorizable;
    }

    @Override
    public Authorizable getParentAuthorizable() {
        if (authorizable.getParentAuthorizable() == null) {
            return new Authorizable() {
                @Override
                public Authorizable getParentAuthorizable() {
                    return null;
                }

                @Override
                public Resource getResource() {
                    return ResourceFactory.getPoliciesResource();
                }
            };
        } else {
            return new AccessPolicyAuthorizable(authorizable.getParentAuthorizable());
        }
    }

    @Override
    public Resource getResource() {
        return ResourceFactory.getPolicyResource(authorizable.getResource());
    }
}