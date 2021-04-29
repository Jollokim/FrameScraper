package Scraper;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
/**
 * SoupNode: an object representing a individual element from the html source.
 * The object contains information of what content is nested within it: other html-elements or text.
 * The object also hold information about the element itself, being what tag it is and what attributes it hold, including values to the attributes. This information is used in particular when searching after elements
 * @author Joakim Jensen
 * @version 1.0
 * @see Scraper
 */
public final class SoupNode {
    private String tag;
//    private String full_tag;

    private Dictionary<String, String> attributes = new Hashtable();

    private ArrayList<String> attributeNames = new ArrayList<>();

    private ArrayList<SoupNode> nodeChildren = new ArrayList<>();
    private ArrayList<String> stringChildren = new ArrayList<>();

    public SoupNode() {
    }

    public SoupNode(String tag) {
        this.tag = tag;
    }

    public void addNodeChild(SoupNode child){
        this.nodeChildren.add(child);
    }

    public void addStringChild(String text){
        this.getStringChildren().add(text);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

//    public String getFull_tag() {
//        return full_tag;
//    }
//
//    public void setFull_tag(String full_tag) {
//        this.full_tag = full_tag;
//    }

    public Dictionary<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Dictionary<String, String> attributes) {
        this.attributes = attributes;
    }


    public ArrayList<SoupNode> getNodeChildren() {
        return nodeChildren;
    }

    public void setNodeChildren(ArrayList<SoupNode> nodeChildren) {
        this.nodeChildren = nodeChildren;
    }

    public ArrayList<String> getStringChildren() {
        return stringChildren;
    }

    public void setStringChildren(ArrayList<String> stringChildren) {
        this.stringChildren = stringChildren;
    }

    public ArrayList<String> getAttributeNames() {
        return attributeNames;
    }


    @Override
    public String toString() {
        return "Scraper.SoupNode{" +
                "tag='" + tag + '\'' +
                ", attributes=" + attributes +
                ", attributeNames=" + attributeNames +
                ", nodeChildren=" + nodeChildren +
                ", stringChildren=" + stringChildren +
                '}';
    }
}
