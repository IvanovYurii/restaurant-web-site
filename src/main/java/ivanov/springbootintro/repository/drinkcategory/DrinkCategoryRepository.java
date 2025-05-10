package ivanov.springbootintro.repository.drinkcategory;

import ivanov.springbootintro.model.DrinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DrinkCategoryRepository extends JpaRepository<DrinkCategory, Long>,
        JpaSpecificationExecutor<DrinkCategory> {
}
