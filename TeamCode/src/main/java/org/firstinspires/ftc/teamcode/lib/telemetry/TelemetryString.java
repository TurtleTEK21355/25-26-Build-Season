package org.firstinspires.ftc.teamcode.lib.telemetry;

public class TelemetryString {
    private StringBuilder stringBuilder = new StringBuilder();

    public TelemetryString() {
        stringBuilder.append("");//will make sure its not null but it probably doesnt matter
    }

    public void addLine(String string) {
        stringBuilder.append(string);
        stringBuilder.append(System.lineSeparator());
    }

    public void addData(String caption, Object data) {
        if (data == null) {
            stringBuilder.append(caption).append(": ").append("null");
            stringBuilder.append(System.lineSeparator());
        }
        else {
            stringBuilder.append(caption).append(": ").append(data.toString());
            stringBuilder.append(System.lineSeparator());
        }
    }

    public void addData(String caption, boolean data) {
        stringBuilder.append(caption).append(": ").append(Boolean.valueOf(data));
        stringBuilder.append(System.lineSeparator());
    }

    public void addData(String caption, int data) {
        stringBuilder.append(caption).append(": ").append(Integer.valueOf(data));
        stringBuilder.append(System.lineSeparator());
    }

    public void addData(String caption, double data) {
        stringBuilder.append(caption).append(": ").append(Double.valueOf(data));
        stringBuilder.append(System.lineSeparator());
    }

    public String toString() {
        return stringBuilder.toString();
    }
}
