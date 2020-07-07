package de.fhg.iais.roberta.worker;

import de.fhg.iais.roberta.bean.NewUsedHardwareBean;
import de.fhg.iais.roberta.components.ConfigurationAst;
import de.fhg.iais.roberta.components.Project;
import de.fhg.iais.roberta.visitor.ITransformerVisitor;

public class ThreeOne2FourTransformerWorker extends AbstractTransformerWorker {

    public ThreeOne2FourTransformerWorker() {
        super("3.0", "3.0", "4.0");
    }

    @Override
    protected ITransformerVisitor<Void> getVisitor(
        Project project, NewUsedHardwareBean.Builder builder, ConfigurationAst configuration) {
        return null;
    }
}
