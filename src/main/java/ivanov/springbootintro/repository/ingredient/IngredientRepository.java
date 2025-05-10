package ivanov.springbootintro.repository.ingredient;

import ivanov.springbootintro.model.Ingredient;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>,
        JpaSpecificationExecutor<Ingredient> {

    List<Ingredient> findByIdIn(Set<Long> ids);
}
