package carteffect.sam.com.cartanimation;

/**
 * Created by sanketauti on 4/19/2017.
 */
public class DataModel {

    String name;
    String type;
    String version_number;
    int image;


    public DataModel(String name, String type, String version_number, int image) {
        this.name = name;
        this.type = type;
        this.version_number = version_number;
        this.image = image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion_number() {
        return version_number;
    }

    public void setVersion_number(String version_number) {
        this.version_number = version_number;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
