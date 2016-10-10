/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.workbench.client.rd.screens;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import org.guvnor.common.services.project.model.Project;
import org.guvnor.structure.organizationalunit.OrganizationalUnit;
import org.guvnor.structure.organizationalunit.OrganizationalUnitService;
import org.guvnor.structure.repositories.Repository;
import org.guvnor.structure.repositories.RepositoryService;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.kie.workbench.common.services.shared.project.KieProjectService;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.mvp.UberElement;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.PlaceRequest;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@WorkbenchScreen( identifier = "ProjectsScreen" )
public class ProjectsScreen {


    public interface View extends UberElement<ProjectsScreen> {
    }

    @Inject
    private View view;


    //ederign DUPLICATED FROM  NewProjectScreen
    @Inject
    private Caller<OrganizationalUnitService> organizationalUnitService;

    @Inject
    private Caller<KieProjectService> projectServiceCaller;

    @Inject
    private Caller<RepositoryService> repositoryService;


    private Collection<OrganizationalUnit> organizationalUnits;
    private Optional<OrganizationalUnit> defaultOU;
    private Repository repository;


    @AfterInitialization
    public void load() {


    }

    @OnStartup
    public void onStartup( final PlaceRequest place ) {

    }

    @OnOpen
    public void onOpen() {
        organizationalUnitService.call( new RemoteCallback<Collection<OrganizationalUnit>>() {
            @Override
            public void callback( Collection<OrganizationalUnit> organizationalUnits ) {
                ProjectsScreen.this.organizationalUnits = organizationalUnits;
                ProjectsScreen.this.defaultOU = organizationalUnits.stream().findFirst();
                GWT.log( "DEFAULT OU " + defaultOU.get().getName() );
                getProjects();

            }
        } ).getOrganizationalUnits();

    }

    //ederign FIXME
    private void getProjects() {
        repositoryService.call( new RemoteCallback<Collection<Repository>>() {
            @Override
            public void callback( Collection<Repository> repositories ) {
                GWT.log( repositories.size() + ":." );
                ProjectsScreen.this.repository = repositories.stream().findFirst().get();
                projectServiceCaller.call( getSuccessProjectsCallback() ).getAllProjects( repository, "master" );

            }
        }, new ErrorCallback<Repository>() {
            @Override
            public boolean error( Repository repository, Throwable throwable ) {
                Window.alert( "TODO GET REPOSITORY" );
                return false;
            }
        } ).getRepositories();
    }

    private RemoteCallback<Set<Project>> getSuccessProjectsCallback() {
        return new RemoteCallback<Set<Project>>() {
            @Override
            public void callback( Set<Project> projects ) {
                GWT.log( "success" );
                GWT.log( projects.size() + "" );
                projects.stream().forEach( p -> GWT.log( p.getProjectName() ) );
            }
        };
    }


    @WorkbenchPartTitle
    public String getTitle() {
        return "ProjectsScreen";
    }

    @WorkbenchPartView
    public UberElement<ProjectsScreen> getView() {
        return view;
    }
}
