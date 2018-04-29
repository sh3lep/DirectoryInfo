package DirInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.File;
import java.util.Collections;

public class DirInfo {

    public static void main(String[] args) throws IOException {

        CmdLineArgs values = new CmdLineArgs(args);

        boolean l = values.longFormat;
        boolean h = values.humanReadable;
        boolean r = values.reverse;
        boolean o = values.out != null;

        if (!l && h) {
            System.err.println("check if input is correct");
            return;
        }

        if (values.dir == null) {
            System.err.println("file path is not set");
            return;
        }

        File dir = new File(values.dir);

        if (!dir.exists()) {
            System.err.print("selected file path does not exist: ");
            System.err.println(dir);
            return;
        }

        boolean d = dir.isDirectory(); // Дополнительный флаг: директория или нет (файл)

        File outputName = new File(".//testOutput//" + values.out);

        ArrayList<String> res = getInfo(l, h, r, d, dir);

        toWrite(res, o, outputName);

    }

    private static String getRWX(File file, boolean flag) { // Проверка на чтение-запись-выполнение в 2х видах
        StringBuilder res = new StringBuilder();
        if (flag) {

            if (file.canRead()) res.append(1);
            else res.append(0);
            if (file.canWrite()) res.append(1);
            else res.append(0);
            if (file.canExecute()) res.append(1);
            else res.append(0);

        } else {

            if (file.canRead()) res.append('r');
            else res.append('-');
            if (file.canWrite()) res.append('w');
            else res.append('-');
            if (file.canExecute()) res.append('x');
            else res.append('-');

        }

        return res.toString();
    }

    private static String fromBytes(File file) { // Перевод из байтов. Добавить округление?
        StringBuilder res = new StringBuilder();
        Long length = file.length();
        if (file.length() >= 1073741824) { // Больше гб или нет
            res.append((double) length / 1073741824.0).append(" GB");
        } else {
            if (file.length() >= 1048576) {
                res.append((double) length / 1048576.0).append(" MB"); // Больше мб или нет
            } else {
                res.append((double) length / 1024.0).append(" KB"); // Т.к меньше МБ, то подсчет в КБ
            }
        }
        return res.toString();
    }

    private static String getTime(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(file.lastModified());
    }

    private static String getData(boolean h, File dir) {

        StringBuilder data = new StringBuilder();

        data.append(dir.getName()).append(" ");
        if (!h) { // Если просто расширенный формат
            data.append(getRWX(dir, true)).append(" ");
            data.append(dir.lastModified()).append(" ");
            data.append(dir.length()).append(" Bytes");
        } else { // Если человеко-читаемый формат
            data.append(getRWX(dir, false)).append(" ");
            data.append(getTime(dir)).append(" ");
            data.append(fromBytes(dir));
        }
        return data.toString();
    }

    private static ArrayList<String> getInfo(boolean l, boolean h, boolean r, boolean d, File dir) {

        ArrayList<String> info = new ArrayList<>();

        if (!d || dir.listFiles() == null) { // Если файл или пустая директория

            if (!l) info.add(dir.getName());
            else info.add(getData(h, dir));

        } else { // Если не пустая директория
            File[] list = dir.listFiles();

            if (!l) for (File file : list) info.add(file.getName());
            else for (File file : list) info.add(getData(h, file));
        }
        if (r) Collections.reverse(info);
        return info;
    }

    private static void toWrite(ArrayList<String> res, boolean o, File outputName) {
        if (o) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputName.toURI()))) {
                for (String info : res) {
                    writer.write(info);
                    writer.newLine();
                }
            } catch (IOException ex) {
                System.out.println(":(");
            }
        } else {
            for (String info : res) System.out.println(info);
        }
    }
}
