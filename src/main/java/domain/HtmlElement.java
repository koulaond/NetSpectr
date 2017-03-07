package domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HtmlElement {
    private String name;
    private String text;
    private Map<String, Object> attributes;
    private Set<HtmlElement> children;
    private HtmlElement parentElement;

    public HtmlElement(){
        this.attributes = new HashMap<>();
        this.children = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Set<HtmlElement> getChildren() {
        return children;
    }

    public void setChildren(Set<HtmlElement> children) {
        this.children = children;
    }

    public HtmlElement getParentElement() {
        return parentElement;
    }

    public void setParentElement(HtmlElement parentElement) {
        this.parentElement = parentElement;
    }
}
