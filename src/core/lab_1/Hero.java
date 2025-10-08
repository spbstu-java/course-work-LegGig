package core.lab_1;

import java.util.ArrayList;
import java.util.List;

public class Hero {
    private MovementStrategy movementStrategy;
    private String currentPosition;
    private List<MoveRecord> moveHistory;
    private int moveCounter;

    public Hero() {
        this.currentPosition = "Начальная точка";
        this.moveHistory = new ArrayList<>();
        this.moveCounter = 0;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
        System.out.println("Выбран способ перемещения: " + movementStrategy.getDescription());
    }

    public void move(String to) {
        move(currentPosition, to);
    }

    public void move(String from, String to) {
        if (movementStrategy == null) {
            System.out.println("Сначала выберите способ перемещения!");
            return;
        }

        movementStrategy.move(from, to);

        moveCounter++;
        moveHistory.add(new MoveRecord(from, to, movementStrategy.getDescription(), moveCounter));

        currentPosition = to;
        System.out.println("Теперь герой находится в: " + currentPosition);
    }

    public MovementStrategy getCurrentStrategy() {
        return movementStrategy;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setPosition(String position) {
        moveCounter++;
        moveHistory.add(new MoveRecord(currentPosition, position, "Принудительная установка", moveCounter));

        this.currentPosition = position;
        System.out.println("Герой перемещен в: " + position);
    }

    public void printMoveHistory() {
        if (moveHistory.isEmpty()) {
            System.out.println("Герой еще никуда не перемещался!");
            return;
        }

        System.out.println("\n=== ИСТОРИЯ ПЕРЕМЕЩЕНИЙ ГЕРОЯ ===");
        for (MoveRecord record : moveHistory) {
            System.out.println(record);
        }
        System.out.println("Итого перемещений: " + moveCounter);
        System.out.println("Текущее положение: " + currentPosition);
    }
}
