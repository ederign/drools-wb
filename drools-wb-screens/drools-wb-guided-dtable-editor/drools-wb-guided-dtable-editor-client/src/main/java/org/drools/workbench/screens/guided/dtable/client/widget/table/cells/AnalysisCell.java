/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.workbench.screens.guided.dtable.client.widget.table.cells;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.drools.workbench.models.guided.dtable.shared.model.Analysis;

/**
 * A Cell that renders it's corresponding row index number only
 */
public class AnalysisCell extends AbstractCell<Analysis> {

    public AnalysisCell() {
        // Good citizen: AbstractCell does not initialise an empty set of
        // consumed events
        super( "" );
    }

    @Override
    public void render( Context context,
                        Analysis analysis,
                        SafeHtmlBuilder sb ) {
        sb.append( SafeHtmlUtils.fromTrustedString( analysis.toHtmlString() ) );
    }

}
