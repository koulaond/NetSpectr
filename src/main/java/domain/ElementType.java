package domain;

public enum ElementType{
    HTML("html"),
    HEAD("head"),
    BODY("body"),
    SCRIPT("script"),
    LINK("a"),
    DIV("div");

    private String elementName;

    ElementType(String elementName){
        this.elementName=elementName;
    }

    public String getElementName() {
        return elementName;
    }
}