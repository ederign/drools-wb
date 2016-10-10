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
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.mvp.UberElement;
import org.uberfire.lifecycle.OnOpen;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.PlaceRequest;

import javax.inject.Inject;

@WorkbenchScreen( identifier = "ProjectScreen" )
public class ProjectScreen {


    public interface View extends UberElement<ProjectScreen> {
    }

    @Inject
    private View view;

    @AfterInitialization
    public void load() {

    }

    @OnStartup
    public void onStartup( final PlaceRequest place ) {
        GWT.log("on startup");

    }

    @OnOpen
    public void onOpen() {
        GWT.log("on open");
    }



    @WorkbenchPartTitle
    public String getTitle() {
        return "ProjectScreen";
    }

    @WorkbenchPartView
    public UberElement<ProjectScreen> getView() {
        return view;
    }
}
