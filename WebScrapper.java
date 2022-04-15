import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


public class WebScrapper {

    static final private String htmlLinkRegExp = "<a\\s+(?:[^>]*?\\s+)?href=([\"'])(.*?)\\1";
    static final private int port = 443;
    static private int maxDepth;
    private static final List<LinkStore> finalArray = new ArrayList<LinkStore>();

    public static void main(String[] _args){
        String hostname;
        String[] args = {"https://onlinepngtools.com/", "3"};

        /* Обработка недостаточного количества аргументов */
//        if (args.length < 2) {
//            System.out.printf("Требуются 2 аргумента, но только %d было передано.", args.length);
//            System.exit(1);
//        }
//        System.out.println(args[0]);

        /* Проверка валидности значения глубины */
        try {
            maxDepth = Integer.parseInt(args[1]);
            if (maxDepth < 0){
                System.out.println("Переданная глубина должна быть больше нуля");
                System.exit(1);
            }
        } catch (NumberFormatException e){
            System.out.println("Переданная глубина должна быть натуральным числом большим нуля.");
            System.exit(1);
        }

        /* Проверка валидности URL & подключение сокета */
        hostname = args[0];

        recurFunc(hostname, 0);

        for (LinkStore str : finalArray) {
            System.out.print(str.getDepth());
            System.out.print("\s");
            System.out.println(str.getLink());
        }

        System.out.println("OK");
    }


    private static void recurFunc(String link, int depth) {
        if (depth != maxDepth) {

            finalArray.add(new LinkStore(link, depth));

            try {
                String html = MyRequest.httpsRequest(link);

                LinksGetter work = new LinksGetter(html);
                work.formLinksArray(htmlLinkRegExp, 2);
                work.onlyStartWith("http");

                for (String str : work.getList()) {
                    recurFunc(str, depth+1);
                }
            } catch (SocketTimeoutException ste){

            } catch (IOException ioe){

            }
        }
    }

}