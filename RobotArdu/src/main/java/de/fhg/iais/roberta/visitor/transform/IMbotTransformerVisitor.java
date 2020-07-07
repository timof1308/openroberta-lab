package de.fhg.iais.roberta.visitor.transform;
import de.fhg.iais.roberta.syntax.Phrase;
import de.fhg.iais.roberta.syntax.action.display.ClearDisplayAction;
import de.fhg.iais.roberta.syntax.action.display.ShowTextAction;
import de.fhg.iais.roberta.syntax.action.light.LightAction;
import de.fhg.iais.roberta.syntax.action.light.LightStatusAction;
import de.fhg.iais.roberta.syntax.action.motor.MotorGetPowerAction;
import de.fhg.iais.roberta.syntax.action.motor.MotorOnAction;
import de.fhg.iais.roberta.syntax.action.motor.MotorSetPowerAction;
import de.fhg.iais.roberta.syntax.action.motor.MotorStopAction;
import de.fhg.iais.roberta.syntax.action.motor.differential.CurveAction;
import de.fhg.iais.roberta.syntax.action.motor.differential.DriveAction;
import de.fhg.iais.roberta.syntax.action.motor.differential.MotorDriveStopAction;
import de.fhg.iais.roberta.syntax.action.motor.differential.TurnAction;
import de.fhg.iais.roberta.syntax.action.sound.PlayFileAction;
import de.fhg.iais.roberta.syntax.action.sound.PlayNoteAction;
import de.fhg.iais.roberta.syntax.action.sound.ToneAction;
import de.fhg.iais.roberta.syntax.action.sound.VolumeAction;
import de.fhg.iais.roberta.syntax.actors.arduino.LEDMatrixImageAction;
import de.fhg.iais.roberta.syntax.actors.arduino.LEDMatrixSetBrightnessAction;
import de.fhg.iais.roberta.syntax.actors.arduino.LEDMatrixTextAction;
import de.fhg.iais.roberta.syntax.actors.arduino.mbot.ReceiveIRAction;
import de.fhg.iais.roberta.syntax.actors.arduino.mbot.SendIRAction;
import de.fhg.iais.roberta.syntax.expressions.arduino.LEDMatrixImage;
import de.fhg.iais.roberta.syntax.functions.arduino.LEDMatrixImageInvertFunction;
import de.fhg.iais.roberta.syntax.functions.arduino.LEDMatrixImageShiftFunction;
import de.fhg.iais.roberta.syntax.lang.expr.Expr;
import de.fhg.iais.roberta.syntax.sensor.SensorMetaDataBean;
import de.fhg.iais.roberta.syntax.sensors.arduino.mbot.FlameSensor;
import de.fhg.iais.roberta.syntax.sensors.arduino.mbot.Joystick;
import de.fhg.iais.roberta.visitor.ITransformerVisitor;
import de.fhg.iais.roberta.visitor.hardware.IMbotVisitor;

public interface IMbotTransformerVisitor<V> extends ITransformerVisitor<V>, IMbotVisitor<Phrase<V>> {

    @Override
    default Phrase<V> visitJoystick(Joystick<Phrase<V>> joystick) {
        return Joystick.make(joystick.getAxis(),
            new SensorMetaDataBean(joystick.getPort(), joystick.getMode(), joystick.getSlot(), joystick.isPortInMutation()),
            joystick.getProperty(),
            joystick.getComment());
    }

    @Override
    default Phrase<V> visitFlameSensor(FlameSensor<Phrase<V>> flameSensor) {
        return FlameSensor.make(flameSensor.getPort(),
            flameSensor.getProperty(),
            flameSensor.getComment());
    }

    @Override
    default Phrase<V> visitSendIRAction(SendIRAction<Phrase<V>> sendIRAction) {
        return SendIRAction.make((Expr<V>) sendIRAction.getMessage().modify(this),
            sendIRAction.getProperty(),
            sendIRAction.getComment());
    }

    @Override
    default Phrase<V> visitReceiveIRAction(ReceiveIRAction<Phrase<V>> receiveIRAction) {
        return ReceiveIRAction.make(
            receiveIRAction.getProperty(),
            receiveIRAction.getComment()
        );
    }

    @Override
    default Phrase<V> visitLEDMatrixImageAction(LEDMatrixImageAction<Phrase<V>> ledMatrixImageAction) {
        return LEDMatrixImageAction.make(ledMatrixImageAction.getPort(),
            ledMatrixImageAction.getDisplayImageMode(),
            (Expr<V>) ledMatrixImageAction.getValuesToDisplay().modify(this),
            ledMatrixImageAction.getProperty(),
            ledMatrixImageAction.getComment());
    }

    @Override
    default Phrase<V> visitLEDMatrixTextAction(LEDMatrixTextAction<Phrase<V>> ledMatrixTextAction) {
        return LEDMatrixTextAction.make(ledMatrixTextAction.getPort(),
            (Expr<V>) ledMatrixTextAction.getMsg().modify(this),
            ledMatrixTextAction.getProperty(),
            ledMatrixTextAction.getComment());
    }

    @Override
    default Phrase<V> visitLEDMatrixImage(LEDMatrixImage<Phrase<V>> ledMatrixImage) {
        return LEDMatrixImage.make(ledMatrixImage.getImage(),
            ledMatrixImage.getProperty(),
            ledMatrixImage.getComment());
    }

    @Override
    default Phrase<V> visitLEDMatrixImageShiftFunction(LEDMatrixImageShiftFunction<Phrase<V>> ledMatrixImageShiftFunction) {
        return LEDMatrixImageShiftFunction.make(
            (Expr<V>) ledMatrixImageShiftFunction.getImage().modify(this),
            (Expr<V>) ledMatrixImageShiftFunction.getPositions().modify(this),
            ledMatrixImageShiftFunction.getShiftDirection(),
            ledMatrixImageShiftFunction.getProperty(),
            ledMatrixImageShiftFunction.getComment()
        );
    }

    @Override
    default Phrase<V> visitLEDMatrixImageInvertFunction(LEDMatrixImageInvertFunction<Phrase<V>> ledMatrixImageInverFunction) {
        return LEDMatrixImageInvertFunction.make(
            (Expr<V>) ledMatrixImageInverFunction.getImage().modify(this),
            ledMatrixImageInverFunction.getProperty(),
            ledMatrixImageInverFunction.getComment()
        );
    }

    @Override
    default Phrase<V> visitLEDMatrixSetBrightnessAction(LEDMatrixSetBrightnessAction<Phrase<V>> ledMatrixSetBrightnessAction) {
        return LEDMatrixSetBrightnessAction.make(ledMatrixSetBrightnessAction.getPort(),
            (Expr<V>) ledMatrixSetBrightnessAction.getBrightness().modify(this),
            ledMatrixSetBrightnessAction.getProperty(),
            ledMatrixSetBrightnessAction.getComment());
    }

    // unrelated defaults

    @Override
    default Phrase<V> visitMotorGetPowerAction(MotorGetPowerAction<Phrase<V>> motorGetPowerAction) {
        return ITransformerVisitor.super.visitMotorGetPowerAction(motorGetPowerAction);
    }

    @Override
    default Phrase<V> visitMotorSetPowerAction(MotorSetPowerAction<Phrase<V>> motorSetPowerAction) {
        return ITransformerVisitor.super.visitMotorSetPowerAction(motorSetPowerAction);
    }

    @Override
    default Phrase<V> visitMotorOnAction(MotorOnAction<Phrase<V>> motorOnAction) {
        return ITransformerVisitor.super.visitMotorOnAction(motorOnAction);
    }

    @Override
    default Phrase<V> visitMotorStopAction(MotorStopAction<Phrase<V>> motorStopAction) {
        return ITransformerVisitor.super.visitMotorStopAction(motorStopAction);
    }

    @Override
    default Phrase<V> visitDriveAction(DriveAction<Phrase<V>> driveAction) {
        return ITransformerVisitor.super.visitDriveAction(driveAction);
    }

    @Override
    default Phrase<V> visitCurveAction(CurveAction<Phrase<V>> curveAction) {
        return ITransformerVisitor.super.visitCurveAction(curveAction);
    }

    @Override
    default Phrase<V> visitTurnAction(TurnAction<Phrase<V>> turnAction) {
        return ITransformerVisitor.super.visitTurnAction(turnAction);
    }

    @Override
    default Phrase<V> visitMotorDriveStopAction(MotorDriveStopAction<Phrase<V>> stopAction) {
        return ITransformerVisitor.super.visitMotorDriveStopAction(stopAction);
    }

    @Override
    default Phrase<V> visitShowTextAction(ShowTextAction<Phrase<V>> showTextAction) {
        return ITransformerVisitor.super.visitShowTextAction(showTextAction);
    }

    @Override
    default Phrase<V> visitClearDisplayAction(ClearDisplayAction<Phrase<V>> clearDisplayAction) {
        return ITransformerVisitor.super.visitClearDisplayAction(clearDisplayAction);
    }

    @Override
    default Phrase<V> visitPlayNoteAction(PlayNoteAction<Phrase<V>> playNoteAction) {
        return ITransformerVisitor.super.visitPlayNoteAction(playNoteAction);
    }

    @Override
    default Phrase<V> visitVolumeAction(VolumeAction<Phrase<V>> volumeAction) {
        return ITransformerVisitor.super.visitVolumeAction(volumeAction);
    }

    @Override
    default Phrase<V> visitToneAction(ToneAction<Phrase<V>> toneAction) {
        return ITransformerVisitor.super.visitToneAction(toneAction);
    }

    @Override
    default Phrase<V> visitPlayFileAction(PlayFileAction<Phrase<V>> playFileAction) {
        return ITransformerVisitor.super.visitPlayFileAction(playFileAction);
    }

    @Override
    default Phrase<V> visitLightAction(LightAction<Phrase<V>> lightAction) {
        return ITransformerVisitor.super.visitLightAction(lightAction);
    }

    @Override
    default Phrase<V> visitLightStatusAction(LightStatusAction<Phrase<V>> lightStatusAction) {
        return ITransformerVisitor.super.visitLightStatusAction(lightStatusAction);
    }
}
