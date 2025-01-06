package ivanov.springbootintro.repository.drink;

import ivanov.springbootintro.model.Drink;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {

    // Метод для отримання всіх напоїв за категорією
    List<Drink> findAllByCategory_Id(Long categoryId);
}
