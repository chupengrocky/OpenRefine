/*

Copyright 2010, Google Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

    * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above
copyright notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
    * Neither the name of Google Inc. nor the names of its
contributors may be used to endorse or promote products derived from
this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,           
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY           
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.openrefine.commands.recon;

import javax.servlet.http.HttpServletRequest;

import org.openrefine.browsing.EngineConfig;
import org.openrefine.commands.EngineDependentCommand;
import org.openrefine.model.AbstractOperation;
import org.openrefine.model.Project;
import org.openrefine.model.Recon;
import org.openrefine.model.ReconCandidate;
import org.openrefine.model.Recon.Judgment;
import org.openrefine.operations.recon.ReconJudgeSimilarCellsOperation;

public class ReconJudgeSimilarCellsCommand extends EngineDependentCommand {

    @Override
    protected AbstractOperation createOperation(
            Project project, HttpServletRequest request, EngineConfig engineConfig) throws Exception {
        
        String columnName = request.getParameter("columnName");
        String similarValue = request.getParameter("similarValue");
        Judgment judgment = Recon.stringToJudgment(request.getParameter("judgment"));
        
        ReconCandidate match = null;
        String id = request.getParameter("id");
        if (id != null) {
            String scoreString = request.getParameter("score");
            
            match = new ReconCandidate(
                id,
                request.getParameter("name"),
                request.getParameter("types").split(","),
                scoreString != null ? Double.parseDouble(scoreString) : 100
            );
        }
        
        String shareNewTopics = request.getParameter("shareNewTopics");
        
        return new ReconJudgeSimilarCellsOperation(
            engineConfig, 
            columnName,
            similarValue,
            judgment,
            match,
            "true".equals(shareNewTopics)
        );
    }
}