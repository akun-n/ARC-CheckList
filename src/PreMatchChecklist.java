import java.util.ArrayList;
import java.util.Scanner;

public class PreMatchChecklist {

    static class ChecklistItem {
        String description;
        boolean isCompleted;

        ChecklistItem(String description) {
            this.description = description;
            this.isCompleted = false;
        }
    }

    static class Battery {
        String status;
        double voltage;

        Battery(String status, double voltage) {
            this.status = status;
            this.voltage = voltage;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<ChecklistItem> checklist = new ArrayList<>();
        ArrayList<Battery> batteries = new ArrayList<>();

        int choice;
        do {
            System.out.println("1- Madde ekleme (Add)");
            System.out.println("2- Kontrol (Control)");
            System.out.println("3- Çıkış (Exit)");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addChecklistItem(scanner, checklist);
                    break;
                case 2:
                    controlChecklist(scanner, checklist, batteries);
                    break;
                case 3:
                    System.out.println("Programdan çıkılıyor.");
                    break;
                default:
                    System.out.println("Geçersiz seçenek, tekrar deneyin.");
            }
        } while (choice != 3);
    }

    public static void addChecklistItem(Scanner scanner, ArrayList<ChecklistItem> checklist) {
        System.out.print("Eklemek istediğiniz maddeyi giriniz: ");
        String itemDescription = scanner.nextLine();
        checklist.add(new ChecklistItem(itemDescription));
    }

    public static void controlChecklist(Scanner scanner, ArrayList<ChecklistItem> checklist, ArrayList<Battery> batteries) {
        if (checklist.isEmpty()) {
            System.out.println("Kontrole geçmek için en az bir madde eklenmiş olmalı.");
            return;
        }

        for (ChecklistItem item : checklist) {
            System.out.print(item.description + " (T/F): ");
            String response = scanner.nextLine();
            item.isCompleted = response.equalsIgnoreCase("T");
        }

        // Battery input section
        System.out.print("Pil sayısını girin: ");
        int batteryCount = scanner.nextInt();
        scanner.nextLine();
        batteries.clear();

        for (int i = 0; i < batteryCount; i++) {
            System.out.print((i + 1) + ". pil voltajını girin: ");
            double voltage = scanner.nextDouble();
            scanner.nextLine();
            System.out.print((i + 1) + ". pil sağlık durumunu girin (good/fair): ");
            String status = scanner.nextLine();
            batteries.add(new Battery(status, voltage));
        }

        Battery bestBattery = selectBestBattery(batteries);
        System.out.println("Maç için en uygun akü: " + bestBattery.status + " statusünde, " + bestBattery.voltage + " V");

        System.out.println("Tamamlanmamış maddeler:");
        for (ChecklistItem item : checklist) {
            if (!item.isCompleted) {
                System.out.println("- " + item.description);
            }
        }

        boolean allItemsCompleted = checklist.stream().allMatch(item -> item.isCompleted);
        if (bestBattery != null && allItemsCompleted) {
            System.out.println("Maça Hazır!");
        } else {
            System.out.println("Maç için tüm hazırlıklar tamamlanmadı.");
        }
    }

    public static Battery selectBestBattery(ArrayList<Battery> batteries) {
        Battery bestBattery = null;
        for (Battery battery : batteries) {
            if (bestBattery == null) {
                bestBattery = battery;
            } else if (battery.voltage > bestBattery.voltage ||
                    (battery.voltage == bestBattery.voltage && battery.status.equals("good") && !bestBattery.status.equals("good"))) {
                bestBattery = battery;
            }
        }
        return bestBattery;
    }
}