package pl.tobynartowski.limfy.model.projection;

import org.springframework.hateoas.server.core.Relation;

public class BodyDataWrapper {

    private Integer height;
    private Integer weight;

    public BodyDataWrapper(Integer height, Integer weight) {
        this.height = height;
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }
}
