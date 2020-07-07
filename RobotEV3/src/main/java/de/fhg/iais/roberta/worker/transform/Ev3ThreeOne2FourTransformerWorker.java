package de.fhg.iais.roberta.worker.transform;

import de.fhg.iais.roberta.bean.NewUsedHardwareBean;
import de.fhg.iais.roberta.components.ConfigurationAst;
import de.fhg.iais.roberta.components.Project;
import de.fhg.iais.roberta.visitor.ITransformerVisitor;
import de.fhg.iais.roberta.visitor.transform.Ev3ThreeOne2FourTransformerVisitor;
import de.fhg.iais.roberta.worker.ThreeOne2FourTransformerWorker;

public class Ev3ThreeOne2FourTransformerWorker extends ThreeOne2FourTransformerWorker {

    @Override
    protected ITransformerVisitor<Void> getVisitor(
        Project project, NewUsedHardwareBean.Builder builder, ConfigurationAst configuration) {
        return new Ev3ThreeOne2FourTransformerVisitor(project.getRobotFactory().getBlocklyDropdownFactory());
    }
}
