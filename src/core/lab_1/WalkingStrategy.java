package core.lab_1;

public class WalkingStrategy implements MovementStrategy {
    public void move(String from, String to) {
        System.out.println("Иду пешком из " + from + " в " + to);
    }

    public String getDescription() {
        return "Пешком";
    }
}
