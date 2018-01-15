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
package org.drools.workbench.screens.guided.rule.backend.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.guvnor.common.services.backend.file.FileDiscoveryService;
import org.jboss.errai.security.shared.api.identity.User;
import org.kie.workbench.common.services.backend.file.DSLFileFilter;
import org.kie.workbench.common.services.backend.file.GlobalsFileFilter;
import org.kie.workbench.common.services.shared.project.KieModuleService;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.backend.vfs.Path;
import org.uberfire.io.IOService;
import org.uberfire.rpc.SessionInfo;

/**
 * Common methods shared between GuidedRuleEditorServiceImpl and GuidedRuleEditorRenameHelper
 */
@ApplicationScoped
public class GuidedRuleEditorServiceUtilities {

    private static final GlobalsFileFilter FILTER_GLOBALS = new GlobalsFileFilter();

    private static final DSLFileFilter FILTER_DSLS = new DSLFileFilter();

    @Inject
    private User identity;

    @Inject
    private SessionInfo sessionInfo;

    @Inject
    @Named("ioStrategy")
    private IOService ioService;

    @Inject
    private FileDiscoveryService fileDiscoveryService;

    @Inject
    private KieModuleService moduleService;

    /**
     * Load DSL definitions held in the Package relating to the provide Path
     * @param path
     * @return
     */
    public String[] loadDslsForPackage(final Path path) {
        final List<String> dsls = new ArrayList<String>();
        final Path packagePath = moduleService.resolvePackage(path).getPackageMainResourcesPath();
        final org.uberfire.java.nio.file.Path nioPackagePath = Paths.convert(packagePath);
        final Collection<org.uberfire.java.nio.file.Path> dslPaths = fileDiscoveryService.discoverFiles(nioPackagePath,
                                                                                                        FILTER_DSLS);
        for (final org.uberfire.java.nio.file.Path dslPath : dslPaths) {
            final String dslDefinition = ioService.readAllString(dslPath);
            dsls.add(dslDefinition);
        }
        final String[] result = new String[dsls.size()];
        return dsls.toArray(result);
    }

    /**
     * Load Global definitions held in the Package relating to the provide Path
     * @param path
     * @return
     */
    public List<String> loadGlobalsForPackage(final Path path) {
        final List<String> globals = new ArrayList<String>();
        final Path packagePath = moduleService.resolvePackage(path).getPackageMainResourcesPath();
        final org.uberfire.java.nio.file.Path nioPackagePath = Paths.convert(packagePath);
        final Collection<org.uberfire.java.nio.file.Path> globalPaths = fileDiscoveryService.discoverFiles(nioPackagePath,
                                                                                                           FILTER_GLOBALS);
        for (final org.uberfire.java.nio.file.Path globalPath : globalPaths) {
            final String globalDefinition = ioService.readAllString(globalPath);
            globals.add(globalDefinition);
        }
        return globals;
    }
}
