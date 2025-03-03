import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Employee {
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
}

class Card {
    String symbol;
    String value;

    Card(String symbol, String value) {
        this.symbol = symbol;
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " of " + symbol;
    }
}

class TicketBooking implements Runnable {
    private static int availableSeats = 10;

    @Override
    public synchronized void run() {
        if (availableSeats > 0) {
            System.out.println(Thread.currentThread().getName() + " booked a seat.");
            availableSeats--;
        } else {
            System.out.println("No seats available.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Employee> employees = new ArrayList<>();
        List<Card> cards = new ArrayList<>();
        String[] symbols = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String symbol : symbols) {
            for (String value : values) {
                cards.add(new Card(symbol, value));
            }
        }

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Employee Management");
            System.out.println("2. Card Collection");
            System.out.println("3. Ticket Booking System");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            if (choice == 1) {
                while (true) {
                    System.out.println("Employee Management:");
                    System.out.println("1. Add Employee");
                    System.out.println("2. Update Employee");
                    System.out.println("3. Remove Employee");
                    System.out.println("4. Search Employee");
                    System.out.println("5. Go back to main menu");
                    System.out.print("Enter choice: ");
                    int empChoice = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    if (empChoice == 1) {
                        System.out.print("Enter ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Salary: ");
                        double salary = scanner.nextDouble();
                        employees.add(new Employee(id, name, salary));
                    } else if (empChoice == 2) {
                        System.out.print("Enter Employee ID to update: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        boolean found = false;
                        for (Employee e : employees) {
                            if (e.id == id) {
                                found = true;
                                System.out.print("Enter New Name: ");
                                e.name = scanner.nextLine();
                                System.out.print("Enter New Salary: ");
                                e.salary = scanner.nextDouble();
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Employee not found.");
                        }
                    } else if (empChoice == 3) {
                        System.out.print("Enter Employee ID to remove: ");
                        int id = scanner.nextInt();
                        employees.removeIf(e -> e.id == id);
                    } else if (empChoice == 4) {
                        System.out.print("Enter Employee ID to search: ");
                        int id = scanner.nextInt();
                        boolean found = false;
                        for (Employee e : employees) {
                            if (e.id == id) {
                                found = true;
                                System.out.println("ID: " + e.id + ", Name: " + e.name + ", Salary: " + e.salary);
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Employee not found.");
                        }
                    } else if (empChoice == 5) {
                        break;
                    }
                }
            } else if (choice == 2) {
                while (true) {
                    System.out.println("Card Collection:");
                    System.out.print("Enter symbol to find cards (Hearts, Diamonds, Clubs, Spades) or 'exit' to go back: ");
                    String symbol = scanner.nextLine();
                    if (symbol.equalsIgnoreCase("exit")) {
                        break;
                    }
                    System.out.println("Cards of " + symbol + ":");
                    boolean found = false;
                    for (Card card : cards) {
                        if (card.symbol.equalsIgnoreCase(symbol)) {
                            System.out.println(card);
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("No cards found for this symbol.");
                    }
                }
            } else if (choice == 3) {
                TicketBooking ticketBooking = new TicketBooking();
                Thread t1 = new Thread(ticketBooking, "VIP Customer");
                Thread t2 = new Thread(ticketBooking, "Regular Customer");
                Thread t3 = new Thread(ticketBooking, "VIP Customer");

                t1.setPriority(Thread.MAX_PRIORITY);
                t2.setPriority(Thread.NORM_PRIORITY);
                t3.setPriority(Thread.MAX_PRIORITY);

                t1.start();
                t2.start();
                t3.start();
                try {
                    t1.join();
                    t2.join();
                    t3.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (choice == 4) {
                break;
            }
        }
        scanner.close();
    }
}
