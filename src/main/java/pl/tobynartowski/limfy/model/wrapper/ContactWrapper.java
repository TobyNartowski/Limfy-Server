package pl.tobynartowski.limfy.model.wrapper;

public class ContactWrapper {

    private String number;

    public ContactWrapper() {}

    public ContactWrapper(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
