package ivanov.springbootintro.repository.dishcategory;

import ivanov.springbootintro.model.DishCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DishCategoryRepository extends JpaRepository<DishCategory, Long>,
        JpaSpecificationExecutor<DishCategory> {
}
