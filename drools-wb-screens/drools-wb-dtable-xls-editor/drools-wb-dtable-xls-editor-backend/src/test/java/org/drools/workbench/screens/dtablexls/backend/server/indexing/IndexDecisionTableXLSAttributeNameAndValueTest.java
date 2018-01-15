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
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.drools.workbench.screens.dtablexls.type.DecisionTableXLSResourceTypeDefinition;
import org.junit.Test;
import org.kie.workbench.common.services.refactoring.backend.server.BaseIndexingTest;
import org.kie.workbench.common.services.refactoring.backend.server.TestIndexer;
import org.kie.workbench.common.services.refactoring.model.index.terms.valueterms.ValueIndexTerm;
import org.kie.workbench.common.services.refactoring.model.index.terms.valueterms.ValueSharedPartIndexTerm;
import org.kie.workbench.common.services.refactoring.service.PartType;
import org.uberfire.ext.metadata.io.KObjectUtil;
import org.uberfire.java.nio.file.Path;

public class IndexDecisionTableXLSAttributeNameAndValueTest extends BaseIndexingTest<DecisionTableXLSResourceTypeDefinition> {

    @Test
    public void testIndexDecisionTableXLSAttributeNameAndValue() throws IOException, InterruptedException {
        //Add test files
        final Path path1 = loadXLSFile(basePath,
                                       "dtable1.xls");
        final Path path2 = loadXLSFile(basePath,
                                       "dtable2.xls");

        Thread.sleep(5000); //wait for events to be consumed from jgit -> (notify changes -> watcher -> index) -> lucene index

        List<String> index = Arrays.asList(KObjectUtil.toKCluster(basePath.getFileSystem()).getClusterId());

        //This simply checks whether there is a Rule Attribute "ruleflow-group" with a Rule Attribute Value "myRuleflowGroup"
        {
            final BooleanQuery.Builder query = new BooleanQuery.Builder();
            ValueIndexTerm valTerm = new ValueSharedPartIndexTerm("myruleflowgroup", PartType.RULEFLOW_GROUP);
            query.add(new TermQuery(new Term(valTerm.getTerm(), valTerm.getValue())), BooleanClause.Occur.MUST);
            searchFor(index, query.build(), 1, path1);
        }
    }

    @Override
    protected TestIndexer getIndexer() {
        return new TestDecisionTableXLSFileIndexer();
    }

    @Override
    protected DecisionTableXLSResourceTypeDefinition getResourceTypeDefinition() {
        return new DecisionTableXLSResourceTypeDefinition();
    }

    @Override
    protected String getRepositoryName() {
        return this.getClass().getSimpleName();
    }

    private Path loadXLSFile(final Path basePath,
                             final String fileName) throws IOException {
        final Path path = basePath.resolve(fileName);
        final InputStream is = this.getClass().getResourceAsStream(fileName);
        final OutputStream os = ioService().newOutputStream(path);
        IOUtils.copy(is,
                     os);
        os.flush();
        os.close();
        return path;
    }
}
