package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * this manages the thingys called MenuItems in an arrayList called "menuItemList"
 */
public class Menu {
    private int selectedMenuItem = 0;
    private boolean previousItemLast = false;
    private boolean nextItemLast = false;
    List<MenuItem> menuItemList = new ArrayList<>();
    private ElapsedTime firstTimer;
    private ElapsedTime loopTimer;
    private boolean lock1 = false;

    public Menu() {
        firstTimer = new ElapsedTime();
        loopTimer = new ElapsedTime();
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

        MenuItem.State selectedItemState = menuItemList.get(selectedMenuItem).getState();

        if (selectedItemState != MenuItem.State.MIDDLE) {
            if (!lock1) {
                menuItemList.get(selectedMenuItem).valueChange();
                firstTimer.startTime();
                lock1 = true;
            }
        } else {
            lock1 = false;
            firstTimer.reset();

        }

        if (firstTimer.milliseconds() > 1000) {
            menuItemList.get(selectedMenuItem).valueChange();
            loopTimer.startTime();
        }

        if (loopTimer.milliseconds() > 100){
            menuItemList.get(selectedMenuItem).valueChange();
            loopTimer.reset();
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
            name = name.concat(menuItemList.get(i).getName() + " = " + menuItemList.get(i).getStringValue()) + "\n";

        }
        return name;
    }
}
