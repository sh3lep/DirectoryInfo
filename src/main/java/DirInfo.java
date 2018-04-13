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

        CMDArgs values = new CMDArgs(args);

        boolean l = values.longFormat;
        boolean h = values.humanReadable;
        boolean r = values.reverse;
        boolean o = values.out != null;

        File dir = new File(values.dir);
        boolean d = dir.isDirectory(); // Дополнительный флаг: директория или нет (файл)
        File outputName = new File(".//testOutput//" + values.out);

        ArrayList res = getInfo(l, h, r, d, dir);

        toWrite(res, o, d, outputName); // Что не так с res? // Стоит ли делать постепенную запись/вывод?

    }

    private static String getRWX(File file, boolean flag) { // Проверка на чтение-запись-выполнение в 2х видах
        String res = "";
        if (flag) {

            if (file.canRead()) {
                res += 1;
            } else {
                res += 0;
            }
            if (file.canWrite()) {
                res += 1;
            } else {
                res += 0;
            }
            if (file.canExecute()) {
                res += 1;
            } else {
                res += 0;
            }

        } else {

            if (file.canRead()) res += "r";
            if (file.canWrite()) res += "w";
            if (file.canExecute()) res += "x"; // Как по-другому?

        }

        return res;
    }

    private static String fromBytes(File file) { // Перевод из байтов. Добавить округление?
        String res;
        Long length = file.length();
        if (file.length() >= 1073741824) { // Больше гб или нет
            res = Double.toString((double) length / 1073741824.0) + " GB";
        } else {
            if (file.length() >= 1048576) {
                res = Double.toString((double) length / 1048576.0) + " MB"; // Больше мб или нет
            } else {
                res = Double.toString((double) length / 1024.0) + " KB"; // Т.к меньше МБ, то подсчет в КБ
            }
        }
        return res;
    }

    private static String getTime(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(file.lastModified());
    }

    private static ArrayList getInfo(boolean l, boolean h, boolean r, boolean d, File dir) { // Получаем сводку запрашиваемых данных в листе

        ArrayList<String> info = new ArrayList<>();

        if (!d) { // Если не директория -> файл
            info.add(dir.getName());

            if (l && !h) { // Если просто расширенный формат
                info.add(getRWX(dir, true));
                info.add(Long.toString(dir.lastModified()));
                info.add(Long.toString(dir.length()) + " Bytes");
            }

            if (l && h) { // Если человеко-читаемый формат
                info.add(getRWX(dir, false));
                info.add(getTime(dir));
                info.add(fromBytes(dir));
            }

            if (r) Collections.reverse(info);

        } else { // Если директория
            File[] list = dir.listFiles();

            if (!l && !h) { // Короткий формат - только имя
                for (File file : list) info.add(file.getName()); // Проверить исключения
            }

            if (l) { // Если длинный формат
                for (File file : list) {

                    ArrayList<String> cur = new ArrayList<>();
                    cur.add(file.getName());

                    if (!h) { // Просто длинный формат
                        cur.add(getRWX(file, true));
                        cur.add(Long.toString(file.lastModified()));
                        cur.add(file.length() + " Bytes");
                    } else { // Длинный человеко-читаемый
                        cur.add(DirInfo.getRWX(dir, false));
                        cur.add(getTime(file));
                        cur.add(fromBytes(file));
                    }

                    if (r) Collections.reverse(cur); // Проверка флага на обратный порядок вывода

                    String formattedCur = cur.toString() // Не слишком ли заморочно для reverse?
                            .replace(",", "")
                            .replace("[", "")
                            .replace("]", "")
                            .trim();
                    info.add(formattedCur);
                }
            }
        }
        return info;
    }

    private static void toWrite(ArrayList<String> res, boolean o, boolean d, File outputName) {
        if (o) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputName.toURI()))) {
                for (String info : res) {
                    writer.write(info + " ");
                    if (d) writer.newLine(); // Как оптимизировать проверку d/!d ?
                }
            } catch (IOException ex) {
                System.out.println(":(");
            }
        } else {
            if (d) {
                for (String info : res) {
                    System.out.println(info);
                }
            } else {
                for (String info : res) {
                    System.out.print(info + " ");
                }
            }
        }
    }
}
