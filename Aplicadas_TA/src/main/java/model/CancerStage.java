package model;

public enum CancerStage {
    ETAPA_I,
    ETAPA_II,
    ETAPA_III,
    ETAPA_IV;

    public static CancerStage fromIndex(int i) {
        return values()[Math.min(Math.max(i, 0), values().length - 1)];
    }
}
