package ivanov.springbootintro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "drinks")
@Getter
@Setter
public class Drink extends Product {

    private Integer volume;

    private Double alcoholPercentage;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private DrinkCategory category;
}
