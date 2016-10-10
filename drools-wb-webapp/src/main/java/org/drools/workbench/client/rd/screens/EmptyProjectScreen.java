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
import org.jboss.errai.security.shared.api.identity.User;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.mvp.UberElement;
import org.uberfire.mvp.impl.DefaultPlaceRequest;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@WorkbenchScreen( identifier = "EmptyProjectScreen" )
public class EmptyProjectScreen {


    public interface View extends UberElement<EmptyProjectScreen> {

        void setup( String username );
    }

    @Inject
    private View view;

    @Inject
    private User user;

    @Inject
    private PlaceManager placeManager;

    @PostConstruct
    public void setup() {
        view.init( this );
        view.setup( user.getIdentifier() );
    }


    public void newProject() {
        placeManager.goTo( new DefaultPlaceRequest( "NewProjectScreen" ) );
    }

    public void runDemo( String demoID ) {
        GWT.log( "DEMO " + demoID );
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Empty Project Screen";
    }

    @WorkbenchPartView
    public UberElement<EmptyProjectScreen> getView() {
        return view;
    }
}
