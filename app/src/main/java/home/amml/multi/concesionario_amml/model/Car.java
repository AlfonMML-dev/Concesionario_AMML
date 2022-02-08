package home.amml.multi.concesionario_amml.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Car implements Parcelable {
    private String ref;
    private String title;
    private String description;
    public String[] categoriesName = {"color", "doors", "tag", "fuel", "horsepower", "km",
            "gearlever", "warranty", "year"};
    private String[] categoriesValue = new String[9];
    private String[] images;
    private int price;
    private String localization;
    private String url;

    private String sql = "";

    public Car() {
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getCategoriesValue() {
        return categoriesValue;
    }

    public void setCategoriesValue(String[] categoriesValue) {
        this.categoriesValue = categoriesValue;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "Car{" +
                "ref='" + ref + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", categoriesName=" + Arrays.toString(categoriesName) +
                ", categoriesValue=" + Arrays.toString(categoriesValue) +
                ", images=" + Arrays.toString(images) +
                ", price=" + price +
                ", localization='" + localization + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    protected Car(Parcel in) {
        ref = in.readString();
        title = in.readString();
        description = in.readString();
        categoriesValue = in.createStringArray();
        images = in.createStringArray();
        price = in.readInt();
        localization = in.readString();
        url = in.readString();
        sql = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ref);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeStringArray(categoriesValue);
        dest.writeStringArray(images);
        dest.writeInt(price);
        dest.writeString(localization);
        dest.writeString(url);
        dest.writeString(sql);
    }

    public String getColor() {
        return categoriesValue[0];
    }

    public String getDoors() {
        return categoriesValue[1];
    }

    public String getTag() {
        return categoriesValue[2];
    }

    public String getFuel() {
        return categoriesValue[3];
    }

    public String getHorsepower() {
        return categoriesValue[4];
    }

    public String getKilometers() {
        return categoriesValue[5];
    }

    public String getGearLever() {
        return categoriesValue[6];
    }

    public String getGearWarranty() {
        return categoriesValue[7];
    }

    public String getDate() {
        return categoriesValue[8];
    }

    public String getImagenPrincipal() {
        return images[0];
    }

    public String getSql(){
        return sql;
    }
    public void setSql(String query){
        sql = query;
    }
}
