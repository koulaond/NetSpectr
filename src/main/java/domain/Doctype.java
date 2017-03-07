package domain;

import java.util.ArrayList;
import java.util.List;

public class Doctype {
    private boolean isPublic;

    private List<String> attributes;

    public Doctype(){
        this.attributes = new ArrayList<String>();
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }
}
