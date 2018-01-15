/*
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drools.workbench.screens.dtablexls.backend.server.indexing;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.drools.compiler.compiler.DecisionTableFactory;
import org.drools.workbench.screens.dtablexls.type.DecisionTableXLSResourceTypeDefinition;
import org.kie.soup.project.datamodel.oracle.ModuleDataModelOracle;
import org.kie.workbench.common.services.datamodel.backend.server.service.DataModelService;
import org.kie.workbench.common.services.refactoring.backend.server.indexing.DefaultIndexBuilder;
import org.kie.workbench.common.services.refactoring.backend.server.indexing.drools.AbstractDrlFileIndexer;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.java.nio.file.Path;
import org.uberfire.java.nio.file.StandardOpenOption;

@ApplicationScoped
public class DecisionTableXLSFileIndexer extends AbstractDrlFileIndexer {

    @Inject
    private DataModelService dataModelService;

    @Inject
    protected DecisionTableXLSResourceTypeDefinition type;

    @Override
    public boolean supportsPath(final Path path) {
        return type.accept(Paths.convert(path));
    }

    @Override
    public DefaultIndexBuilder fillIndexBuilder(final Path path) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = ioService.newInputStream(path,
                                                   StandardOpenOption.READ);
            final String drl = DecisionTableFactory.loadFromInputStream(inputStream,
                                                                        null);

            return fillDrlIndexBuilder(path, drl);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // no-op
                }
            }
        }
    }

    @Override
    protected ModuleDataModelOracle getModuleDataModelOracle(final Path path) {
        return dataModelService.getModuleDataModel(Paths.convert(path));
    }
}
