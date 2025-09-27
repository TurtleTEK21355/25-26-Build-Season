package org.firstinspires.ftc.teamcode.internalClasses;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * this manages the thingys called modes in an arrayList called "modes"
 *
 */
public class ModeController {
    double updateTimeValue = 400;
    private int selectedMode = 0;
    private boolean previousItemLast = false;
    private boolean nextItemLast = false;
    List<Mode> modes = new ArrayList<>();
    private ElapsedTime elapsedTime;

    public ModeController() {
        elapsedTime = new ElapsedTime();
        elapsedTime.startTime();

    }

    public void add(Mode m) {
        this.modes.add(m);
    }

    public void add(Mode... modes) {
        this.modes.addAll(Arrays.asList(modes));
    }

    public String getCurrentModeName() {
        return modes.get(selectedMode).getName();
    }

    public double getModeValueDouble(String modeName) {
        for (int i = 0; i < modes.size(); i++) {
            if (Objects.equals(modes.get(i).getName(), modeName)) {
                return modes.get(i).getValueDouble();
            }
        }
        throw new RuntimeException("Mode not found");
    }
    public boolean getModeValueBoolean(String modeName) {
        for (int i = 0; i < modes.size(); i++) {
            if (Objects.equals(modes.get(i).getName(), modeName)) {
                return modes.get(i).getValueBoolean();
            }
        }
        throw new RuntimeException("Mode not found");
    }

    public void modeSelection(boolean previousMode, boolean nextMode, boolean valueUp, boolean valueDown) {
        if (previousMode && !previousItemLast) {
            selectedMode -= 1;
            previousItemLast = true;
        } else if (!previousMode) {
            previousItemLast = false;
        }

        if (nextMode && !nextItemLast) {
            selectedMode += 1;
            nextItemLast = true;
        } else if (!nextMode) {
            nextItemLast = false;
        }

        if (selectedMode > modes.size() - 1) {
            selectedMode = 0;
        } else if (selectedMode < 0) {
            selectedMode = modes.size() - 1;
        }

        modes.get(selectedMode).stateChange(valueUp, valueDown);


        if (elapsedTime.milliseconds() > updateTimeValue){
            modes.get(selectedMode).valueChange();

            elapsedTime.reset();
        }
    }

    /**
     * reports the values and names of the modes as a string with the selected one having ">" before it
     */
    public String reportModeValue() {
        String name = "";
        for(int i = 0; i < modes.size(); i++) {
            if (i == selectedMode) {
                name = name.concat("> ");
            }

            DecimalFormat valueFormat = new DecimalFormat("##.##");
            valueFormat.setRoundingMode(RoundingMode.HALF_UP);
            if (modes.get(i).getType() == Mode.Type.BOOLEAN) {
                name = name.concat(modes.get(i).getName() + " = " + modes.get(i).getValueBoolean()) + "\n";
            } else if (modes.get(i).getType() == Mode.Type.DOUBLE){
                name = name.concat(modes.get(i).getName() + " = " + valueFormat.format(modes.get(i).getValueDouble())) + "\n";
            }

        }
        return name;
    }
}
