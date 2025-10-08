package core.lab_1;

public class HorseRidingStrategy implements MovementStrategy {
    public void move(String from, String to) {
        System.out.println("Скачу на лошади из " + from + " в " + to);
    }

    public String getDescription() {
        return "На лошади";
    }
}
