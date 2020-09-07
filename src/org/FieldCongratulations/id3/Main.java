package org.FieldCongratulations.id3;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String string = scanner.nextLine();
            File file;
            byte tag;
            do {
                file = new File("D:/CloudMusic/" + string + ".mp3");
                tag = AudioFile.getFileID3Version(file);
                System.out.print("\n\nID3V1:");
                System.out.print(Boolean.valueOf((tag & ID3Versions.ID3V1) != 0));
                System.out.print("    ID3V2.3:");
                System.out.print(Boolean.valueOf((tag & ID3Versions.ID3V2_3) != 0));
                System.out.print("    ID3V2.4:");
                System.out.println(Boolean.valueOf((tag & ID3Versions.ID3V2_4) != 0));
                System.out.println(AudioFile.readID3V2Tag(file).dumpFrames());
                System.out.println("END OF READ");
            } while (!(string = scanner.nextLine()).equals("exit"));
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
