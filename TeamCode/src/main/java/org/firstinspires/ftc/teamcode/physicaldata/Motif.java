package org.firstinspires.ftc.teamcode.physicaldata;

public enum Motif {
    GPP(21, new ArtifactState[]{ArtifactState.GREEN, ArtifactState.PURPLE, ArtifactState.PURPLE}),
    PGP(22, new ArtifactState[]{ArtifactState.PURPLE, ArtifactState.GREEN, ArtifactState.PURPLE}),
    PPG(23, new ArtifactState[]{ArtifactState.PURPLE, ArtifactState.PURPLE, ArtifactState.GREEN});

    private final int motifID;

    private final ArtifactState[] artifactSequence;


    Motif(int id, ArtifactState[] artifactSequence) {
        this.motifID = id;
        this.artifactSequence = artifactSequence;
    }

    public int getID() {
        return motifID;
    }

    public ArtifactState getArtifactState(int index) {
        return artifactSequence[index];
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
