package agh.ics.oop.model.genotype;


public enum GenotypeType {
    BASIC_GENOTYPE,
    PING_PONG_GENOTYPE;

    public Genotype createGenotype(int length) {
        return switch (this) {
            case BASIC_GENOTYPE -> new BasicGenotype(length);
            case PING_PONG_GENOTYPE -> new PingPongGenotype(length);
        };
    }

}
