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

package org.drools.workbench.screens.drltext.backend.server.indexing;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.drools.workbench.screens.drltext.type.DRLResourceTypeDefinition;
import org.junit.Test;
import org.kie.workbench.common.services.refactoring.backend.server.BaseIndexingTest;
import org.kie.workbench.common.services.refactoring.backend.server.TestIndexer;
import org.kie.workbench.common.services.refactoring.backend.server.query.builder.SingleTermQueryBuilder;
import org.kie.workbench.common.services.refactoring.model.index.terms.ModuleRootPathIndexTerm;
import org.kie.workbench.common.services.refactoring.model.index.terms.valueterms.ValueResourceIndexTerm;
import org.kie.workbench.common.services.refactoring.service.ResourceType;
import org.uberfire.ext.metadata.backend.lucene.analyzer.FilenameAnalyzer;
import org.uberfire.ext.metadata.io.KObjectUtil;
import org.uberfire.java.nio.file.Path;

public class IndexRuleTest extends BaseIndexingTest<DRLResourceTypeDefinition> {

    @Test
    public void testIndexDrlRules() throws IOException, InterruptedException {
        //Add test files
        Path path = basePath.resolve("drl1.drl");
        String drl = loadText("drl1.drl");
        ioService().write(path, drl);

        path = basePath.resolve("drl2.drl");
        drl = loadText("drl2.drl");
        ioService().write(path, drl);

        Thread.sleep(5000); //wait for events to be consumed from jgit -> (notify changes -> watcher -> index) -> lucene index

        List<String> index = Arrays.asList(KObjectUtil.toKCluster(basePath.getFileSystem()).getClusterId());

        {
            final Query query = new SingleTermQueryBuilder(new ValueResourceIndexTerm("myRule", ResourceType.RULE))
                    .build();
            searchFor(index, query, 1);
        }

        {
            final Query query = new SingleTermQueryBuilder(
                    new ValueResourceIndexTerm("org.drools.workbench.screens.drltext.backend.server.indexing.classes.myRule", ResourceType.RULE))
                    .build();
            searchFor(index, query, 1);
        }
    }

    @Override
    protected TestIndexer getIndexer() {
        return new TestDrlFileIndexer();
    }

    @Override
    public Map<String, Analyzer> getAnalyzers() {
        return new HashMap<String, Analyzer>() {
            {
                put(ModuleRootPathIndexTerm.TERM, new FilenameAnalyzer());
            }
        };
    }

    @Override
    protected DRLResourceTypeDefinition getResourceTypeDefinition() {
        return new DRLResourceTypeDefinition();
    }

    @Override
    protected String getRepositoryName() {
        return this.getClass().getSimpleName();
    }
}
