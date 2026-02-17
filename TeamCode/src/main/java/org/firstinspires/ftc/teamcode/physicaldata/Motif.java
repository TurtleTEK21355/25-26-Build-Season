package org.firstinspires.ftc.teamcode.physicaldata;

public enum Motif {
    GPP(21),
    PGP(22),
    PPG(23);

    private final int motifID;


    Motif(int id) {
        this.motifID = id;
    }
    public int getID() {
        return motifID;
    }
    public static Motif fromID(int id) {
        for (Motif motif : Motif.values()) {
            if (motif.motifID == id){
                return motif;
            }
        }
        return null;
    }
}
