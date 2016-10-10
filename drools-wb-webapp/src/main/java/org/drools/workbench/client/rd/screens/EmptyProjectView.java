package org.drools.workbench.client.rd.screens;

import com.google.gwt.user.client.Event;
import org.jboss.errai.common.client.dom.Anchor;
import org.jboss.errai.common.client.dom.Button;
import org.jboss.errai.common.client.dom.Heading;
import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.SinkNative;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;
import javax.inject.Named;

@Templated
public class EmptyProjectView implements EmptyProjectScreen.View, IsElement {

    private EmptyProjectScreen presenter;

    @Inject
    @DataField
    private Button newProject;

    @Inject
    @DataField
    private Button mortageDemo;

    @Inject
    @DataField
    private Button hrOnboardingDemo;

    @Inject
    @DataField
    private Button medicalAppDemo;


    @Named( "h1" )
    @Inject
    @DataField
    private Heading welcome;

    @Inject
    @DataField
    private Anchor newProjectLink;

    @Override
    public void init( EmptyProjectScreen presenter ) {
        this.presenter = presenter;
    }

    @SinkNative( Event.ONCLICK )
    @EventHandler( "newProject" )
    public void newProject( Event e ) {
        presenter.newProject();
    }

    @SinkNative( Event.ONCLICK )
    @EventHandler( "newProjectLink" )
    public void newProjectLink( Event e ) {
        presenter.newProject();
    }

    @SinkNative( Event.ONCLICK )
    @EventHandler( "mortageDemo" )
    public void mortageDemo( Event e ) {
        presenter.runDemo( "mortageDemo" );
    }

    @SinkNative( Event.ONCLICK )
    @EventHandler( "hrOnboardingDemo" )
    public void hrOnboardingDemo( Event e ) {
        presenter.runDemo( "hrOnboardingDemo" );
    }

    @SinkNative( Event.ONCLICK )
    @EventHandler( "medicalAppDemo" )
    public void medicalAppDemo( Event e ) {
        presenter.runDemo( "medicalAppDemo" );
    }


    @Override
    public void setup( String username ) {
        //TODO ederign i18n
        welcome.setInnerHTML( "Welcome " + username + "." );
    }
}