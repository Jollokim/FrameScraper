package Scraper;

import Scraper.Exceptions.ParseException;
import HTMLString.HTMLToString;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Generic scraper class representing a specific instantiation of a Scraper.ConceptScraper
 * @author Mathias Jarbekk
 * @author Thomas Johannessen
 * @author Joakim Jensen
 * @author Michal Kowalski
 * @version 1.1
 * @see ConceptScraper
 */
public class Scraper {
    private final static int FILE = 0;
    private final static int WEBURL = 1;
    private final static int STRING = 2;

    private String url;
    private String websiteContent;
    private Element root = null;



    private ArrayList<Element> nodes = new ArrayList<>();

    /**
     * Constructor for a scraper
     * @param URL Link to target site for scraping
     * @throws IOException Throws an IO Exception whenever user input is crashing with an expected string value
     */
    public Scraper(String URL) throws IOException, ParseException {
        this.url = URL;
        websiteContent = HTMLToString.requestHTMLWithUrl(URL);
        this.root = TreeBuilder.createTree(websiteContent);
    }

    public Scraper(String siteContent, boolean test) throws ParseException {
        websiteContent = siteContent;
        this.root = TreeBuilder.createTree(siteContent);
    }

    // nye Scraper konstruktør
    private Scraper (String source, int sourceType){

        try {
            if (sourceType == FILE){
                this.websiteContent = HTMLToString.readHTMLFromFile(source);
                this.url = source;
                this.root = TreeBuilder.createTree(this.websiteContent);
            }
            else if(sourceType == WEBURL){
                this.websiteContent = HTMLToString.requestHTMLWithUrl(source);
                this.url = source;
                this.root = TreeBuilder.createTree(this.websiteContent);
            }
            else if (sourceType == STRING){
                this.websiteContent = source;
                this.root = TreeBuilder.createTree(source);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    /**
     * A methode for getting the content from a specific HTML-tag in the webpage
     * @param path: Path to the html-file
     * @return Scraper object build from file
     * example: Scraper sc = Scraper.buildScraperWithFile("index.html");
     */
    public static Scraper buildWithFile(String path){
        return new Scraper(path, FILE);
    }

    /**
     * A methode for getting the content from a specific HTML-tag in the webpage
     * @param url: WebUrl to the website that is to be scraped
     * @return Scraper object build from the websites html-source
     * example: Scraper sc = Scraper.buildScraperWithWebUrl("https://webscraper.io/test-sites/e-commerce/allinone");
     */
    public static Scraper buildWithWebUrl(String url){
        return new Scraper(url, WEBURL);
    }

    /**
     * A methode for getting the content from a specific HTML-tag in the webpage
     * @param html: A string which contains html
     * @return Scraper object build from the websites html-source
     * example: Scraper sc = Scraper.buildScraperWithString("<html lang="en"><body><h1 id="header1">Hello world</h1><p>This is my world</p></body></html>");
     */
    public static Scraper buildWithString(String html){
        return new Scraper(html, STRING);
    }

    /**
     *
     * @return The url as a String
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return The website content as String
     */
    public String getWebsiteContent() {
        return websiteContent;
    }

    /**
     * A method for getting the content from a specific HTML-tag in the webpage
     * @param tag The tag from which we want to see the content of
     * @return The content is returned as Strings in an Arraylist of Strings
     */
    public ArrayList<String> getContentFromTagAsString(String tag){
            TreeTraverser.traversingGetContentFromTagAsString(root, tag);
        return TreeTraverser.getTagStringArray();
    }

    /**
     * Works like {@link Scraper#getContentFromTagAsString(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getContentFromTagAsString(String)
     */
    public ArrayList<String> getContentFromTagAsString(String tag, Element startNode){
        TreeTraverser.traversingGetContentFromTagAsString(startNode, tag);
        return TreeTraverser.getTagStringArray();
    }

    /**
     * A method for getting the content from a specific HTML-tag in the webpage
     * @param tag The tag from which we want to see the content of
     * @return The content is returned as Nodes in an Arraylist of Nodes
     */
    public ArrayList<Element> getContentFromTagAsNode(String tag){
            TreeTraverser.traversingGetContentFromTagAsNode(root, tag);
        return TreeTraverser.getTagNodeArray();
    }

    /**
     * Works like {@link Scraper#getContentFromTagAsNode(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getContentFromTagAsNode(String)
     */
    public ArrayList<Element> getContentFromTagAsNode(String tag, Element startNode){
        TreeTraverser.traversingGetContentFromTagAsNode(startNode, tag);
        return TreeTraverser.getTagNodeArray();
    }

    /**
     * A method for getting the content from a id in the webpage
     * @param id The id of an element which we want to get the content from
     * @return The content is returned as Strings in a Arraylist of Strings
     */
    public ArrayList<String> getContentFromIdAsString(String id){
            TreeTraverser.traversingGetContentFromIdAsString(root,id);
        return TreeTraverser.getIdStringArray();
    }

    /**
     * Works like {@link Scraper#getContentFromIdAsString(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getContentFromIdAsString(String)
     */
    public ArrayList<String> getContentFromIdAsString(String id, Element startNode){
        TreeTraverser.traversingGetContentFromIdAsString(startNode,id);
        return TreeTraverser.getIdStringArray();
    }

    /**
     * A method for getting the content from a id in the webpage
     * @param id The id of an element which we want to get the content from
     * @return The content is returned as Nodes in a Arraylist of Nodes
     */
    public ArrayList<Element> getContentFromIdAsNode(String id){
            TreeTraverser.traversingGetContentFromIdAsNode(root, id);
        return TreeTraverser.getIdNodeArray();
    }

    /**
     * Works like {@link Scraper#getContentFromIdAsNode(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getContentFromIdAsNode(String)
     */
    public ArrayList<Element> getContentFromIdAsNode(String id, Element startNode){
        TreeTraverser.traversingGetContentFromIdAsNode(startNode, id);
        return TreeTraverser.getIdNodeArray();
    }

    /**
     * A method for getting the content from a class in the webpage
     * @param className The class name of the element which we want to get the content of
     * @return returns the content of the given class as strings in an Arraylist of Strings
     */
    public ArrayList<String> getContentFromClassAsString(String className){
            TreeTraverser.traversingGetContentFromClassAsString(root,className);
        return TreeTraverser.getClassStringArray();
    }

    /**
     * Works like {@link Scraper#getContentFromClassAsString(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getContentFromClassAsString(String)
     */
    public ArrayList<String> getContentFromClassAsString(String className, Element startNode){
        TreeTraverser.traversingGetContentFromClassAsString(startNode, className);
        return TreeTraverser.getClassStringArray();
    }

    /**
     * A method for getting the content from a class in the webpage
     * @param className The class name of the element which we want to get the content of
     * @return returns the content of the given class as Nodes in an Arraylist of Nodes
     */
    public ArrayList<Element> getContentFromClassAsNode(String className){
            TreeTraverser.traversingGetContentFromClassAsNode(root, className);
        return TreeTraverser.getClassNodeArray();
    }

    /**
     * Works like {@link Scraper#getContentFromClassAsNode(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getContentFromClassAsNode(String)
     */
    public ArrayList<Element> getContentFromClassAsNode(String className, Element startNode){
        TreeTraverser.traversingGetContentFromClassAsNode(startNode, className);
        return TreeTraverser.getClassNodeArray();
    }

    /**
     *A method for getting all links (<a-tags) in the webpage
     * @return the links as String in an Arraylist of Strings
     */
    public ArrayList<String> getLinksInPageAsString(){
            TreeTraverser.traversingGetLinksInPageAsString(root,getUrl());
        return TreeTraverser.getLinksStringArray();
    }

    /**
     * Works like {@link Scraper#getLinksInPageAsString()} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getLinksInPageAsString()
     */

    public ArrayList<String> getLinksInPageAsString(Element startNode){
        TreeTraverser.traversingGetLinksInPageAsString(startNode,getUrl());
        return TreeTraverser.getLinksStringArray();
    }

    /**
     *A method for getting all links (<a-tags) in the webpage
     * @return the links as Nodes in an Arraylist of Nodes
     */
    public ArrayList<Element> getLinksInPageAsNode(){
            TreeTraverser.traversingGetLinksInPageAsNode(root);
        return TreeTraverser.getLinksNodeArray();
    }

    /**
     * Works like {@link Scraper#getLinksInPageAsNode()} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getLinksInPageAsNode()
     */
    public ArrayList<Element> getLinksInPageAsNode(Element startNode){
        TreeTraverser.traversingGetLinksInPageAsNode(startNode);
        return TreeTraverser.getLinksNodeArray();
    }

    /**
     * A method for seaching if a webpage contains a string. this methode IS CASE SENSETIVE.
     * @param searchString string we want to see if exist in the HTML code. IS case sensetive
     * @return returns boolean based on if the String was found or not. True for was found and false for was not found.
     */
    public boolean containsAsBoolean(String searchString){
            Boolean contains = TreeTraverser.traversingContainsAsBoolean(root, searchString);
        return contains;
    }

    /**
     * Works like {@link Scraper#containsAsBoolean(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#containsAsBoolean(String)
     */
    public boolean containsAsBoolean(String searchString, Element startNode){
        Boolean contains = TreeTraverser.traversingContainsAsBoolean(startNode, searchString);
        return contains;
    }

    /**
     * A method for seaching if a webpage contains a string. this methode IS CASE SENSETIVE.
     * @param searchString string we want to see if exist in the HTML code. IS case sensetive
     * @return returns all nodes where it the search string was found in an arraylist of nodes
     */
    public ArrayList<Element> containsAsNode(String searchString){
        TreeTraverser.traversingContainsAsNode(root, searchString);
        return TreeTraverser.getContainsNodeArray();
    }

    /**
     * Works like {@link Scraper#containsAsNode(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#containsAsNode(String)
     */
    public ArrayList<Element> containsAsNode(String searchString, Element startNode){
        TreeTraverser.traversingContainsAsNode(startNode, searchString);
        return TreeTraverser.getContainsNodeArray();
    }

    /**
     * A method for seaching if a webpage contains a string.
     * @param searchString string we want to see if exist in the HTML code. IS NOT case sensetive
     * @return returns boolean based on if the String was found or not. True for was found and false for was not found.
     */
    public boolean containsCaseInSensetiveAsBoolean(String searchString){
        Boolean contains = TreeTraverser.traversingContainsCaseInSensetiveAsBoolean(root, searchString);
        return contains;
    }

    /**
     * Works like {@link Scraper#containsCaseInSensetiveAsBoolean(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#containsCaseInSensetiveAsBoolean(String)
     */
    public boolean containsCaseInSensetiveAsBoolean(String searchString, Element startNode){
        Boolean contains = TreeTraverser.traversingContainsCaseInSensetiveAsBoolean(startNode, searchString);
        return contains;
    }

    /**
     * A method for seaching if a webpage contains a string.
     * @param searchString string we want to see if exist in the HTML code. IS NOT case sensetive
     * @return returns all nodes where it the search string was found in an arraylist of nodes
     */
    public ArrayList<Element> containsCaseInSensetiveAsNode(String searchString){
            TreeTraverser.traversingContainsCaseInSensetiveAsNode(root, searchString);
        return TreeTraverser.getContainsCaseInSensitiveNodeArray();
    }

    /**
     * Works like {@link Scraper#containsCaseInSensetiveAsNode(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#containsCaseInSensetiveAsNode(String)
     */
    public ArrayList<Element> containsCaseInSensetiveAsNode(String searchString, Element startNode){
        TreeTraverser.traversingContainsCaseInSensetiveAsNode(startNode, searchString);
        return TreeTraverser.getContainsCaseInSensitiveNodeArray();
    }

    /**
     * A method for getting all Images from the webpage
     * @return the source for the images as String in an Arraylist of Strings
     */
    public ArrayList<String> getAllImagesFromPageAsString(){
            TreeTraverser.traversingGetAllImagesFromPageAsString(root);
        return TreeTraverser.getImgStringArray();
    }

    /**
     * Works like {@link Scraper#getAllImagesFromPageAsString()} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getAllImagesFromPageAsString()
     */
    public ArrayList<String> getAllImagesFromPageAsString(Element startNode){
        TreeTraverser.traversingGetAllImagesFromPageAsString(startNode);
        return TreeTraverser.getImgStringArray();
    }

    /**
     * A method for getting all Images from the webpage
     * @return the source for the images as Nodes in an Arraylist of Nodes
     */
    public ArrayList<Element> getAllImagesFromPageAsNode(){
        TreeTraverser.traversingGetAllImagesFromPageAsNode(root);
        return TreeTraverser.getImgNodeArray();
    }

    /**
     * Works like {@link Scraper#getAllImagesFromPageAsNode()} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getAllImagesFromPageAsNode()
     */
    public ArrayList<Element> getAllImagesFromPageAsNode(Element startNode){
        TreeTraverser.traversingGetAllImagesFromPageAsNode(startNode);
        return TreeTraverser.getImgNodeArray();
    }

    /**
     * A method for getting a Image with that id from the webpage
     * @param pictureId id as String we want to get the picture from
     * @return the source for the image as String
     */
    public String getImageByIdAsString(String pictureId){
        return TreeTraverser.traversingGetImageByIdAsString(root, pictureId);
    }

    /**
     * Works like {@link Scraper#getImageByIdAsString(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getImageByIdAsString(String)
     */
    public String getImageByIdAsString(String pictureId, Element startNode){
        return TreeTraverser.traversingGetImageByIdAsString(startNode, pictureId);
    }

    /**
     * A method for getting a Image with that id from the webpage
     * @param pictureId id as String we want to get the picture from
     * @return the source for the image as Nodes in an Arraylist of Nodes
     */
    public Element getImageByIdAsNode(String pictureId){
        return TreeTraverser.traversingGetImageByIdAsNode(root, pictureId);
    }

    /**
     * Works like {@link Scraper#getImageByIdAsNode(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getImageByIdAsNode(String)
     */
    public Element getImageByIdAsNode(String pictureId, Element startNode){
        return TreeTraverser.traversingGetImageByIdAsNode(startNode, pictureId);
    }

    /**
     * A method for getting all the Images with that classname from the webpage
     * @param pictureClass class as String we want to get the pictures from
     * @return the source for the images as String in an Arraylist of Strings
     */
    public ArrayList<String> getImageByClassAsString(String pictureClass){
        TreeTraverser.traversingGetImageByClassAsString(root, pictureClass);
        return TreeTraverser.getImgClassStringArray();
    }

    /**
     * Works like {@link Scraper#getImageByClassAsString(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getImageByClassAsString(String)
     */
    public ArrayList<String> getImageByClassAsString(String pictureClass, Element startNode){
        TreeTraverser.traversingGetImageByClassAsString(startNode, pictureClass);
        return TreeTraverser.getImgClassStringArray();
    }

    /**
     * A method for getting all the Images with that classname from the webpage
     * @param pictureClass class as String we want to get the pictures from
     * @return the source for the images as Nodes in an Arraylist of Nodes
     */
    public ArrayList<Element> getImageByClassAsNode(String pictureClass){
        TreeTraverser.traversingGetImageByClassAsNode(root, pictureClass);
        return TreeTraverser.getImgClassNodeArray();
    }

    /**
     * Works like {@link Scraper#getImageByClassAsNode(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getImageByClassAsNode(String)
     */
    public ArrayList<Element> getImageByClassAsNode(String pictureClass, Element startNode){
        TreeTraverser.traversingGetImageByClassAsNode(startNode, pictureClass);
        return TreeTraverser.getImgClassNodeArray();
    }

    /**
     *A method for getting all Videos from the webpage
     * @return the source for the videos as String in an Arraylist of Strings
     */
    public ArrayList<String> getAllVideosFromPageAsString(){
        TreeTraverser.traversingGetAllVideosFromPageAsString(root);
        return TreeTraverser.getVideoStringArray();
    }

    /**
     * Works like {@link Scraper#getAllVideosFromPageAsString()} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getAllVideosFromPageAsString()
     */
    public ArrayList<String> getAllVideosFromPageAsString(Element startNode){
        TreeTraverser.traversingGetAllVideosFromPageAsString(startNode);
        return TreeTraverser.getVideoStringArray();
    }

    /**
     *A method for getting all Videos from the webpage
     * @return the source for the videos as Nodes in an Arraylist of Nodes
     */
    public ArrayList<Element> getAllVideosFromPageAsNode(){
        TreeTraverser.traversingGetAllVideosFromPageAsNode(root);
        return TreeTraverser.getVideoNodeArray();
    }

    /**
     * Works like {@link Scraper#getAllVideosFromPageAsNode()} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getAllVideosFromPageAsNode()
     */
    public ArrayList<Element> getAllVideosFromPageAsNode(Element startNode){
        TreeTraverser.traversingGetAllVideosFromPageAsNode(startNode);
        return TreeTraverser.getVideoNodeArray();
    }

    /**
     * A method for getting a Videos with that id from the webpage
     * @param videoId id as String we want to get the video from
     * @return the source for the video as String in a ArrayList of strings
     */
    public String getVideoByIdAsString(String videoId){
        return TreeTraverser.traversingGetVideoByIdAsString(root, videoId);
    }

    /**
     * Works like {@link Scraper#getVideoByIdAsString(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getVideoByIdAsString(String)
     */
    public String getVideoByIdAsString(String videoId, Element startNode){
        return TreeTraverser.traversingGetVideoByIdAsString(startNode, videoId);
    }

    /**
     * A method for getting a Videos with that id from the webpage
     * @param videoId id as String we want to get the video from
     * @return the source for the video as Nods in a Arraylist of Nodes
     */
    public Element getVideoByIdAsNode(String videoId){
        return TreeTraverser.traversingGetVideoByIdAsNode(root, videoId);
    }

    /**
     * Works like {@link Scraper#getVideoByIdAsNode(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getVideoByIdAsNode(String)
     */
    public Element getVideoByIdAsNode(String videoId, Element startNode){
        return TreeTraverser.traversingGetVideoByIdAsNode(startNode, videoId);
    }

    /**
     * A method for getting all the Videos with that classname from the webpage
     * @param videoClass class as String we want to get the video from
     * @return the source for the videos as String in an Arraylist of Strings
     */
    public ArrayList<String> getVideoByClassAsString(String videoClass){
        TreeTraverser.traversingGetVideoByClassAsString(root, videoClass);
        return TreeTraverser.getVideoClassStringArray();
    }

    /**
     * Works like {@link Scraper#getVideoByClassAsString(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getVideoByClassAsString(String)
     */
    public ArrayList<String> getVideoByClassAsString(String videoClass, Element startNode){
        TreeTraverser.traversingGetVideoByClassAsString(startNode, videoClass);
        return TreeTraverser.getVideoClassStringArray();
    }

    /**
     * A method for getting all the Videos with that classname from the webpage
     * @param videoClass class as String we want to get the video from
     * @return the source for the videos as Node in an Arraylist of Nodes
     */
    public ArrayList<Element> getVideoByClassAsNode(String videoClass){
        TreeTraverser.traversingGetVideoByClassAsNode(root, videoClass);
        return TreeTraverser.getVideoClassNodeArray();
    }

    /**
     * Works like {@link Scraper#getVideoByClassAsNode(String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getVideoByClassAsNode(String)
     */
    public ArrayList<Element> getVideoByClassAsNode(String videoClass, Element startNode){
        TreeTraverser.traversingGetVideoByClassAsNode(startNode, videoClass);
        return TreeTraverser.getVideoClassNodeArray();
    }

    /**
     * A method for getting all the HTML classes for the webpage
     * @return all found classes as strings in an Arraylist of Strings
     */
    public ArrayList<String> getClassesInPage(){
        TreeTraverser.traversingGetClassesInPage(root);
        return TreeTraverser.getAllClassArray();
    }

    /**
     * Works like {@link Scraper#getClassesInPage()} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getClassesInPage()
     */
    public ArrayList<String> getClassesInPage(Element startNode){
        TreeTraverser.traversingGetClassesInPage(startNode);
        return TreeTraverser.getAllClassArray();
    }

    /**
     * A method for getting all the HTML id's for the webpage
     * @return all found id's as strings in an Arraylist of Strings
     */
    public ArrayList<String> getIdsInPage(){
        TreeTraverser.traversingGetIdsInPage(root);
        return TreeTraverser.getAllIdsArray();
    }

    /**
     * Works like {@link Scraper#getIdsInPage()} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getIdsInPage()
     */
    public ArrayList<String> getIdsInPage(Element startNode){
        TreeTraverser.traversingGetIdsInPage(startNode);
        return TreeTraverser.getAllIdsArray();
    }

    /**
     * A method for getting the content from a attribute of a tag as Node
     * @param tag name of the tag you want to search for
     * @param attribute name of the attribute you want to search for
     * @return a list with the content of the attribute in the tag as Node in an Arraylist of Nodes
     */
    public ArrayList<Element> getAttributeContentWithTagAndNameAsNode(String tag, String attribute){
        TreeTraverser.traversingGetAttributeContentWithTagAndNameAsNode(root, tag,attribute);
        return TreeTraverser.getAttributeContentNodeArray();
    }

    /**
     * Works like {@link Scraper#getAttributeContentWithTagAndNameAsNode(String, String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getAttributeContentWithTagAndNameAsNode(String, String)
     */
    public ArrayList<Element> getAttributeContentWithTagAndNameAsNode(String tag, String attribute, Element startNode){
        TreeTraverser.traversingGetAttributeContentWithTagAndNameAsNode(startNode, tag,attribute);
        return TreeTraverser.getAttributeContentNodeArray();
    }

    /**
     * A method for getting the content from a attribute of a tag as String
     * @param tag name of the tag you want to search for
     * @param attribute name of the attribute you want to search for
     * @return a list with the content of the attribute in the tag as String in an Arraylist of Strings
     */
    public ArrayList<String> getAttributeContentWithTagAndNameAsString(String tag, String attribute){
        TreeTraverser.traversingGetAttributeContentWithTagAndNameAsString(root, tag,attribute);
        return TreeTraverser.getAttributeContentStringArray();
    }

    /**
     * Works like {@link Scraper#getAttributeContentWithTagAndNameAsString(String, String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getAttributeContentWithTagAndNameAsString(String, String)
     */
    public ArrayList<String> getAttributeContentWithTagAndNameAsString(String tag, String attribute, Element startNode){
        TreeTraverser.traversingGetAttributeContentWithTagAndNameAsString(startNode, tag,attribute);
        return TreeTraverser.getAttributeContentStringArray();
    }

    /**
     * A method for getting the content from a attribute of a tag with the id, as String
     * @param id name of the tag you want to search for
     * @param attribute name of the attribute you want to search for
     * @return a list with the content of the attribute in the tag with the id, as String in an Arraylist of Strings
     */
    public ArrayList<String> getAttributeContentWithIdAndNameAsString(String id, String attribute){
        TreeTraverser.traversingGetAttributeContentWithIdAndNameAsString(root, id,attribute);
        return TreeTraverser.getAttributeIdContentStringArray();
    }

    /**
     * Works like {@link Scraper#getAttributeContentWithIdAndNameAsString(String, String)} except you can choose your own startnode
     * from anywhere in the HTML code tree
     *
     * @param startNode Node to start the search from.
     * @see Scraper#getAttributeContentWithIdAndNameAsString(String, String)
     */
    public ArrayList<String> getAttributeContentWithIdAndNameAsString(String id, String attribute, SoupNode startNode){
        TreeTraverser.traversingGetAttributeContentWithIdAndNameAsString(startNode, id,attribute);
        return TreeTraverser.getAttributeIdContentStringArray();
    }



    public Element getRoot() {
        return root;
    }

    public void printBeautyfull(){
        printBeautyfull(root, 0);
    }

    public void printBeautyfull(Element node, int nTabs){

        if (node != null){
            String tabs = "";

            for (int i = 0; i < nTabs; i++){
                tabs += '\t';
            }

            String str = tabs +
                    "Scraper.SoupNode{" +
                    "tag=\'" + node.getTag() + '\'' +
                    ", attributes=" + node.getAttributeNames().toString() +
                    ", textChildren=" + node.getStringChildren().toString() + '}';

            System.out.println(str);

            for (int i = 0; i < node.getNodeChildren().size(); i++){
                printBeautyfull(node.getNodeChildren().get(i), nTabs+1);
            }
        }
    }
}
