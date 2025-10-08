package core.lab_1;

public class TeleportStrategy implements MovementStrategy {
    public void move(String from, String to) {
        System.out.println("Телепортируюсь из " + from + " в " + to);
    }

    public String getDescription() {
        return "Телепортация";
    }
}
