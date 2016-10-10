package org.drools.workbench.client.rd.screens;

import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Templated
public class ProjectView implements ProjectScreen.View, IsElement {

    private ProjectScreen presenter;


    @Override
    public void init( ProjectScreen presenter ) {
        this.presenter = presenter;

    }


}