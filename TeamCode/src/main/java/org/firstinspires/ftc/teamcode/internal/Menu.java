package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * this manages the thingys called modes in an arrayList called "modes"
 */
public class Menu {
    double updateTimeValue = 400;
    private int selectedMenuItem = 0;
    private boolean previousItemLast = false;
    private boolean nextItemLast = false;
    List<MenuItem> menuItemList = new ArrayList<>();
    private ElapsedTime firstTimer;
    private ElapsedTime loopTimer;

    public Menu() {
        firstTimer = new ElapsedTime();
        firstTimer.startTime();

    }

    public void add(MenuItem m) {
        this.menuItemList.add(m);
    }

    public void add(MenuItem... modes) {
        this.menuItemList.addAll(Arrays.asList(modes));
    }

    public String getSelectedModeName() {
        return menuItemList.get(selectedMenuItem).getName();
    }

    public void modeSelection(boolean previousMenuItem, boolean nextMenuItem, boolean valueUp, boolean valueDown) {
        if (previousMenuItem && !previousItemLast) {
            selectedMenuItem -= 1;
            previousItemLast = true;
        } else if (!previousMenuItem) {
            previousItemLast = false;
        }

        if (nextMenuItem && !nextItemLast) {
            selectedMenuItem += 1;
            nextItemLast = true;
        } else if (!nextMenuItem) {
            nextItemLast = false;
        }

        if (selectedMenuItem > menuItemList.size() - 1) {
            selectedMenuItem = 0;
        } else if (selectedMenuItem < 0) {
            selectedMenuItem = menuItemList.size() - 1;
        }

        menuItemList.get(selectedMenuItem).stateChange(valueUp, valueDown);

        if (firstTimer.milliseconds() > updateTimeValue) {
            menuItemList.get(selectedMenuItem).valueChange();
            firstTimer.reset();

        }
    }

    /**
     * reports the values and names of the modes as a string with the selected one having ">" before it
     */
    public String reportModeValue() {
        String name = "";
        for(int i = 0; i < menuItemList.size(); i++) {
            if (i == selectedMenuItem) {
                name = name.concat("> ");
            }

            DecimalFormat valueFormat = new DecimalFormat("##.##");
            valueFormat.setRoundingMode(RoundingMode.HALF_UP);
            name = name.concat(menuItemList.get(i).getName() + " = " + menuItemList.get(i).getValue()) + "\n";

        }
        return name;
    }
}
