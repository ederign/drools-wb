package org.drools.workbench.client.rd.screens;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import org.jboss.errai.common.client.dom.Button;
import org.jboss.errai.common.client.dom.Input;
import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.SinkNative;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

@Templated
public class ProjectsView implements ProjectsScreen.View, IsElement {

    private ProjectsScreen presenter;


    @Override
    public void init( ProjectsScreen presenter ) {
        this.presenter = presenter;

    }


}