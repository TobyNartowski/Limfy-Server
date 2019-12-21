package pl.tobynartowski.limfy.model.wrapper;

import pl.tobynartowski.limfy.model.misc.Gender;

public class BodyDataWrapper {

    private Gender gender;
    private Integer height;
    private Integer weight;

    public BodyDataWrapper(Gender gender, Integer height, Integer weight) {
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }
}
