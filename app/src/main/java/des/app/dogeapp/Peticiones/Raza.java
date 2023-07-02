package des.app.dogeapp.Peticiones;

public class Raza {
    private String weightImperial;
    private String weightMetric;
    private String heightImperial;
    private String heightMetric;
    private int id;
    private String name;
    private String bredFor;
    private String breedGroup;
    private String lifeSpan;
    private String temperament;
    private String referenceImageId;

    // Constructor
    public Raza(String weightImperial, String weightMetric, String heightImperial, String heightMetric,
                int id, String name, String bredFor, String breedGroup, String lifeSpan, String temperament,
                String referenceImageId) {
        this.weightImperial = weightImperial;
        this.weightMetric = weightMetric;
        this.heightImperial = heightImperial;
        this.heightMetric = heightMetric;
        this.id = id;
        this.name = name;
        this.bredFor = bredFor;
        this.breedGroup = breedGroup;
        this.lifeSpan = lifeSpan;
        this.temperament = temperament;
        this.referenceImageId = referenceImageId;
    }

    // Getters
    public String getWeightImperial() {
        return weightImperial;
    }

    public String getWeightMetric() {
        return weightMetric;
    }

    public String getHeightImperial() {
        return heightImperial;
    }

    public String getHeightMetric() {
        return heightMetric;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBredFor() {
        return bredFor;
    }

    public String getBreedGroup() {
        return breedGroup;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public String getTemperament() {
        return temperament;
    }

    public String getReferenceImageId() {
        return referenceImageId;
    }
}
