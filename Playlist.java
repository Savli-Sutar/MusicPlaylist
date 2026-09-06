import java.util.Scanner;
class SongNode {
    String title, artist;
    double duration;
    SongNode next;
    public SongNode(String title, String artist, double duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.next = null;
    }
    public String toString() {
        return "\"" + title + "\" by " + artist + " (" + duration + " mins)";
    }
}
class Playlist {
    private SongNode head = null, currentSong = null;
    private int size = 0;

    public void addSong(String title, String artist, double duration) {
        SongNode newSong = new SongNode(title, artist, duration);
        if (head == null) {
            head = currentSong = newSong;
        } else {
            SongNode temp = head;
            while (temp.next != null) temp = temp.next;
            temp.next = newSong;
        }
        size++;
        System.out.println("-> Added: " + newSong);
    }

    public void removeSong(String title) {
        if (head == null) {
            System.out.println("-> Playlist is empty. Nothing to remove.");
            return;
        }

        if (head.title.equalsIgnoreCase(title)) {
            if (currentSong == head) currentSong = head.next;
            head = head.next;
            size--;
            System.out.println("-> Removed song: \"" + title + "\"");
            return;
        }
        SongNode temp = head;
        while (temp.next != null && !temp.next.title.equalsIgnoreCase(title)) {
            temp = temp.next;
        }
        if (temp.next != null) {
            if (currentSong == temp.next) {
                currentSong = (temp.next.next != null) ? temp.next.next : head;
            }
            temp.next = temp.next.next;
            size--;
            System.out.println("-> Removed song: \"" + title + "\"");
        } else {
            System.out.println("-> Song titled \"" + title + "\" not found in the playlist.");
        }
    }
    public void displayPlaylist() {
        if (head == null) {
            System.out.println("-> Playlist is empty.");
            return;
        }

        System.out.println("\n--- Current Playlist (" + size + " songs) ---");
        SongNode temp = head;
        int index = 1;
        while (temp != null) {
            String marker = (temp == currentSong) ? " [Currently Playing]" : "";
            System.out.println(index++ + ". " + temp + marker);
            temp = temp.next;
        }
        System.out.println("--------------------------------");
    }
    public void playCurrent() {
        if (currentSong == null) System.out.println("-> Playlist is empty. No song to play.");
        else System.out.println("-> Now Playing: " + currentSong);
    }
    public void playNext() {
        if (currentSong == null) {
            System.out.println("-> Playlist is empty.");
            return;
        }
        if (currentSong.next != null) {
            currentSong = currentSong.next;
            System.out.println("-> Played Next: " + currentSong);
        } else {
            System.out.println("-> Reached the end of the playlist. Loop back to start.");
            currentSong = head;
            if (currentSong != null) System.out.println("-> Now Playing: " + currentSong);
        }
    }
    public void searchSong(String title) {
        SongNode temp = head;
        int position = 1;
        while (temp != null) {
            if (temp.title.equalsIgnoreCase(title)) {
                System.out.println("-> Found: " + temp + " at position " + position);
                return;
            }
            temp = temp.next;
            position++;
        }
        System.out.println("-> Song titled \"" + title + "\" not found.");
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Playlist playlist = new Playlist();

        System.out.println("====================================");
        System.out.println("    MUSIC PLAYLIST MANAGER          ");
        System.out.println("====================================");

        while (true) {
            System.out.println("\n1. Add Song\n2. Remove Song\n3. Display Playlist\n4. Play Current Song\n5. Play Next Song\n6. Search Song\n7. Exit");
            System.out.print("Enter your choice (1-7): ");
            if (!scanner.hasNextInt()) {
                System.out.println("-> Invalid input! Please enter a number.");
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 7) {
                System.out.println("-> Exiting Music Playlist Manager. Goodbye!");
                break;
            }
            switch (choice) {
                case 1:
                    System.out.print("Enter song title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter artist name: ");
                    String artist = scanner.nextLine();
                    System.out.print("Enter duration (e.g., 3.45): ");
                    while (!scanner.hasNextDouble()) {
                        System.out.print("Invalid duration. Enter a valid decimal number: ");
                        scanner.next();
                    }
                    playlist.addSong(title, artist, scanner.nextDouble());
                    scanner.nextLine();
                    break;
                case 2:
                    System.out.print("Enter title of song to remove: ");
                    playlist.removeSong(scanner.nextLine());
                    break;
                case 3:
                    playlist.displayPlaylist();
                    break;
                case 4:
                    playlist.playCurrent();
                    break;
                case 5:
                    playlist.playNext();
                    break;
                case 6:
                    System.out.print("Enter title of song to search: ");
                    playlist.searchSong(scanner.nextLine());
                    break;
                default:
                    System.out.println("-> Invalid option. Choice must be between 1 and 7.");
            }
        }
        scanner.close();
    }
} 

