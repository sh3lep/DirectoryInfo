import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.File;
import java.util.Collections;

public class DirInfo {

    public static void main(String[] args) {

        if (args.length > 6) throw new IllegalArgumentException("Too many flags selected.");

        boolean l = false; // Аргументы-флаги. Значения по умолчанию
        boolean h = false;
        boolean r = false;
        boolean o = false;

        File dir = new File(args[args.length - 1]);
        boolean d = dir.isDirectory(); // Дополнительный флаг: директория или нет (файл)
        String outputName = "!!!???"; // ???

        for (String arg : args) { // Переопределение флагов
            if (arg.equals("-l")) l = true;
            if (arg.equals("-h")) h = true;
            if (arg.equals("-r")) r = true;
            if (arg.equals("-o")) o = true;
            if (arg.matches("\\w+\\.\\w+") && o) outputName = arg;
        }

        ArrayList res = getInfo(l, h, d, dir);

        toWrite(res, r, o, d, outputName); // Что не так с res?

    }

    private static String getRWX(File file, boolean flag) { // Проверка на чтение-запись-выполнение в 2х видах
        String res = "";
        if (flag) {

            if (file.canRead()) { res += 1; } else { res += 0; }
            if (file.canWrite()) { res += 1; } else { res += 0; }
            if (file.canExecute()) { res += 1; } else { res += 0; }

        } else {

            if (file.canRead()) res += "r";
            if (file.canWrite()) res += "w";
            if (file.canExecute()) res += "x";

        }

        return res + " ";
    }

    private static String fromBytes(File file) { // Перевод из байтов. Добавить округление?
        String res;
        Long length = file.length();
        if (file.length() >= 1073741824) { // Больше гб или нет
            res = Double.toString((double)length / 1073741824.0) + " GB";
        } else {
            if (file.length() >= 1048576) {
                res = Double.toString((double)length / 1048576.0) + " MB"; // Больше мб или нет
            } else {
                res = Double.toString((double)length / 1024.0) + " KB"; // Т.к меньше МБ, то подсчет в КБ
            }
        }
        return res;
    }

    private static String getTime(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(file.lastModified());
    }

    private static ArrayList getInfo(boolean l, boolean h, boolean d, File dir) { // Получаем сводку запрашиваемых данных в листе

        ArrayList<String> info = new ArrayList<>();

        if (!d) { // Если не директория -> файл
            info.add(dir.getName());

            if (l && !h) { // Если просто расширенный формат
                info.add(DirInfo.getRWX(dir, true));
                info.add(Long.toString(dir.lastModified()));
                info.add(Long.toString(dir.length()) + " Bytes");
            }

            if (l && h) { // Если человеко-читаемый формат
                info.add(DirInfo.getRWX(dir, false));
                info.add(getTime(dir));
                info.add(DirInfo.fromBytes(dir));
            }

        } else {
            File[] list = dir.listFiles();

            if (!l && !h) {
                for (File file : list) { // Проверить исключения
                    info.add(file.getName());
                }
            }
            if (l && !h) {
                for (File file : list) {
                    String cur = file.getName() + " ";

                    cur += DirInfo.getRWX(file, true);
                    cur += file.lastModified() + " ";
                    cur += file.length() + " Bytes";

                    info.add(cur);
                }
            }
            if (l && h) {
                for (File file : list) {
                    String cur = file.getName() + " ";

                    cur += DirInfo.getRWX(dir, false);
                    cur += getTime(file) + " ";
                    cur += DirInfo.fromBytes(file);

                    info.add(cur);
                }
            }
        }

        return info;
    }

    private static void toWrite(ArrayList<String> res, boolean r, boolean o, boolean d, String outputName) {
        if (r) Collections.reverse(res);

        if (o) {
            File file = new File("C:\\Users\\Vladislav\\IdeaProjects\\DirectoryInfo\\testOutput\\" + outputName);
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.toURI()))) {
                for (String info: res) {
                    writer.write(info);
                    if (d) writer.newLine(); // Как оптимизировать проверку d/!d ?
                }
            } catch (IOException ex) { System.out.println(":("); }
        } else {
            if (d) {
                for (String info: res) {
                    System.out.println(info);
                }
            } else {
                for (String info: res) {
                    System.out.print(info);
                }
            }
        }
    }
}
