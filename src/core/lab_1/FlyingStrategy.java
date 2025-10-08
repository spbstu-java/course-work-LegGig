package core.lab_1;

public class FlyingStrategy implements MovementStrategy {
    public void move(String from, String to) {
        System.out.println("Лечу по воздуху из " + from + " в " + to);
    }

    public String getDescription() {
        return "Полёт";
    }
}
