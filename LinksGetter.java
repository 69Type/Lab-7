import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinksGetter {
    private String text;
    private List<String> list;

    public LinksGetter(String str) {
        text = str;
    }

    public void formLinksArray(String regexp, int group){
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(regexp).matcher(text);
        while (m.find()) {
            allMatches.add(m.group(group));
        }
        list = allMatches;
    }

    public void validateLinkStart(String ifStarted, String shouldStarted){
        for (int i=0; i < list.size(); i++) {
            String curLink = list.get(i);
            if (curLink.startsWith(ifStarted)){
                list.set(i, shouldStarted + curLink);
            }
        }
    }

    public void onlyStartWith(String start){
        List<String> newList = new ArrayList<>();
        for (String str : list) {
            if (str.startsWith(start)) newList.add(str);
        }
        list = newList;
    }

    public void removeProtocolPart(){}

    public List<String> getList(){
        return list;
    }

    public String getText(){
        return text;
    }

    public void setText(String t){
        text = t;
    }
}
