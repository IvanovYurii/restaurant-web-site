package ivanov.springbootintro.repository.dish;

import ivanov.springbootintro.model.Dish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DishRepository extends JpaRepository<Dish, Long>,
        JpaSpecificationExecutor<Dish> {

    List<Dish> findDishByCategory_Id(Long categoryId);
}
